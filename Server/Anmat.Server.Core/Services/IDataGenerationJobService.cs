using System.Collections.Generic;
using Anmat.Server.Core.Data;

namespace Anmat.Server.Core.Services
{
	public interface IDataGenerationJobService
	{
		DataGenerationJob CreateJob (int version);

		void UpdateJob (DataGenerationJob job);

		void FinishJob (DataGenerationJob job);

		IEnumerable<DataGenerationJob> GetJobs ();

		DataGenerationJob GetLatestJob ();
	}
}
