using System;
using System.Collections.Generic;
using System.Linq.Expressions;

namespace Anmat.Server.Core.Data
{
    public interface IRepository<T> where T : DataEntity
    {
        IEnumerable<T> GetAll(Expression<Func<T, bool>> predicate = null);

        T Get(Expression<Func<T, bool>> predicate = null);

        bool Exist(Expression<Func<T, bool>> predicate = null);

        ///<exception cref="DataException">DataException</exception>
        void Create(T dataEntity);

        ///<exception cref="DataException">DataException</exception>
        void Update(T dataEntity);

        ///<exception cref="DataException">DataException</exception>
        void Delete(T dataEntity);
    }
}
