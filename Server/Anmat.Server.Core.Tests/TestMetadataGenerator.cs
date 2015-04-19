using System;
using System.Collections.Generic;
using System.Linq;

namespace Anmat.Server.Core.Tests
{
	public class TestMetadataGenerator
	{
		public static DocumentMetadata GetTestMetadata(string documentName = null)
		{
			var metadata = new DocumentMetadata
            {
                DocumentName = documentName ?? string.Format("Test Metadata {0}", Guid.NewGuid().ToString().Substring(4)),
                Columns = GetColumnsMetadata(),
                HasHeader = false
            };

			return metadata;
		}

		public static List<DocumentColumnMetadata> GetColumnsMetadata()
		{
			var columns = new List<DocumentColumnMetadata>();

            columns.Add(new DocumentColumnMetadata
            {
                ColumnNumber = 0,
                Name = "nombre",
                Type = typeof(string).ToString(),
                IsNullable = true
            });

            columns.Add(new DocumentColumnMetadata
            {
                ColumnNumber = 1,
                Name = "descripcion",
                Type = typeof(string).ToString(),
                IsNullable = true
            });

            columns.Add(new DocumentColumnMetadata
            {
                ColumnNumber = 2,
                Name = "valor",
                Type = typeof(double).ToString(),
                IsNullable = true
            });

            columns.Add(new DocumentColumnMetadata
            {
                ColumnNumber = 3,
                Name = "ok",
                Type = typeof(bool).ToString(),
                IsNullable = true
            });

            return columns;
		}
	}
}
