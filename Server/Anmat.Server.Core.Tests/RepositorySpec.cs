using System;
using Anmat.Server.Core.Data;

namespace Anmat.Server.Core.Tests
{
    public abstract class RepositorySpec<T>  : IDisposable
		where T : DataEntity
    {
        private readonly bool cleanDbWhenFinishes;

        protected MongoRepository<T> testRepository;

        protected RepositorySpec(bool cleanDbWhenFinishes = true)
        {
            this.cleanDbWhenFinishes = cleanDbWhenFinishes;

			var configuration = new AnmatConfiguration { SourceDatabaseConnectionString = "mongodb://localhost", SourceDatabaseName = "TestDatabase" };

            this.testRepository = new MongoRepository<T>(configuration);
        }

        protected static string GetUniqueName(string name = null)
        {
            var uniqueName = Guid.NewGuid().ToString();

            return string.IsNullOrEmpty(name) ? uniqueName : string.Concat(name, "-", uniqueName);
        }

		public void Dispose()
        {
            this.testRepository.DeleteAll();
        }
    }
}
