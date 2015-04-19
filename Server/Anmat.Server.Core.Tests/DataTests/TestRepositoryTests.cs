using Anmat.Server.Core.Tests.DataTests;
using Anmat.Server.Core.Tests.TestModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace Anmat.Server.Core.Tests.DataTests
{
    public class TestRepositoryTests : RepositoryTests<TestEntity>, IDisposable
    {


        [Fact]
        public void UT_When_CreateTestEntity_Then_Success()
        {
            var testEntityName = GetUniqueName("test");
            var testEntity = new TestEntity
            {
                Name = testEntityName,
                DisplayName = GetUniqueName("Test"),
                IsValid = true
            };

            this.testRepository.Create(testEntity);

            var createdTestEntity = this.testRepository.Get(e => e.Name == testEntityName);

            Assert.NotNull(createdTestEntity);
            Assert.Equal(testEntity.Id, createdTestEntity.Id);
            Assert.Equal(testEntity.DisplayName, createdTestEntity.DisplayName);
            Assert.Equal(true, createdTestEntity.IsValid);
            
        }

        [Fact]
        public void UT_When_UpdateTestEntity_Then_Success()
        {
            var testEntityName = GetUniqueName("test");
            var testEntity = new TestEntity
            {
                Name = testEntityName,
                DisplayName = GetUniqueName("Test"),
                IsValid = true
            };

            this.testRepository.Create(testEntity);

            var createdTestEntity = this.testRepository.Get(e => e.Name == testEntityName);

            createdTestEntity.IsValid = false;
            createdTestEntity.DisplayName = GetUniqueName("Updated Test");

            this.testRepository.Update(createdTestEntity);

            var updatedTestEntity = this.testRepository.Get(e => e.Name == testEntityName);

            Assert.NotNull(updatedTestEntity);
            Assert.Equal(testEntity.Id, updatedTestEntity.Id);
            Assert.Equal(createdTestEntity.DisplayName, updatedTestEntity.DisplayName);
            Assert.Equal(false, updatedTestEntity.IsValid);
        }

        [Fact]
        public void UT_When_DeleteTestEntity_Then_Success()
        {
            var testEntityName = GetUniqueName("test");
            var testEntity = new TestEntity
            {
                Name = testEntityName,
                DisplayName = GetUniqueName("Test"),
                IsValid = true
            };

            this.testRepository.Create(testEntity);

            var createdTestEntity = this.testRepository.Get(e => e.Name == testEntityName);

            this.testRepository.Delete(createdTestEntity);

            var deletedTestEntity = this.testRepository.Get(e => e.Name == testEntityName);

            Assert.Null(deletedTestEntity);


        }

        [Fact]
        public void UT_When_GetTestEntitiesWithPredicate_Then_Success()
        {
            var testEntityName1 = GetUniqueName("test");
            var testEntity1 = new TestEntity
            {
                Name = testEntityName1,
                DisplayName = GetUniqueName("Test"),
                IsValid = true
            };
            var testEntityName2 = GetUniqueName("test");
            var testEntity2 = new TestEntity
            {
                Name = testEntityName2,
                DisplayName = GetUniqueName("Test"),
                IsValid = false
            };
            var testEntityName3 = GetUniqueName("test");
            var testEntity3 = new TestEntity
            {
                Name = testEntityName3,
                DisplayName = GetUniqueName("Test"),
                IsValid = true
            };
            var testEntityName4 = GetUniqueName("entity");
            var testEntity4 = new TestEntity
            {
                Name = testEntityName4,
                DisplayName = GetUniqueName("Entity"),
                IsValid = true
            };

            this.testRepository.Create(testEntity1);
            this.testRepository.Create(testEntity2);
            this.testRepository.Create(testEntity3);
            this.testRepository.Create(testEntity4);

            var validEntities = this.testRepository.GetAll(e => e.IsValid);
            var testEntities = this.testRepository.GetAll(e => e.Name.StartsWith("test"));
            var entitiesWith4 = this.testRepository.GetAll(e => e.DisplayName.Contains("Entity"));

            Assert.Equal(3, validEntities.Count());
            Assert.Equal(3, testEntities.Count());
            Assert.Equal(1, entitiesWith4.Count());
        }

        public void Dispose()
        {
            this.testRepository.DeleteAll();
        }
    }
}
