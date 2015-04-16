using System;

namespace Anmat.Server.Core
{
    public abstract class DataEntity<T>
    {
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
