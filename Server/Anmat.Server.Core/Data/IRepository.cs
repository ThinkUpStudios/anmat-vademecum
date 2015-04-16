using System;
using System.Collections.Generic;

namespace Anmat.Server.Core
{
    public interface IRepository<T> where T : DataEntity
    {
        IEnumerable<T> GetAll(Func<T, bool> predicate = null);

        T Get(Func<T, bool> predicate = null);

        bool Exist(Func<T, bool> predicate = null);

        ///<exception cref="DataException">DataException</exception>
        void Create(T dataEntity);

        ///<exception cref="DataException">DataException</exception>
        void Update(T dataEntity);

        ///<exception cref="DataException">DataException</exception>
        void Delete(T dataEntity);
    }
}
