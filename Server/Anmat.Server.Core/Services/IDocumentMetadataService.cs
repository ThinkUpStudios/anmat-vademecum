using Anmat.Server.Core.Data;

namespace Anmat.Server.Core.Services
{
	public interface IDocumentMetadataService
	{
		DocumentMetadata GetByDocumentName (string name);
	}
}
