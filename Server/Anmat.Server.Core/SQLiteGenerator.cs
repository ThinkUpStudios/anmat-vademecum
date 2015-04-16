using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    class SQLiteGenerator : ISQLGenerator
    {
        public string Script { get; set; }

        public void Generate(params IDocumentGenerator[] documentGenerators)
        {
            var db = this.CreateDataBase();

            foreach (var documentGenerator in documentGenerators)
            {
                var document = documentGenerator.Generate();

                this.CreateTable(document, db);
            }

            this.Save(db);
        }

        private object CreateDataBase()
        {
            throw new NotImplementedException();
        }

        private void CreateTable(Document document, object db)
        {
            throw new NotImplementedException();
        }

        private void Save(object db)
        {
            throw new NotImplementedException();
        }
    }
}
