using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace Anmat.Server.Core.Tests
{
    class FooTests
    {
        [Fact]
        public void when_then()
        {
            var path = @"C:\Users\dcamarro\Documents\medicamentos.csv";
            var reader = new CsvDocumentReader();
            var repository = new InMemoryRepository<DocumentMetadata>();
            var medicamentosGenerator = new MedicamentosGenerator(path, reader, repository);
            var sqliteGenerator = new SQLiteGenerator();

            sqliteGenerator.Generate(medicamentosGenerator);

            var script = sqliteGenerator.Script;
        }
    }
}
