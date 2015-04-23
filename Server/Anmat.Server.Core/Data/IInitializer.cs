namespace Anmat.Server.Core.Data
{
	public interface IInitializer<T> where T : DataEntity
	{
		void Initialize(IRepository<T> repository, bool force = false);
	}
}
