using System;
using System.Linq;
using Xunit;
using Xunit.Extensions;

namespace Anmat.Server.Core.Tests
{
    public class CSVDocumentReaderTest
    {
        private static readonly string validFilePath = "valid_format.csv";
        private static readonly string invalidFilePath = "invalid_format.csv";
        private static string fieldWithNull = "field_with_null.csv";
		private readonly IDocumentReader reader;

        public CSVDocumentReaderTest()
        {
            this.reader = new CsvDocumentReader();
        }

        [Fact]
        public void when_then()
        {
            var path = validFilePath;
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "Caso Feliz",
                Columns = columns,
                HasHeader = true
            };

            var document = this.reader.Read(validFilePath, metadata);

            Assert.NotNull(document);
            Assert.Equal(3, document.Rows.Count());
            Assert.True(document.Rows.Select(r => r.Cells.First()).All(c => c.Type == typeof(string)), "Columna no es string");
            Assert.True(document.Rows.Select(r => r.Cells.Skip(1).Take(1).First()).All(c => c.Type == typeof(string)), "Columna no es string");
            Assert.True(document.Rows.Select(r => r.Cells.Skip(2).Take(1).First()).All(c => c.Type == typeof(double)), "Columna no es double");
            Assert.True(document.Rows.Select(r => r.Cells.Skip(3).Take(1).First()).All(c => c.Type == typeof(bool)), "Columna no es boolean");
        }

        [Fact]
        public void when_hasHeaderAndNoHeaderIsExpected_then_Fails()
        {
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "Caso Feliz",
                Columns = columns,
                HasHeader = false
            };


            Assert.Throws<FieldFormatException>(() => { this.reader.Read(validFilePath, metadata); });
        }

        [Fact]
        public void when_fileDoesNotExist_then_fail()
        {
            Assert.Throws<ArgumentException>(() => { this.reader.Read("IOnlyExistInMyDreams.csv", new DocumentMetadata()); });
        }

        [Fact]
        public void when_fileWithDiferentColumnsThanMetadata_then_fail()
        {
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "Sad Case",
                Columns = columns,
                HasHeader = true
            };

            Assert.Throws<DocumentFormatException>(() => { this.reader.Read("wrong_column_count.csv", metadata); });
        }

        [Fact]
        public void when_fileHasInvalidExtension_then_fail()
        {
            Assert.Throws<ArgumentException>(() => { this.reader.Read("invalid_format.txt", new DocumentMetadata()); });
        }

        [Fact]
        public void when_fileHasACoulmnWithInvalidFormat_then_fail()
        {
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "Sad Case",
                Columns = columns,
                HasHeader = true
            };

            Assert.Throws<FieldFormatException>(() => { this.reader.Read(invalidFilePath, metadata); });
        }

        [Fact]
        public void when_fieldHasNullAndIsNullable_then_valueGetsEmpty()
        {
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = "Sad Case",
                Columns = columns,
                HasHeader = true
            };
            var document = this.reader.Read(fieldWithNull, metadata);

            Assert.Equal(string.Empty, document.Rows.First().Cells.First().Value);
        }

        [Theory]
        [InlineData("invalid_format.txt", typeof(ArgumentException), "invalid file extension")]
        [InlineData("invalid_format.csv", typeof(FieldFormatException), "invalid format")]
        [InlineData("wrong_column_count.csv", typeof(DocumentFormatException), "wrong column")]
        [InlineData("IOnlyExistInMyDreams.csv", typeof(ArgumentException), "not existing")]
        public void when_file_has_invalid_format_then_fails(string path, Type exceptionType, string documentName)
        {
			var columns = TestMetadataGenerator.GetColumnsMetadata ();
            var metadata = new DocumentMetadata
            {
                DocumentName = documentName ?? "New Document",
                Columns = columns,
                HasHeader = true
            };

            Assert.Throws(exceptionType, () => { this.reader.Read(path, metadata); });
        }
    }
}
