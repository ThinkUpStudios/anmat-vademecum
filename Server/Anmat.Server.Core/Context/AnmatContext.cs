using System.Collections.Generic;
using System.Configuration;
using Anmat.Server.Core.Data;

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
				DocumentsPath = ConfigurationManager.AppSettings["DocumentsPath"],
				SourceDatabaseName = ConfigurationManager.AppSettings["SourceDatabaseName"],
				TargetDatabaseName = ConfigurationManager.AppSettings["TargetDatabaseName"],
				TargetMedicinesTableName = ConfigurationManager.AppSettings["TargetMedicinesTableName"],
				TargetActiveComponentsTableName = ConfigurationManager.AppSettings["TargetActiveComponentsTableName"],
				ReplaceExistingTargetDatabase = bool.Parse(ConfigurationManager.AppSettings["ReplaceExistingTargetDatabase"]),
				FullInitialize = bool.Parse(ConfigurationManager.AppSettings["FullInitialize"]),
				DefaultTextEncoding = ConfigurationManager.AppSettings["DefaultTextEncoding"]
			};
			var metadataRepository = new MongoRepository<DocumentMetadata>(configuration, new DocumentMetadataInitializer(configuration));
			var versionRepository = new MongoRepository<UpdateVersion>(configuration, new UpdateVersionInitializer());
			var documentReader = new CsvDocumentReader (configuration);
			var documentGenerators = new List<IDocumentGenerator> ();

			documentGenerators.Add (new DocumentGenerator (configuration.TargetMedicinesTableName, 
				documentReader, metadataRepository, versionRepository, configuration));

			documentGenerators.Add (new DocumentGenerator (configuration.TargetActiveComponentsTableName, 
				documentReader, metadataRepository, versionRepository, configuration));

			var sqlGenerator = new SQLiteGenerator(versionRepository, configuration);

			context = new AnmatContext (documentReader, documentGenerators, sqlGenerator, configuration);

			return context;
		}

		private AnmatContext (IDocumentReader documentReader, IEnumerable<IDocumentGenerator> documentGenerators, 
			ISQLGenerator sqlGenerator, AnmatConfiguration configuration)
		{
			this.Configuration = configuration;
			this.DocumentReader = documentReader;
			this.DocumentGenerators = documentGenerators;
			this.SQLGenerator = sqlGenerator;
		}

		public AnmatConfiguration Configuration { get; private set; }

		public IDocumentReader DocumentReader { get; private set; }

		public IEnumerable<IDocumentGenerator> DocumentGenerators { get; private set; }

		public ISQLGenerator SQLGenerator { get; private set; }
	}
}
