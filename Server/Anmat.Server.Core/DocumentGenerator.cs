using System.IO;
using System.Linq;
using Anmat.Server.Core.Data;
using Anmat.Server.Core.Exceptions;
using Anmat.Server.Core.Properties;

namespace Anmat.Server.Core
{
	public class DocumentGenerator: IDocumentGenerator
    {
        private readonly IDocumentReader reader;
        private readonly IRepository<DocumentMetadata> metadataRepository;
		private readonly IRepository<UpdateVersion> versionRepository;
		private readonly AnmatConfiguration configuration;

        public DocumentGenerator(string name, IDocumentReader reader, IRepository<DocumentMetadata> metadataRepository, 
			IRepository<UpdateVersion> versionRepository, AnmatConfiguration configuration)
        {
            this.reader = reader;
            this.metadataRepository = metadataRepository;
			this.versionRepository = versionRepository;
			this.configuration = configuration;

			this.Name = name;
            this.Metadata = this.GetMetadata();
        }

        public string Name { get; private set; }

        public DocumentMetadata Metadata { get; private set; }

		/// <exception cref="DocumentGenerationException">DocumentGenerationException</exception>
        public Document Generate()
        {
			var version = this.versionRepository.GetAll ().Max (v => v.Number);
			var path = Path.Combine (this.configuration.GetVersionPath (version), this.Name + this.reader.FileExtension);

            return this.reader.Read(path, this.Metadata);
        }

        private DocumentMetadata GetMetadata()
        {
            var metadata = this.metadataRepository.Get(d => d.DocumentName == this.Name);

            if (metadata == null)
            {
				throw new DocumentGenerationException (string.Format(Resources.DocumentGenerator_NoMetadataFound, this.Name));
			}

            return metadata;
        }
    }
}
