using System;
using System.Data.SQLite;
using System.IO;
using System.Linq;
using Anmat.Server.Core.Data;
using Anmat.Server.Core.Model;
using Anmat.Server.Core.Services;
using Moq;
using Xunit;

namespace Anmat.Server.Core.Tests
{
	public class SQLiteGeneratorSpec
	{
		private static readonly int version = 2;

		[Fact]
		public void when_generating_database_from_test_document_generator_then_succeeds () 
		{
			var configuration = new AnmatConfiguration {
				DocumentsPath = "GeneratedDb",
				TargetDatabaseName = this.GetDatabaseName("TestDb"),
				ReplaceExistingTargetDatabase = true
			};
			var updateVersion = new UpdateVersion { Number = version, Date = DateTime.UtcNow };
			var job = new DataGenerationJob
			{
				Version = updateVersion.Number,
				Status = DataGenerationJobStatus.InProgress
			};
			
			var jobService = new Mock<IDataGenerationJobService> ();

			jobService
				.Setup (s => s.GetLatestJob ())
				.Returns (job);
			var versionService = new Mock<IVersionService> ();

			versionService
				.Setup (r => r.IncrementVersion())
				.Returns (updateVersion);

			var sqlGenerator = new SQLiteGenerator (jobService.Object, versionService.Object, configuration);
			var documentName = "FooDocument";
			var document = this.GetDocument ();
			var documentGenerator = new Mock<IDocumentGenerator> ();

			documentGenerator
				.Setup (g => g.Name)
				.Returns (documentName);
			documentGenerator
				.Setup(g => g.Metadata)
				.Returns(TestMetadataGenerator.GetTestMetadata(documentName));
			documentGenerator
				.Setup (g => g.Generate ())
				.Returns (document);

			var databaseFileName = sqlGenerator.GenerateDatabase (documentGenerator.Object);

			Assert.True (File.Exists (databaseFileName));
			Assert.False (string.IsNullOrEmpty (sqlGenerator.Script));

			var connectionString = string.Format("Data Source={0};Version=3;", databaseFileName);
			var connection = new SQLiteConnection (connectionString);

			connection.Open ();

			var command = new SQLiteCommand(@"SELECT COUNT(*) FROM " + documentName, connection);
			var read = (long)command.ExecuteScalar ();

			Assert.Equal (document.Rows.Count(), int.Parse(read.ToString()));

			connection.Close ();
		}

		[Fact]
		public void when_database_already_exists_and_configuration_does_not_allow_override_then_fails ()
		{
			var configuration = new AnmatConfiguration {
				DocumentsPath = "GeneratedDb",
				TargetDatabaseName = this.GetDatabaseName("TestDb"),
				ReplaceExistingTargetDatabase = false
			};
			var updateVersion = new UpdateVersion { Number = 2, Date = DateTime.UtcNow };
			var job = new DataGenerationJob
			{
				Version = updateVersion.Number,
				Status = DataGenerationJobStatus.InProgress
			};
			
			var jobService = new Mock<IDataGenerationJobService> ();

			jobService
				.Setup (s => s.GetLatestJob ())
				.Returns (job);
			var versionService = new Mock<IVersionService> ();

			versionService
				.Setup (r => r.IncrementVersion())
				.Returns (updateVersion);

			var sqlGenerator = new SQLiteGenerator (jobService.Object, versionService.Object, configuration);
			var documentGenerator = new Mock<IDocumentGenerator> ();

			var databaseFileName = this.GetDatabaseFileName (configuration, sqlGenerator);

			using(File.Create (databaseFileName)) {};
			
			Assert.Throws<SQLGenerationException>(
				 () => sqlGenerator.GenerateDatabase (documentGenerator.Object));
		}

		private string GetDatabaseFileName(AnmatConfiguration configuration, ISQLGenerator sqlGenerator)
		{
			var databaseFileName = Path.Combine (configuration.GetVersionPath (version: version), configuration.TargetDatabaseName + sqlGenerator.FileExtension);

			if (!Directory.Exists (Path.GetDirectoryName(databaseFileName))) {
				Directory.CreateDirectory (Path.GetDirectoryName(databaseFileName));
			}

			return databaseFileName;
		}

		private string GetDatabaseName(string name)
		{
			return string.Format ("{0}-{1}", name, Guid.NewGuid ().ToString ().Substring (6));
		}

		private Document GetDocument()
		{
			var document = new Document ();
			
			var row = new Row ();

			row.AddCell<string> ("valor1");
			row.AddCell<string> ("valor2");
			row.AddCell<double> ("10.5");
			row.AddCell<bool> ("true");

			document.AddRow (row);

			row = new Row ();

			row.AddCell<string> ("valor3");
			row.AddCell<string> ("valor4");
			row.AddCell<double> ("12.5");
			row.AddCell<bool> ("false");

			document.AddRow (row);
			
			row = new Row ();

			row.AddCell<string> ("valor5");
			row.AddCell<string> ("valor6");
			row.AddCell<double> ("14.5");
			row.AddCell<bool> ("false");

			document.AddRow (row);

			return document;
		}
	}
}
