using Moq;
using System.Linq;
using Xunit;
using System.IO;
using System.Data.SQLite;

namespace Anmat.Server.Core.Tests
{
    public class RepositoryTests
    {
        [Fact]
        public void Test()
        {
            var repository = new InMemoryRepository<Foo>();

            repository.Create(new Foo { Name = "Foo1", Value = 20 });
            repository.Create(new Foo { Name = "Foo2", Value = 120 });

            var result = repository.GetAll(foo => foo.Value > 100);

            Assert.Equal(1, result.Count());
        }

		[Fact]
		public void misc()
		{
			var file = "test1.sqlite";

			using (var stream = File.Create (file)) { };

			SQLiteConnection.CreateFile (file);
		}
    }
}
