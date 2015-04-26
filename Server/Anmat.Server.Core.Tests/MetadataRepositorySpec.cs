using System.Collections.Generic;
using System.Linq;
using Anmat.Server.Core.Data;
using Xunit;

namespace Anmat.Server.Core.Tests
{
    public class MetadataRepositorySpec : RepositorySpec<DocumentMetadata>
    {
        [Fact]
        public void when_creating_metadata_then_succeeds()
        {
            var metadataName = GetUniqueName("TestMetadata");
            var columns = GetTestMetadataColumns();
            var metadata = new DocumentMetadata
            {
                DocumentName = metadataName,
                Columns = columns,
                HasHeader = true
            };

            this.testRepository.Create(metadata);

            var createdTestEntity = this.testRepository.Get(e => e.DocumentName == metadataName);

            Assert.NotNull(createdTestEntity);
            Assert.Equal(metadata.Id, createdTestEntity.Id);
            Assert.Equal(metadata.DocumentName, createdTestEntity.DocumentName);
            Assert.Equal(true, createdTestEntity.HasHeader);
        }
        
        [Fact]
        public void when_updating_metadata_then_succeeds()
        {
            var metadataName = GetUniqueName("TestMetadata");
            var columns = GetTestMetadataColumns();
            var metadata = new DocumentMetadata
            {
                DocumentName = metadataName,
                Columns = columns,
                HasHeader = true
            };

            this.testRepository.Create(metadata);

            var createdTestEntity = this.testRepository.Get(e => e.DocumentName == metadataName);

            createdTestEntity.HasHeader = false;
            createdTestEntity.DocumentName = GetUniqueName("UpdatedTestMetadata");

            this.testRepository.Update(createdTestEntity);

            var updatedTestEntity = this.testRepository.Get(e => e.DocumentName == createdTestEntity.DocumentName);

            Assert.NotNull(updatedTestEntity);
            Assert.Equal(createdTestEntity.Id, updatedTestEntity.Id);
            Assert.Equal(createdTestEntity.DocumentName, updatedTestEntity.DocumentName);
            Assert.Equal(false, updatedTestEntity.HasHeader);
        }
        
        [Fact]
        public void when_deleting_metadata_then_succeeds()
        {
            var metadataName = GetUniqueName("TestMetadata");
            var columns = GetTestMetadataColumns();
            var metadata = new DocumentMetadata
            {
                DocumentName = metadataName,
                Columns = columns,
                HasHeader = true
            };

            this.testRepository.Create(metadata);

            var createdTestEntity = this.testRepository.Get(e => e.DocumentName == metadataName);

            this.testRepository.Delete(createdTestEntity);

            var deletedTestEntity = this.testRepository.Get(e => e.DocumentName == metadataName);

            Assert.Null(deletedTestEntity);
        }

        [Fact]
        public void when_getting_metadata_with_predicate_then_succeeds()
        {
            var metadataName = GetUniqueName("DocumentMetadata");
            var columns = GetTestMetadataColumns();
            var metadata = new DocumentMetadata
            {
                DocumentName = metadataName,
                Columns = columns.ToList(),
                HasHeader = true
            };

            var metadataName2 = GetUniqueName("DocumentMetadata2");
            var metadata2 = new DocumentMetadata
            {
                DocumentName = metadataName2,
                Columns = columns.ToList(),
                HasHeader = false
            };

            var metadataName3 = GetUniqueName("TestMetadata");
            var metadata3 = new DocumentMetadata
            {
                DocumentName = metadataName3,
                Columns = columns.ToList(),
                HasHeader = true
            };

            this.testRepository.Create(metadata);
            this.testRepository.Create(metadata2);
            this.testRepository.Create(metadata3);

            var hasHeader = this.testRepository.GetAll(e => e.HasHeader);
            var startsWithTest = this.testRepository.Get(e => e.DocumentName.StartsWith("Test"));
            var startsWithDoc = this.testRepository.GetAll(e => e.DocumentName.StartsWith("Doc"));

            Assert.Equal(2, hasHeader.Count());
            Assert.True(startsWithTest.DocumentName.Contains("TestMetadata"));
            Assert.Equal(2, startsWithDoc.Count());
        }
        
        private static List<DocumentColumnMetadata> GetTestMetadataColumns()
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
