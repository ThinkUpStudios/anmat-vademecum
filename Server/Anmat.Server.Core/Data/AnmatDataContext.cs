using System.Data.Entity;

namespace Anmat.Server.Core.Data
{
	public class AnmatDataContext : DbContext
	{
		public AnmatDataContext () : base("DefaultConnection")
		{
			//Database.SetInitializer<AnmatDataContext>(new CreateDatabaseIfNotExists<AnmatDataContext>());
			//Database.SetInitializer<AnmatDataContext>(new DropCreateDatabaseIfModelChanges<AnmatDataContext>());
			//Database.SetInitializer<AnmatDataContext>(new DropCreateDatabaseAlways<AnmatDataContext>());
		}

		public DbSet<UpdateVersion> Versions { get; set; }

		public DbSet<DocumentMetadata> Metadata { get; set; }

		public DbSet<DataGenerationJob> Jobs { get; set; }
	}
}
