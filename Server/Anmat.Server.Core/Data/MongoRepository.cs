using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Linq.Expressions;

namespace Anmat.Server.Core
{
    public class MongoRepository<T> : IRepository<T>
        where T : DataEntity
    {
        private IMongoDatabase database;
        
        private static readonly string collectionName = typeof(T).Name;

        public MongoRepository(string dataBaseName)
        {
            this.Initialize(dataBaseName);
        }

        private void Initialize(string dataBaseName)
        {
            try
            {
                var client = new MongoClient();

                this.database = client.GetDatabase(dataBaseName);
            }
            catch (MongoException mongoEx)
            {
                var errorMessage = string.Concat("No se puede inicializar la base de datos: {0}", mongoEx.Message);

                throw new DataException(errorMessage, mongoEx);
            }
        }


        public IEnumerable<T> GetAll(Expression<Func<T, bool>> predicate = null)
        {
            return this.database.GetCollection<T>(collectionName).Find(predicate).ToListAsync().Result; 
        }

        public T Get(Expression<Func<T, bool>> predicate = null)
        {
            return  this.GetAll(predicate).FirstOrDefault();
        }

        public bool Exist(Expression<Func<T, bool>> predicate = null)
        {
            return this.Get(predicate) != null;
        }

        public void Create(T dataEntity)
        {
            try
            {
                this.database.GetCollection<T>(collectionName).InsertOneAsync(dataEntity).Wait();
            }
            catch (MongoException e)
            {
                throw new DataException("No se pudo crear la entidad.", e);
            }
        }

        public void Update(T dataEntity)
        {
            try
            {
                this.database.GetCollection<T>(collectionName).ReplaceOneAsync(e => e.Id == dataEntity.Id, dataEntity).Wait();
            }
            catch (MongoException e)
            {
                throw new DataException("No se pudo actualizar la entidad.", e);
            }
        }

        public void Delete(T dataEntity)
        {
            try
            {
             this.database.GetCollection<T>(collectionName).DeleteOneAsync(x => x.Id == dataEntity.Id).Wait();
            }
            catch (MongoException e)
            {
                throw new DataException("No se pudo borrar de la entidad.", e);
            }
        }

        public void DeleteAll()
        {
            try
            {
                this.database.GetCollection<T>(collectionName).DeleteManyAsync(e => e.Id != Guid.Empty);
            }
            catch (MongoException e)
            {
                throw new DataException("No se pudo borrar de la entidad.", e);
            }
        }
    }
}
