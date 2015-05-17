using System;
using System.Data.Entity;
using Anmat.Server.Core.Data;

namespace Anmat.Server.Core.Tests
{
    public abstract class RepositorySpec<T>  : IDisposable
		where T : DataEntity
    {
        private readonly bool cleanDbWhenFinishes;

        protected SqlRepository<T> testRepository;

		protected RepositorySpec(bool cleanDbWhenFinishes = true)
			: this(new AnmatDataContext(), cleanDbWhenFinishes)
        {
        }

        protected RepositorySpec(DbContext context, bool cleanDbWhenFinishes = true)
        {
            this.cleanDbWhenFinishes = cleanDbWhenFinishes;

			var configuration = new AnmatConfiguration {};

            this.testRepository = new SqlRepository<T>(context, configuration);
        }

        protected static string GetUniqueName(string name = null)
        {
            var uniqueName = Guid.NewGuid().ToString();

            return string.IsNullOrEmpty(name) ? uniqueName : string.Concat(name, "-", uniqueName);
        }

		public void Dispose()
        {
			//this.testRepository.DeleteAll();
        }
    }
}
