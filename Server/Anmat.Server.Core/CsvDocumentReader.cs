using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Anmat.Server.Core.Data;
using Anmat.Server.Core.Exceptions;
using Anmat.Server.Core.Model;
using Anmat.Server.Core.Properties;
using Microsoft.VisualBasic.FileIO;
using System.Text.RegularExpressions;
using System.Text;

namespace Anmat.Server.Core
{
    public class CsvDocumentReader : IDocumentReader
    {
		private static readonly string separator = ";";
		private readonly AnmatConfiguration configuration;
		private readonly IDictionary<Type, Func<string, DocumentColumnMetadata, string>> typeConverters;

		public CsvDocumentReader (AnmatConfiguration configuration)
		{
			this.configuration = configuration;
			this.typeConverters = new Dictionary<Type, Func<string, DocumentColumnMetadata, string>> ();

			this.LoadTypeConverters ();
		}

		public string FileExtension { get { return ".csv"; } }

		/// <exception cref="DocumentFormatException">DocumentFormatException</exception>
		/// <exception cref="FieldFormatException">FieldFormatException</exception>
		/// <exception cref="ArgumentException">ArgumentException</exception>
        public Document Read(string path, DocumentMetadata metadata)
        {
			this.ValidateDocumentFile (path);

            var document = new Document();

			using (var parser = new TextFieldParser (path, this.configuration.GetDefaultEncoding())) {
				parser.TextFieldType = FieldType.Delimited;
				parser.SetDelimiters(separator);
				parser.HasFieldsEnclosedInQuotes = true;

				while (!parser.EndOfData) 
				{
					if (parser.LineNumber == 1 && metadata.HasHeader) {
						parser.ReadLine ();
						continue;
					}

					var fields = parser.ReadFields();
					
					this.ValidateDocumentColumns (metadata, fields, parser.LineNumber);

					var row = this.GetRow (fields, metadata);
                                
					document.AddRow(row);
				}
			}

			return document;
        }

		private void ValidateDocumentFile (string path)
		{
			if (!File.Exists(path)) {
                throw new ArgumentException(string.Format(Resources.DocumentReader_FileNotExists, path));
            }

            if (Path.GetExtension(path) != this.FileExtension)
            {
                throw new DocumentFormatException(string.Format(Resources.DocumentReader_NotSupportedFileExtension, Path.GetExtension(path), this.FileExtension));
            }
		}

		private void ValidateDocumentColumns(DocumentMetadata metadata, string[] fields, long line)
		{
			if (fields.Length != metadata.Columns.Count())
            {
                throw new DocumentFormatException(string.Format(Resources.CsvDocumentReader_ExpectedColumnsFailed, metadata.Columns.Count(), fields.Length, line));
            }
		}

		private Row GetRow(string[] fields, DocumentMetadata metadata)
		{
			var row = new Row();

            for (var i = 0; i < fields.Length; i++ )
            {
                var columnMetadata = metadata.Columns.FirstOrDefault(x => x.ColumnNumber == i);

                if (columnMetadata == null)
                {
                    throw new FieldFormatException(string.Format(Resources.CsvDocumentReader_ColumnNotDefined, i, metadata.DocumentName));
                }

                var value = this.Normalize(fields[i]);

                if(!columnMetadata.IsNullable && string.IsNullOrEmpty(value)) {
                    throw new FieldFormatException(string.Format(Resources.CsvDocumentReader_NullValueOnFieldNotAllowed, columnMetadata.Name, metadata.DocumentName));
                }

                if ((typeof(string)) == (columnMetadata.GetType ()) && columnMetadata.RemovableAccents)
                {
                    value = this.RemoveAccents(value.ToLower());
                }

				if ((typeof (string)) == (columnMetadata.GetType ()) && columnMetadata.UpperCase) {
					value = value.ToUpper();
				}

				var formattedValue = this.FormatField (metadata, columnMetadata, value);

                row.AddCell(formattedValue, columnMetadata.GetType());    
            }

			return row;
		}

