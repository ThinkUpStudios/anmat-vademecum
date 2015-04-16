using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    class MedicamentosGenerator : IDocumentGenerator
    {
        private readonly string path;
        private readonly IDocumentReader reader;
        private readonly IRepository<DocumentMetadata> repository;

        public MedicamentosGenerator(string path, IDocumentReader reader, IRepository<DocumentMetadata> repository)
        {
            this.path = path;
            this.reader = reader;
            this.repository = repository;

            this.Metadata = this.GetMetadata();
        }

        public string Name { get { return "Medicamentos"; } }

        public DocumentMetadata Metadata { get; private set; }

        public Document Generate()
        {
            return this.reader.Read(this.path, this.Metadata);
        }

        private DocumentMetadata GetMetadata()
        {
            var metadata = this.repository.Get(d => d.DocumentName == this.Name);

            if (metadata == null)
            {
                var columns = new List<DocumentColumnMetadata>();

                columns.Add(new DocumentColumnMetadata
                {
                    ColumnNumber = 1,
                    Name = "Certificado",
                    Type = typeof(string),
                    IsNullable = true
                });
                columns.Add(new DocumentColumnMetadata
                {
                    ColumnNumber = 2,
                    Name = "Nombre Generico",
                    Type = typeof(string),
                    IsNullable = true
                });
                columns.Add(new DocumentColumnMetadata
                {
                    ColumnNumber = 3,
                    Name = "Nombre Comercial",
                    Type = typeof(string),
                    IsNullable = true
                });

                metadata = new DocumentMetadata
                {
                    DocumentName = this.Name,
                    Columns = columns
                };

                this.repository.Create(metadata);
            }

            return metadata;
        }
    }
}
