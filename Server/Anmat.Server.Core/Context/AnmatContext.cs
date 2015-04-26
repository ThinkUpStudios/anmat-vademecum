using System.Collections.Generic;
using System.Configuration;
using Anmat.Server.Core.Data;
using Anmat.Server.Core.Services;

namespace Anmat.Server.Core.Context
{
	public class AnmatContext : IAnmatContext
	{
		private static IAnmatContext context;

		public static IAnmatContext Initialize ()
		{
			if (context != null) {
				return context;
			}

			var configuration = new AnmatConfiguration {
				AnmatDataServiceUrl = ConfigurationManager.AppSettings["AnmatDataServiceUrl"],
				DocumentsPath = ConfigurationManager.AppSettings["DocumentsPath"],
				TargetDatabaseName = ConfigurationManager.AppSettings["TargetDatabaseName"],
				TargetMedicinesTableName = ConfigurationManager.AppSettings["TargetMedicinesTableName"],
				TargetActiveComponentsTableName = ConfigurationManager.AppSettings["TargetActiveComponentsTableName"],
				ReplaceExistingTargetDatabase = bool.Parse(ConfigurationManager.AppSettings["ReplaceExistingTargetDatabase"]),
				FullInitialize = bool.Parse(ConfigurationManager.AppSettings["FullInitialize"]),
				DefaultTextEncoding = ConfigurationManager.AppSettings["DefaultTextEncoding"]
			};
			var dataContext = new AnmatDataContext ();
			var metadataRepository = new SqlRepository<DocumentMetadata>(dataContext, configuration, new DocumentMetadataInitializer(configuration));
			var versionRepository = new SqlRepository<UpdateVersion>(dataContext, configuration, new UpdateVersionInitializer());
			var jobRepository = new SqlRepository<DataGenerationJob>(dataContext, configuration);

			var versionService = new VersionService (versionRepository);
			var metadataService = new DocumentMetadataService(metadataRepository);
			var jobService = new DataGenerationJobService (jobRepository);

			var documentReader = new CsvDocumentReader (configuration);
			var documentGenerators = new List<IDocumentGenerator> ();

			documentGenerators.Add (new DocumentGenerator (configuration.TargetMedicinesTableName, 
				documentReader, jobService, metadataService, configuration));

			documentGenerators.Add (new DocumentGenerator (configuration.TargetActiveComponentsTableName, 
				documentReader, jobService, metadataService, configuration));

			var sqlGenerator = new SQLiteGenerator(jobService, versionService, configuration);

			context = new AnmatContext (versionService, jobService, documentReader, documentGenerators, sqlGenerator, configuration);

			return context;
		}

		private AnmatContext (IVersionService versionService, IDataGenerationJobService jobService, IDocumentReader documentReader, IEnumerable<IDocumentGenerator> documentGenerators, 
			ISQLGenerator sqlGenerator, AnmatConfiguration configuration)
		{
			this.Configuration = configuration;
			this.VersionService = versionService;
			this.JobService = jobService;
			this.DocumentReader = documentReader;
			this.DocumentGenerators = documentGenerators;
			this.SQLGenerator = sqlGenerator;
		}

		public AnmatConfiguration Configuration { get; private set; }

		public IVersionService VersionService { get; private set; }

		public IDataGenerationJobService JobService { get; private set; }

		public IDocumentReader DocumentReader { get; private set; }

		public IEnumerable<IDocumentGenerator> DocumentGenerators { get; private set; }

		public ISQLGenerator SQLGenerator { get; private set; }
	}
}
