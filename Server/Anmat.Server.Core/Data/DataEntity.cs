using System;
using System.ComponentModel.DataAnnotations;

namespace Anmat.Server.Core.Data
{
    public abstract class DataEntity<T>
    {
		[Key] 
        public T Id { get; set; }

        protected DataEntity()
        {
        }

        protected DataEntity(Func<T> idGenerator)
        {
            this.Id = idGenerator.Invoke();
        }
    }

    public abstract class DataEntity : DataEntity<Guid>
    {
        protected DataEntity()
            : base(() => Guid.NewGuid())
        {
        }
    }
}
