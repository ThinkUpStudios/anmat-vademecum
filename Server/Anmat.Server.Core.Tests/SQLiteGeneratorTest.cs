using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Moq;
using Xunit;

namespace Anmat.Server.Core.Tests
{
	class SQLiteGeneratorTest : IDisposable
	{
		private ISQLGenerator generator;
		private DatabaseConfiguration databaseConfig;
		


		public SQLiteGeneratorTest ()
		{
			this.databaseConfig = new DatabaseConfiguration {
				DatabaseName = "PruebaDB",
				Path = "defaultPath",
				ReplaceIfExist = true,
				Version = "1"
			};
			this.generator = new SQLiteGenerator (this.databaseConfig);
			

		}
		private string GetTestFileName (string name)
		{
			return Path.Combine (this.databaseConfig.Path, name + ".sqlite");
		}

		[Fact]
		public void when_generatedDB_then_succeeds () 
		{
			var documentName = "FooDocument";
			var document = new Document ();
			
			var row = new Row ();
			row.Add<string> ("valor1");
			row.Add<string> ("valor2");
			row.Add<double> ("10.5");
			row.Add<bool> ("true");
			document.Add (row);

			row = new Row ();
			row.Add<string> ("valor3");
			row.Add<string> ("valor4");
			row.Add<double> ("12.5");
			row.Add<bool> ("false");
			document.Add (row);
			
			row = new Row ();
			row.Add<string> ("valor5");
			row.Add<string> ("valor6");
			row.Add<double> ("14.5");
			row.Add<bool> ("false");
			document.Add (row);
			this.databaseConfig.DatabaseName = "Test1";
			var docGenerator = new Mock<IDocumentGenerator> ();
			docGenerator
				.Setup (generator => generator.Name)
				.Returns (documentName);
			docGenerator
				.Setup(generator => generator.Metadata)
				.Returns(TestMetadataGenerator.GetTestMetadata(documentName));
			docGenerator
				.Setup (generator => generator.Generate ())
				.Returns (document);

			this.generator.GenerateDatabase (docGenerator.Object);
			Assert.True (File.Exists (this.GetTestFileName ("Test1")));
			Assert.False (string.IsNullOrEmpty (this.generator.Script));
			var connectionString = string.Format("Data Source={0};Version=3;", this.GetTestFileName ("Test1"));
			var connection = new SQLiteConnection (connectionString);

			connection.Open ();

			SQLiteCommand sqlComm = new SQLiteCommand(@"SELECT COUNT(*) FROM " + documentName,connection);
			var read = (long)sqlComm.ExecuteScalar ();
			Assert.Equal (document.Rows.Count(), int.Parse(read.ToString()));

			connection.Close ();
		}

		[Fact]
		public void when_fileAlreadyExistAndNotOverrideFlag_then_fail ()
		{
			this.databaseConfig.ReplaceIfExist = false;
			var docGenerator = new Mock<IDocumentGenerator> ();			
			using(File.Create (this.GetTestFileName ("Test2"))) {};
			
			Exception ex = Assert.Throws<SQLGenerationException>(
				 () => this.generator.GenerateDatabase (docGenerator.Object));
			
		}

		public void Dispose ()
		{
			if (File.Exists (this.GetTestFileName ("Test1"))){ 
				File.Delete (this.GetTestFileName ("Test1"));
			}
			if (File.Exists (this.GetTestFileName ("Test2"))) {
				File.Delete (this.GetTestFileName ("Test2"));
			}
		}
	}
}
