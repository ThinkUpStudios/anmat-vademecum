using Anmat.Server.Core.Data;

namespace Anmat.Server.Core.Services
{
	public class DocumentMetadataService : IDocumentMetadataService
	{
		private readonly IRepository<DocumentMetadata> repository;

		public DocumentMetadataService (IRepository<DocumentMetadata> repository)
		{
			this.repository = repository;
		}

		public DocumentMetadata GetByDocumentName (string name)
		{
			return this.repository.Get(d => d.DocumentName == name);
		}
	}
}
