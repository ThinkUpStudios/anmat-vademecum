using Anmat.Server.Core.Data;

namespace Anmat.Server.Core.Services
{
	public interface IVersionService
	{
		UpdateVersion GetLatestVersion ();

		UpdateVersion IncrementVersion ();
	}
}
