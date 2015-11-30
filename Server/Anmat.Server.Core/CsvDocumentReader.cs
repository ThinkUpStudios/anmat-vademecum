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
using Anmat.Server.Core;

namespace Anmat.Server.Core
{
	public class CsvDocumentReader : IDocumentReader
	{
		private static readonly string separator = ";";
		private readonly AnmatConfiguration configuration;
		private readonly IDictionary<Type, Func<string, DocumentMetadata, DocumentColumnMetadata, string>> typeConverters;

		public CsvDocumentReader (AnmatConfiguration configuration)
		{
			this.configuration = configuration;
			this.typeConverters = new Dictionary<Type, Func<string, DocumentMetadata, DocumentColumnMetadata, string>> ();

			this.LoadTypeConverters ();
		}

		public string FileExtension { get { return ".csv"; } }

		/// <exception cref="DocumentFormatException">DocumentFormatException</exception>
		/// <exception cref="FieldFormatException">FieldFormatException</exception>
		/// <exception cref="ArgumentException">ArgumentException</exception>
		public Document Read (string path, DocumentMetadata metadata)
		{
			this.ValidateDocumentFile (path);

			var document = new Document();

			using (var parser = new TextFieldParser (path, this.configuration.GetDefaultEncoding ())) {
				parser.TextFieldType = FieldType.Delimited;
				parser.SetDelimiters (separator);
				parser.HasFieldsEnclosedInQuotes = true;

				while (!parser.EndOfData) {
					if (parser.LineNumber == 1 && metadata.HasHeader) {
						parser.ReadLine ();
						continue;
					}

					var line = parser.LineNumber;
					var fields = parser.ReadFields();

					fields = this.SanitizeColumns (fields, metadata);

					if (fields.IsNullOrEmpty ()) {
						continue;
					}

					this.ValidateDocumentColumns (metadata, fields, line);

					var row = this.GetRow (fields, metadata, line);

					document.AddRow (row);

					if (parser.LineNumber == -1) {
						continue;
					}
				}
			}

			return document;
		}

		private void ValidateDocumentFile (string path)
		{
			if (!File.Exists (path)) {
				throw new ArgumentException (string.Format (Resources.DocumentReader_FileNotExists, path));
			}

			if (Path.GetExtension (path) != this.FileExtension) {
				throw new DocumentFormatException (string.Format (Resources.DocumentReader_NotSupportedFileExtension, Path.GetExtension (path), this.FileExtension));
			}
		}

		private void ValidateDocumentColumns (DocumentMetadata metadata, string[] fields, long line)
		{
			if (fields.Length > metadata.Columns.Count ()) {
				throw new DocumentFormatException (string.Format (Resources.CsvDocumentReader_ExpectedColumnsFailed, metadata.Columns.Count (), fields.Length, line, metadata.DocumentName));
			}
		}

		private string[] SanitizeColumns (string[] fields, DocumentMetadata metadata)
		{
			var result = fields;

			if (fields.Length > metadata.Columns.Count) {
				var canClean = true;

				for (var i = metadata.Columns.Count; i < fields.Length; i++) {
					canClean = canClean && string.IsNullOrWhiteSpace (fields[i]);
				}

				if (canClean) {
					result = fields.Take (metadata.Columns.Count).ToArray ();
				}
			}

			return result;
		}

		private Row GetRow (string[] fields, DocumentMetadata metadata, long line)
		{
			var row = new Row();

			for (var i = 0; i < fields.Length; i++) {
				var columnMetadata = metadata.Columns.FirstOrDefault(x => x.ColumnNumber == i);

				if (columnMetadata == null) {
					throw new FieldFormatException (string.Format (Resources.CsvDocumentReader_ColumnNotDefined, i, metadata.DocumentName));
				}

				var value = this.Normalize(fields[i]);

				if (!columnMetadata.IsNullable && string.IsNullOrEmpty (value)) {
					throw new FieldFormatException (string.Format (Resources.CsvDocumentReader_NullValueOnFieldNotAllowed, columnMetadata.Name, metadata.DocumentName, line));
				}

				if ((typeof (string)) == (columnMetadata.GetType ()) && columnMetadata.RemovableAccents) {
					value = this.RemoveAccents (value.ToLower ());
				}

				if ((typeof (string)) == (columnMetadata.GetType ()) && columnMetadata.UpperCase) {
					value = value.ToUpper ();
				}

				var formattedValue = this.FormatField (metadata, columnMetadata, value);

				row.AddCell (formattedValue, columnMetadata.GetType ());
			}

			if (fields.Length < metadata.Columns.Count ()) {
				for (var i = fields.Length; i < metadata.Columns.Count; i++) {
					var columnMetadata = metadata.Columns.FirstOrDefault(x => x.ColumnNumber == i);
					var cellType = columnMetadata.GetType();
					var formattedValue = this.FormatField (metadata, columnMetadata, cellType.GetDefault ().ToString());

					row.AddCell (formattedValue, cellType);
				}
			}

			return row;
		}

