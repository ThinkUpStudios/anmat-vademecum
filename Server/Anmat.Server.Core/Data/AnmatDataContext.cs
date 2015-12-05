using System.Data.Entity;

namespace Anmat.Server.Core.Data
{
	public class AnmatDataContext : DbContext
	{
		public AnmatDataContext (AnmatConfiguration configuration = null) : base("DefaultConnection")
		{
			//Database.SetInitializer<AnmatDataContext> (new CreateDatabaseIfNotExists<AnmatDataContext> ());

			if (configuration != null && configuration.FullInitialize) {
				Database.SetInitializer (new DropCreateDatabaseAlways<AnmatDataContext> ());
			} else {
				Database.SetInitializer (new DropCreateDatabaseIfModelChanges<AnmatDataContext> ());
			}
		}

		public DbSet<UpdateVersion> Versions { get; set; }

		public DbSet<DocumentMetadata> Metadata { get; set; }

		public DbSet<DataGenerationJob> Jobs { get; set; }

		protected override void OnModelCreating (DbModelBuilder modelBuilder)
		{
			base.OnModelCreating (modelBuilder);

			modelBuilder.Entity<DocumentMetadata>()
				.HasMany(d => d.Columns).WithOptional(c => c.Metadata)
				.WillCascadeOnDelete(true);
		}
	}
}