		private string ReplaceAccents(string value)
        {
            var a = new Regex("[à|ä|â]", RegexOptions.Compiled);
            var e = new Regex("[è|ë|ê]", RegexOptions.Compiled);
            var i = new Regex("[ì|ï|î]", RegexOptions.Compiled);
            var o = new Regex("[ò|ö|ô]", RegexOptions.Compiled);
            var u = new Regex("[ù|ü|û]", RegexOptions.Compiled);
            
            value = a.Replace(value, "á");
            value = e.Replace(value, "é");
            value = i.Replace(value, "í");
            value = o.Replace(value, "ó");
            value = u.Replace(value, "ú"); 
           
            return value;
        }

        private string RemoveAccents(string value)
        {
            var a = new Regex("[á|à|ä|â]", RegexOptions.Compiled);
            var e = new Regex("[é|è|ë|ê]", RegexOptions.Compiled);
            var i = new Regex("[í|ì|ï|î]", RegexOptions.Compiled);
            var o = new Regex("[ó|ò|ö|ô]", RegexOptions.Compiled);
            var u = new Regex("[ú|ù|ü|û]", RegexOptions.Compiled);
            
            value = a.Replace(value, "a");
            value = e.Replace(value, "e");
            value = i.Replace(value, "i");
            value = o.Replace(value, "o");
            value = u.Replace(value, "u"); 
           
            return value;
        }

		private string FormatField(DocumentMetadata metadata, DocumentColumnMetadata columnMetadata, string value)
		{
			if (columnMetadata.IsNullable && string.IsNullOrEmpty (value)) {
				return value;
			}

			var converter = default (Func<string, DocumentColumnMetadata, string>);
			var fieldType = columnMetadata.GetType ();

			if (!this.typeConverters.Any (c => c.Key == fieldType)) {
				fieldType = typeof (string);
			}

			if (!this.typeConverters.TryGetValue (fieldType, out converter)) {
				throw new FieldFormatException (string.Format(Resources.CsvDocumentReader_CantConvertFieldValue, value, columnMetadata.Type));
			}

			return converter (value, columnMetadata);
		}

		private string Normalize(string value)
		{
			if (string.IsNullOrWhiteSpace (value) || value.ToLower () == "null") {
				return string.Empty;
			}

			return value;
		}

		private void LoadTypeConverters ()
		{
			this.typeConverters.Add (typeof (string), (value, columnMetadata) => value);
			this.typeConverters.Add (typeof (bool), (value, columnMetadata) => {
				var converted = default (bool);

				if (!bool.TryParse (value, out converted)) {
					throw new FieldFormatException(string.Format(Resources.CsvDocumentReader_InvalidFieldFormat, columnMetadata.Name, typeof(bool).Name));
				}

				return converted.ToString();
			});
			this.typeConverters.Add (typeof (int), (value, columnMetadata) => {
				var converted = default (int);

				if (!int.TryParse (value, out converted)) {
					throw new FieldFormatException(string.Format(Resources.CsvDocumentReader_InvalidFieldFormat, columnMetadata.Name, typeof(int).Name));
				}

				return converted.ToNumberString ();
			});
			this.typeConverters.Add (typeof (double), (value, columnMetadata) => {
				var converted = default (double);

				if (!double.TryParse (value, out converted)) {
					throw new FieldFormatException(string.Format(Resources.CsvDocumentReader_InvalidFieldFormat, columnMetadata.Name, typeof(double).Name));
				}

				return converted.ToNumberString ();
			});
			this.typeConverters.Add (typeof (DateTime), (value, columnMetadata) => {
				var converted = default (DateTime);

				if (!DateTime.TryParse (value, out converted)) {
					throw new FieldFormatException(string.Format(Resources.CsvDocumentReader_InvalidFieldFormat, columnMetadata.Name, typeof(DateTime).Name));
				}

				return converted.ToString ();
			});
		}
    }
}
