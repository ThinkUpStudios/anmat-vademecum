using System.Data.Entity;
using Anmat.Server.Core.Tests.TestModels;

namespace Anmat.Server.Core.Tests
{
	public class TestDataContext : DbContext
	{
		public TestDataContext () : base("DefaultConnection")
		{
			//Database.SetInitializer<TestDataContext>(new CreateDatabaseIfNotExists<TestDataContext>());
			//Database.SetInitializer<TestDataContext>(new DropCreateDatabaseIfModelChanges<TestDataContext>());
			//Database.SetInitializer<TestDataContext>(new DropCreateDatabaseAlways<TestDataContext>());
		}

		public DbSet<TestEntity> TestEntities { get; set; }
	}
}
