using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace Anmat.Server.Core.Tests.DataTests
{
    public class MetadataRepositoryTest : RepositoryTests<DocumentMetadata>
    {
        [Fact]
        public void UT_When_CreateMetadataRepository_Then_Success()
        {
            var metadataName = GetUniqueName("documentMetadata");
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
        public void UT_When_UpdateMetadata_Then_Success()
        {
            var metadataName = GetUniqueName("documentMetadata");
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
            createdTestEntity.DocumentName = GetUniqueName("Updated Test");

            this.testRepository.Update(createdTestEntity);

            var updatedTestEntity = this.testRepository.Get(e => e.DocumentName == createdTestEntity.DocumentName);

            Assert.NotNull(updatedTestEntity);
            Assert.Equal(createdTestEntity.Id, updatedTestEntity.Id);
            Assert.Equal(createdTestEntity.DocumentName, updatedTestEntity.DocumentName);
            Assert.Equal(false, updatedTestEntity.HasHeader);
        }

        
        [Fact]
        public void UT_When_DeleteTestEntity_Then_Success()
        {
            var metadataName = GetUniqueName("documentMetadata");
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
        public void UT_When_GetTestEntitiesWithPredicate_Then_Success()
        {

            var metadataName = GetUniqueName("documentMetadata");
            var columns = GetTestMetadataColumns();
            var metadata = new DocumentMetadata
            {
                DocumentName = metadataName,
                Columns = columns,
                HasHeader = true
            };

            var metadataName2 = GetUniqueName("documentMetadata2");
            var metadata2 = new DocumentMetadata
            {
                DocumentName = metadataName2,
                Columns = columns,
                HasHeader = false
            };

            var metadataName3 = GetUniqueName("testMetadata3");
            var metadata3 = new DocumentMetadata
            {
                DocumentName = metadataName3,
                Columns = columns,
                HasHeader = true
            };

            this.testRepository.Create(metadata);
            this.testRepository.Create(metadata2);
            this.testRepository.Create(metadata3);

            var hasHeader = this.testRepository.GetAll(e => e.HasHeader);
            var startsWithTest = this.testRepository.Get(e => e.DocumentName.StartsWith("test"));
            var startsWithDoc = this.testRepository.GetAll(e => e.DocumentName.StartsWith("docume"));

            Assert.Equal(2, hasHeader.Count());
            Assert.Equal("testMetadata3", startsWithTest.DocumentName);
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
