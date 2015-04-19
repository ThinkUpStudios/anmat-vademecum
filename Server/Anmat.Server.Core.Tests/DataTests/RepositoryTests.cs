using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core.Tests.DataTests
{
    
    public abstract class RepositoryTests<T> where T : DataEntity
    {
        private readonly bool cleanDbWhenFinishes;

        protected MongoRepository<T> testRepository;

        protected RepositoryTests(bool cleanDbWhenFinishes = true)
        {
            this.cleanDbWhenFinishes = cleanDbWhenFinishes;

            var dataBaseName = "testDB";

            this.testRepository = new MongoRepository<T>(dataBaseName);
        }


        protected static string GetUniqueName(string name = null)
        {
            var uniqueName = Guid.NewGuid().ToString();

            return string.IsNullOrEmpty(name) ? uniqueName : string.Concat(name, "-", uniqueName);
        }
    }
}
