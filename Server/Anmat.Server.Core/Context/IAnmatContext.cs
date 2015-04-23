using System.Collections.Generic;

namespace Anmat.Server.Core.Context
{
	public interface IAnmatContext
	{
		AnmatConfiguration Configuration { get; }

		IDocumentReader DocumentReader { get; }

		IEnumerable<IDocumentGenerator> DocumentGenerators { get; }

		ISQLGenerator SQLGenerator { get; }
	}
}
