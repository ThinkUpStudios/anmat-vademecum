using System;
using System.Linq;

namespace Anmat.Server.Core.Data
{
	public class UpdateVersionInitializer : IInitializer<UpdateVersion>
	{
		public void Initialize (IRepository<UpdateVersion> repository, bool force = false)
		{
			if (!force && repository.GetAll ().Any ()) {
				return;
			}

			if (force) {
				this.CleanExistingData (repository);
			}

			var version = new UpdateVersion { Number = 1, Date = new DateTime (2015, 4, 23) };

			repository.Create (version);
		}

		private void CleanExistingData (IRepository<UpdateVersion> repository)
		{
			(repository as MongoRepository<UpdateVersion>).DeleteAll ();
		}
	}
}
