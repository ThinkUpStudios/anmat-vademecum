using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Linq.Expressions;

namespace Anmat.Server.Core.Data
{
	public class SqlRepository<T> : IRepository<T>
		where T : DataEntity
	{
		private readonly DbContext dataContext;

		public SqlRepository (DbContext dataContext, AnmatConfiguration configuration, IInitializer<T> initializer = null)
		{
			this.dataContext = dataContext;

			if (initializer != null) {
				initializer.Initialize (this, configuration.FullInitialize);
			}
		}

		public IEnumerable<T> GetAll (Expression<Func<T, bool>> predicate = null, bool cache = false)
		{
			if (predicate == null) {
				return cache ? 
					this.dataContext.Set<T> ().AsEnumerable() : 
					this.dataContext.Set<T> ().AsNoTracking().AsEnumerable ();
			}

			var query = this.dataContext.Set<T> ().Where (predicate);

			return cache ? 
				query.AsEnumerable () : 
				query.AsNoTracking ().AsEnumerable ();
		}

		public T Get (Expression<Func<T, bool>> predicate = null, bool cache = false)
		{
			if (predicate == null) {
				return cache ?
					this.dataContext.Set<T> ().FirstOrDefault () :
					this.dataContext.Set<T> ().AsNoTracking ().FirstOrDefault ();
			}

			var query = this.dataContext.Set<T> ().Where (predicate);

			return cache ?
				query.AsEnumerable ().FirstOrDefault() :
				query.AsNoTracking ().AsEnumerable ().FirstOrDefault();
		}

		public bool Exist (Expression<Func<T, bool>> predicate = null)
		{
			return this.Get (predicate) != default (T);
		}

		public void Create (T dataEntity)
		{
			this.dataContext.Set<T>().Add (dataEntity);
			this.Save ();
		}

		public void Update (T dataEntity)
		{
			if(!this.IsAttached(dataEntity)) {
				this.dataContext.Set<T> ().Attach (dataEntity);
			}
			
			this.dataContext.Entry (dataEntity).State = EntityState.Modified;
			this.Save ();
		}

		public void Delete (T dataEntity)
		{
			if (!this.IsAttached (dataEntity)) {
				this.dataContext.Set<T> ().Attach (dataEntity);
			}

			this.dataContext.Set<T> ().Remove (dataEntity);
			this.Save ();
		}

		public void DeleteAll()
        {
			var all = this.GetAll (cache: true).ToList();

			foreach (var entity in all) {
				this.Delete (entity);
				this.Save ();
			}
        }

		private void Save ()
		{
			this.dataContext.SaveChanges ();
		}

		private bool IsAttached(T dataEntity)
		{
			return this.dataContext.Set<T> ().Local.Any (entity => entity.Id == dataEntity.Id);
		}
	}
}
