using System.IO;
using Anmat.Server.Core.Data;
using Anmat.Server.Core.Exceptions;
using Anmat.Server.Core.Properties;
using Anmat.Server.Core.Services;

namespace Anmat.Server.Core
{
	public class DocumentGenerator: IDocumentGenerator
    {
        private readonly IDocumentReader reader;
        private readonly IDocumentMetadataService metadataService;
		private readonly IDataGenerationJobService jobService;
		private readonly AnmatConfiguration configuration;

        public DocumentGenerator(string name, IDocumentReader reader, IDataGenerationJobService jobService,
			IDocumentMetadataService metadataService, AnmatConfiguration configuration)
        {
            this.reader = reader;
			this.jobService = jobService;
            this.metadataService = metadataService;
			this.configuration = configuration;

			this.Name = name;
            this.Metadata = this.GetMetadata();
        }

        public string Name { get; private set; }

        public DocumentMetadata Metadata { get; private set; }

		/// <exception cref="DocumentGenerationException">DocumentGenerationException</exception>
        public Document Generate()
        {
			var job = this.jobService.GetLatestJob ();

			if (job == null) {
				throw new DocumentGenerationException (Resources.LatestJob_Null);
			}

			if (job.Status != DataGenerationJobStatus.InProgress) {
				throw new DocumentGenerationException (string.Format(Resources.LatestJob_NotInProgress, job.Id, job.Version));
			}

			var path = Path.Combine (this.configuration.GetVersionPath (job.Version), this.Name + this.reader.FileExtension);

            return this.reader.Read(path, this.Metadata);
        }

        private DocumentMetadata GetMetadata()
        {
			var metadata = this.metadataService.GetByDocumentName (this.Name);

            if (metadata == null)
            {
				throw new DocumentGenerationException (string.Format(Resources.DocumentGenerator_NoMetadataFound, this.Name));
			}

            return metadata;
        }
    }
}
