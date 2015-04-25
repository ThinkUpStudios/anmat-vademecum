using System;

namespace Anmat.Server.Web
{
	public interface IAnmatGenerationServiceClient
	{
		void ProcessJob (Guid id);
	}
}
