using System;
using System.Collections.Generic;
using System.Linq;

namespace Anmat.Server.Core
{
    public class InMemoryRepository<T> : IRepository<T>
        where T : DataEntity
    {
        private readonly IList<T> db;

        public InMemoryRepository()
        {
            this.db = new List<T>();
        }

        public IEnumerable<T> GetAll(Func<T, bool> predicate = null)
        {
            IEnumerable<T> result = this.db;

            if (predicate != null)
            {
                result = result.Where(predicate);
            }

            return result;
        }

        public T Get(Func<T, bool> predicate = null)
        {

            return this.GetAll(predicate).FirstOrDefault();
        }

        public bool Exist(Func<T, bool> predicate = null)
        {
            throw new NotImplementedException();
        }

        public void Create(T dataEntity)
        {
            this.db.Add(dataEntity);
        }

        public void Update(T dataEntity)
        {
            throw new NotImplementedException();
        }

        public void Delete(T dataEntity)
        {
            throw new NotImplementedException();
        }
    }
}