		private string RemoveAccents (string value)
		{
			var a = new Regex("[á|à|ä|â]", RegexOptions.Compiled);
			var e = new Regex("[é|è|ë|ê]", RegexOptions.Compiled);
			var i = new Regex("[í|ì|ï|î]", RegexOptions.Compiled);
			var o = new Regex("[ó|ò|ö|ô]", RegexOptions.Compiled);
			var u = new Regex("[ú|ù|ü|û]", RegexOptions.Compiled);

			value = a.Replace (value, "a");
			value = e.Replace (value, "e");
			value = i.Replace (value, "i");
			value = o.Replace (value, "o");
			value = u.Replace (value, "u");

			return value;
		}

		private string FormatField (DocumentMetadata metadata, DocumentColumnMetadata columnMetadata, string value)
		{
			if (columnMetadata.IsNullable && string.IsNullOrEmpty (value)) {
				return value;
			}

			var converter = default (Func<string, DocumentMetadata, DocumentColumnMetadata, string>);
			var fieldType = columnMetadata.GetType ();

			if (!this.typeConverters.Any (c => c.Key == fieldType)) {
				fieldType = typeof (string);
			}

			if (!this.typeConverters.TryGetValue (fieldType, out converter)) {
				throw new FieldFormatException (string.Format (Resources.CsvDocumentReader_CantConvertFieldValue, value, columnMetadata.Type, metadata.DocumentName));
			}

			return converter (value, metadata, columnMetadata);
		}

		private string Normalize (string value)
		{
			if (string.IsNullOrWhiteSpace (value) || value.ToLower () == "null") {
				return string.Empty;
			}

			return value;
		}

		private void LoadTypeConverters ()
		{
			this.typeConverters.Add (typeof (string), (value, metadata, columnMetadata) => value);
			this.typeConverters.Add (typeof (bool), (value, metadata, columnMetadata) => {
				var converted = default (bool);

				if (!bool.TryParse (value, out converted)) {
					throw new FieldFormatException (string.Format (Resources.CsvDocumentReader_InvalidFieldFormat, columnMetadata.Name, typeof (bool).Name, metadata.DocumentName));
				}

				return converted.ToString ();
			});
			this.typeConverters.Add (typeof (int), (value, metadata, columnMetadata) => {
				var converted = default (int);

				if (!int.TryParse (value, out converted)) {
					throw new FieldFormatException (string.Format (Resources.CsvDocumentReader_InvalidFieldFormat, columnMetadata.Name, typeof (int).Name, metadata.DocumentName));
				}

				return converted.ToNumberString ();
			});
			this.typeConverters.Add (typeof (double), (value, metadata, columnMetadata) => {
				var converted = default (double);

				if (!double.TryParse (value, out converted)) {
					throw new FieldFormatException (string.Format (Resources.CsvDocumentReader_InvalidFieldFormat, columnMetadata.Name, typeof (double).Name, metadata.DocumentName));
				}

				return converted.ToNumberString ();
			});
			this.typeConverters.Add (typeof (DateTime), (value, metadata, columnMetadata) => {
				var converted = default (DateTime);

				if (!DateTime.TryParse (value, out converted)) {
					throw new FieldFormatException (string.Format (Resources.CsvDocumentReader_InvalidFieldFormat, columnMetadata.Name, typeof (DateTime).Name, metadata.DocumentName));
				}

				return converted.ToString ();
			});
		}
	}
}
