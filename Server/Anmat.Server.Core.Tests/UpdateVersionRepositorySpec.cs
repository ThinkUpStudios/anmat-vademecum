using System;
using System.Linq;
using Anmat.Server.Core.Data;
using Xunit;

namespace Anmat.Server.Core.Tests
{
    public class UpdateVersionRepositorySpec : RepositorySpec<UpdateVersion>
    {
        [Fact]
        public void when_creating_version_then_succeeds()
        {
			var number = this.GetVersion();
            var version = new UpdateVersion
            {
                Number = number,
				Date = DateTime.UtcNow
            };

            this.testRepository.Create(version);

            var createdversion = this.testRepository.Get(v => v.Number == number);

            Assert.NotNull(createdversion);
            Assert.Equal(version.Id, createdversion.Id);
            Assert.Equal(version.Number, createdversion.Number);

			this.testRepository.Delete (createdversion);
        }
        
        [Fact]
        public void when_deleting_version_then_succeeds()
        {
           var number = this.GetVersion();
            var version = new UpdateVersion
            {
                Number = number,
				Date = DateTime.UtcNow
            };

            this.testRepository.Create(version);

            var createdversion = this.testRepository.Get(v => v.Number == number);

            this.testRepository.Delete(createdversion);

            var deletedTestEntity = this.testRepository.Get(v => v.Number == number);

            Assert.Null(deletedTestEntity);

			this.testRepository.Delete (createdversion);
        }

        [Fact]
        public void when_getting_metadata_with_predicate_then_succeeds()
        {
			var number1 = this.GetVersion ();
            var version1 = new UpdateVersion
            {
                Number = number1,
				Date = DateTime.UtcNow.AddDays(-2)
            };
			var number2 = this.GetVersion ();
			var version2 = new UpdateVersion
            {
                Number = number2,
				Date = DateTime.UtcNow.AddDays(-1)
            };
			var number3 = this.GetVersion ();
			var version3 = new UpdateVersion
            {
                Number = number3,
				Date = DateTime.UtcNow
            };

            this.testRepository.Create(version1);
			this.testRepository.Create(version2);
			this.testRepository.Create(version3);

            Assert.NotNull(this.testRepository.Get(v => v.Number == number1));
			Assert.NotNull(this.testRepository.Get(v => v.Number == number2));
			Assert.NotNull(this.testRepository.Get(v => v.Number == number3));

			this.testRepository.Delete (version1);
			this.testRepository.Delete (version2);
			this.testRepository.Delete (version3);
        }
        
		private int GetVersion()
		{
			var versions = this.testRepository.GetAll ();
			var lastVersion = versions.Any() ? versions.Max (v => v.Number) : 1;

			return lastVersion;
		}
    }
}
