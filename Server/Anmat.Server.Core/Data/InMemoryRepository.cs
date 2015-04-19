using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;

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

        
        public void Create(T dataEntity)
        {
            this.db.Add(dataEntity);
        }

        public IEnumerable<T> GetAll(Expression<Func<T, bool>> predicate = null)
        {
            IEnumerable<T> result = this.db;

            if (predicate != null)
            {
                result = result.Where(predicate.Compile());
            }

            return result;
        }

        public T Get(Expression<Func<T, bool>> predicate = null)
        {
            return this.GetAll(predicate).FirstOrDefault();
        }

        public bool Exist(System.Linq.Expressions.Expression<Func<T, bool>> predicate = null)
        {
            throw new NotImplementedException();
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
