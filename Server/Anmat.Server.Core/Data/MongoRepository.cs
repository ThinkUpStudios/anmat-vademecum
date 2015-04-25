using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using Anmat.Server.Core.Properties;

namespace Anmat.Server.Core.Data
{
    public class MongoRepository<T> : IRepository<T>
        where T : DataEntity
    {
		private static readonly string collectionName = typeof(T).Name;

		private string databaseName;
        private MongoClient client;
        
        public MongoRepository(AnmatConfiguration configuration, IInitializer<T> initializer = null)
        {
            this.InitializeClient(configuration);

			if (initializer != null) {
				initializer.Initialize (this, configuration.FullInitialize);
			}
        }

        public IEnumerable<T> GetAll(Expression<Func<T, bool>> predicate = null)
        {
            var collection = this.GetDatabase().GetCollection<T>(collectionName);

			if(predicate == null) {
				return collection.Find(x => x.Id != Guid.Empty).ToListAsync().Result;
			} else {
				return collection.Find(predicate).ToListAsync().Result;
			}
        }

        public T Get(Expression<Func<T, bool>> predicate = null)
        {
			var collection = this.GetDatabase().GetCollection<T>(collectionName);

			if(predicate == null) {
				return collection.Find(x => x.Id != Guid.Empty).FirstOrDefaultAsync().Result;
			} else {
				return collection.Find(predicate).FirstOrDefaultAsync().Result;
			}
        }

        public bool Exist(Expression<Func<T, bool>> predicate = null)
        {
            return this.Get(predicate) != default(T);
        }

        public void Create(T dataEntity)
        {
            try
            {
                this.GetDatabase()
					.GetCollection<T>(collectionName)
					.InsertOneAsync(dataEntity)
					.Wait();
            }
            catch (AggregateException aggregateEx)
            {
				var flatten = aggregateEx.Flatten();

				if (flatten.InnerException is MongoException) {
					throw new DataException(string.Format(Resources.MongoRepository_CreateError, typeof(T).Name), aggregateEx.InnerException);
				}

				throw flatten.InnerException;
            }
        }

        public void Update(T dataEntity)
        {
            try
            {
                this.GetDatabase()
					.GetCollection<T>(collectionName)
					.ReplaceOneAsync(e => e.Id == dataEntity.Id, dataEntity)
					.Wait();
            }
            catch (AggregateException aggregateEx)
            {
				var flatten = aggregateEx.Flatten();

				if (flatten.InnerException is MongoException) {
					throw new DataException(string.Format(Resources.MongoRepository_UpdateError, typeof(T).Name), aggregateEx.InnerException);
				}

				throw flatten.InnerException;
            }
        }

        public void Delete(T dataEntity)
        {
            try
            {
				this.GetDatabase()
					.GetCollection<T>(collectionName)
					.DeleteOneAsync(x => x.Id == dataEntity.Id)
					.Wait();
            }
            catch (AggregateException aggregateEx)
            {
				var flatten = aggregateEx.Flatten();

				if (flatten.InnerException is MongoException) {
					throw new DataException(string.Format(Resources.MongoRepository_DeleteError, typeof(T).Name), aggregateEx.InnerException);
				}

				throw flatten.InnerException;
            }
        }

        public void DeleteAll()
        {
            try
            {
                this.GetDatabase()
					.GetCollection<T>(collectionName)
					.DeleteManyAsync(e => e.Id != Guid.Empty);
            }
            catch (AggregateException aggregateEx)
            {
				var flatten = aggregateEx.Flatten();

				if (flatten.InnerException is MongoException) {
					throw new DataException(string.Format(Resources.MongoRepository_DeleteAllError, typeof(T).Name), aggregateEx.InnerException);
				}

				throw flatten.InnerException;
            }
        }

		private void InitializeClient(AnmatConfiguration configuration)
        {
            try
            {
				this.databaseName = configuration.SourceDatabaseName;
                this.client = new MongoClient(configuration.SourceDatabaseConnectionString);
            }
            catch (MongoException mongoEx)
            {
                var errorMessage = string.Format(Resources.MongoRepository_DatabaseCreationError, databaseName);

                throw new DataException(errorMessage, mongoEx);
            }
        }

		private IMongoDatabase GetDatabase()
		{
			return this.client.GetDatabase(this.databaseName);
		}
    }
}
