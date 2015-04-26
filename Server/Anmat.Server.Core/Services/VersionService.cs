using System;
using Anmat.Server.Core.Data;
using System.Linq;

namespace Anmat.Server.Core.Services
{
	public class VersionService : IVersionService
	{
		private readonly IRepository<UpdateVersion> repository;

		public VersionService (IRepository<UpdateVersion> repository)
		{
			this.repository = repository;
		}

		public UpdateVersion GetLatestVersion ()
		{
			var latestNumber = this.repository.GetAll ().Max (v => v.Number);

			return this.repository.Get (v => v.Number == latestNumber);
		}

		public UpdateVersion IncrementVersion ()
		{
			var latestVersion = this.GetLatestVersion ();
			var newVersion = new UpdateVersion {
				Number = latestVersion.Number + 1,
				Date = DateTime.Now
			};

			this.repository.Create (newVersion);

			return newVersion;
		}
	}
}
