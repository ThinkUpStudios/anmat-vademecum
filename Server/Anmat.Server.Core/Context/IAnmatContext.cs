using System.Collections.Generic;
using Anmat.Server.Core.Services;

namespace Anmat.Server.Core.Context
{
	public interface IAnmatContext
	{
		AnmatConfiguration Configuration { get; }

		IVersionService VersionService { get; }

		IDataGenerationJobService JobService { get; }

		IDocumentReader DocumentReader { get; }

		IEnumerable<IDocumentGenerator> DocumentGenerators { get; }

		ISQLGenerator SQLGenerator { get; }
	}
}
