using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.IO;
using System.Text;
using System.Linq;

namespace Anmat.Server.Core
{
	public class SQLiteGenerator : ISQLGenerator
    {
		private readonly StringBuilder scriptBuilder;
		private readonly DatabaseConfiguration configuration;

		public SQLiteGenerator (DatabaseConfiguration configuration)
		{
			this.scriptBuilder = new StringBuilder ();
			this.configuration = configuration;
		}

		public string DatabaseName { get; private set; }

        public string Script { get; private set; }

        public void GenerateDatabase(params IDocumentGenerator[] documentGenerators)
        {
			this.scriptBuilder.Clear ();
			if (!Directory.Exists (this.configuration.Path)) {
				Directory.CreateDirectory (this.configuration.Path);
			}
			var databaseFileName = Path.Combine (this.configuration.Path, this.configuration.DatabaseName + ".sqlite");
			
			if(!configuration.ReplaceIfExist && File.Exists(databaseFileName)) {
				throw new SQLGenerationException ("File already Exist");
			}

			SQLiteConnection.CreateFile (databaseFileName);

			var connectionString = string.Format("Data Source={0};Version=3;", databaseFileName);
			var connection = new SQLiteConnection (connectionString);

			connection.Open ();

			foreach (var documentGenerator in documentGenerators) {
				var document = documentGenerator.Generate ();
				var tableScript = this.GetCreateTableScript(documentGenerator.Metadata);
				var tableCommand = new SQLiteCommand(tableScript, connection);

				tableCommand.ExecuteNonQuery();
				this.scriptBuilder.AppendLine (tableScript);

				foreach(var row in document.Rows) {
					var insertScript = this.GetInsertScript(documentGenerator.Metadata, row);
					var insertCommand = new SQLiteCommand(insertScript, connection);

					insertCommand.ExecuteNonQuery();
					this.scriptBuilder.AppendLine(insertScript);
				}
			}

			connection.Close ();

			this.Script = scriptBuilder.ToString ();
        }

		private string GetCreateTableScript(DocumentMetadata metadata)
		{
			var scriptBuilder = new StringBuilder ();

			scriptBuilder.Append ("CREATE TABLE ");
			scriptBuilder.Append (metadata.DocumentName);
			scriptBuilder.Append ("(");

			var i = 1;

			foreach (var columnMetadaData in metadata.Columns)
			{
				 scriptBuilder.Append (string.Format ("{0} {1}", columnMetadaData.Name, this.GetSQLiteType (columnMetadaData.Type)));

				 if (i < metadata.Columns.Count ()) {
					scriptBuilder.Append (", ");
				 }

				i++;
			}

			scriptBuilder.Append(");");

			return scriptBuilder.ToString ();
		}

		private string GetInsertScript (DocumentMetadata metadata, Row row)
		{
			var scriptBuilder = new StringBuilder ();

			scriptBuilder.Append ("INSERT INTO ");
			scriptBuilder.Append (metadata.DocumentName);
			scriptBuilder.Append ("(");

			var columnNames = metadata.Columns.Select (c => c.Name);
			var columns = string.Join (", ", columnNames);

			scriptBuilder.Append (columns);
			scriptBuilder.Append (") VALUES (");

			for (var i = 0; i < row.Cells.Count (); i++) {
				var cell = row.Cells.ElementAt (i);

				if(cell.Type == typeof(string) || cell.Type == typeof(DateTime)) {
					scriptBuilder.Append (string.Format("'{0}'", cell.Value));
				} else if(cell.Type == typeof(bool))
				{
					scriptBuilder.Append (string.Format ("{0}", cell.Value.ToLower () == "true" ? 1 : 0));
				}else
				{
					scriptBuilder.Append (string.IsNullOrEmpty(cell.Value) ? "NULL" : cell.Value);	
				}

				if (i == row.Cells.Count () - 1) {
					scriptBuilder.Append (");");
				} else {
					scriptBuilder.Append (", ");
				}
			}

			return scriptBuilder.ToString ();
		}

		private string GetSQLiteType(Type columnType)
		{
			return columnType == typeof (double) ? "REAL" :
				columnType == typeof (int) ? "INT" :
						columnType == typeof (bool) ? "INT" :
				"TEXT";
		}
	}
}
