using System;
using System.Linq;
using Anmat.Server.Core.Data;
using Anmat.Server.Core.Exceptions;
using Xunit;
using System.Collections.Generic;
using System.Globalization;
using System.IO;

namespace Anmat.Server.Core.Tests
{
    public class CsvDocumentReaderSpec : IDisposable
    {
		private IDocumentReader reader;

        public CsvDocumentReaderSpec()
        {
            this.reader = new CsvDocumentReader(new AnmatConfiguration { DefaultTextEncoding = "ISO-8859-1" });
        }

        [Fact]
        public void when_reading_valid_file_with_corresponding_metadata_then_succeeds()
        {
            var path = @"Files\valid_format.csv";
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "ValidFile",
                Columns = columns,
                HasHeader = true
            };

            var document = this.reader.Read(path, metadata);

            Assert.NotNull(document);
            Assert.Equal(3, document.Rows.Count());
            Assert.True(document.Rows.Select(r => r.Cells.First()).All(c => c.Type == typeof(string)), "Columna no es string");
            Assert.True(document.Rows.Select(r => r.Cells.Skip(1).Take(1).First()).All(c => c.Type == typeof(string)), "Columna no es string");
            Assert.True(document.Rows.Select(r => r.Cells.Skip(2).Take(1).First()).All(c => c.Type == typeof(double)), "Columna no es double");
            Assert.True(document.Rows.Select(r => r.Cells.Skip(3).Take(1).First()).All(c => c.Type == typeof(bool)), "Columna no es boolean");
        }

        [Fact]
        public void when_reading_valid_file_with_header_and_metadata_expects_no_header_then_fails()
        {
			var path = @"Files\valid_format.csv";
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "ValidFileWithHeader",
                Columns = columns,
                HasHeader = false
            };

            Assert.Throws<FieldFormatException>(() => { this.reader.Read(path, metadata); });
        }

        [Fact]
        public void when_reading_not_existing_file_then_fails()
        {
            Assert.Throws<ArgumentException>(() => { this.reader.Read("not_existing_file.csv", new DocumentMetadata()); });
        }

        [Fact]
        public void when_reading_file_with_different_columns_than_metadata_then_fails()
        {
			var path = @"Files\wrong_column_count.csv";
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "FileWithDifferentColumns",
                Columns = columns,
                HasHeader = true
            };

            Assert.Throws<DocumentFormatException>(() => { this.reader.Read(path, metadata); });
        }

        [Fact]
        public void when_reading_file_with_invalid_extension_then_fails()
        {
			var path = @"Files\invalid_file_extension.txt";
            Assert.Throws<ArgumentException>(() => { this.reader.Read(path, new DocumentMetadata()); });
        }

        [Fact]
        public void when_reading_file_with_invalid_columns_format_then_fails()
        {
			var path = @"Files\invalid_format.csv";
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "FileWithInvalidColumnsFormat",
                Columns = columns,
                HasHeader = true
            };

            Assert.Throws<FieldFormatException>(() => { this.reader.Read(path, metadata); });
        }

        [Fact]
        public void when_reading_file_with_nullable_fields_and_metadata_has_nullable_then_gets_empty_value()
        {
			var path = @"Files\field_with_null.csv";
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "FileWithNullFields",
                Columns = columns,
                HasHeader = true
            };
            var document = this.reader.Read(path, metadata);

            Assert.Equal(string.Empty, document.Rows.First().Cells.First().Value);
        }

		[Fact]
        public void when_reading_file_with_metadata_with_removableAccents_and_upperCase_then_gets_removableAccents_and_upperCase_value()
        {
            
            var path = @"Files\valid_format_upper.csv";
            var columns = new List<DocumentColumnMetadata>();

            columns.Add(new DocumentColumnMetadata
            {
                ColumnNumber = 0,
                Name = "nombre",
                Type = typeof(string).ToString(),
                IsNullable = true,
                RemovableAccents = true,
                UpperCase = true
            });

            columns.Add(new DocumentColumnMetadata
            {
                ColumnNumber = 1,
                Name = "descripcion",
                Type = typeof(string).ToString(),
                IsNullable = true,
                RemovableAccents = false,
                UpperCase = false
            });

            columns.Add(new DocumentColumnMetadata
            {
                ColumnNumber = 2,
                Name = "valor",
                Type = typeof(double).ToString(),
                IsNullable = true,
                RemovableAccents = false,
                UpperCase = false
            });

            columns.Add(new DocumentColumnMetadata
            {
                ColumnNumber = 3,
                Name = "ok",
                Type = typeof(bool).ToString(),
                IsNullable = true,
                RemovableAccents = false,
                UpperCase = false
            });

            var metadata = new DocumentMetadata
            {
                DocumentName = "FileWithLatinChars",
                Columns = columns,
                HasHeader = true
            };

            var document = this.reader.Read(path, metadata);

            Assert.Equal("PAPEPIPOPU PAPEPIPOPU", document.Rows.First().Cells.First().Value);
            
        }
		
		[Fact]
		public void when_reading_file_with_decimal_numbers_then_conversion_is_successfully()
		{
			var path = @"Files\number_format.csv";
            var metadata = new DocumentMetadata
            {
                DocumentName = "ValidFile",
                Columns = new List<DocumentColumnMetadata> {
					new DocumentColumnMetadata { 
						ColumnNumber = 0,
						Name = "nombre",
						Type = typeof(string).Name
					},
					new DocumentColumnMetadata { 
						ColumnNumber = 1,
						Name = "valor",
						Type = typeof(double).Name
					}
				},
                HasHeader = true
            };

            var document = this.reader.Read(path, metadata);
			var expectedNumbers = File.ReadAllLines (path).Skip (1).Select (l => l.Split (';').Skip (1).First ());

			Assert.NotNull (document);
			Assert.Equal(9, document.Rows.Count());

			var convertedNumbers = document.Rows.Select (r => r.Cells.Skip(1).First().Value);


		}

		public void Dispose ()
		{
			this.reader = null;
		}
	}
}
