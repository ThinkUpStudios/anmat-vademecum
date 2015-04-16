using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    interface IDocumentGenerator
    {
        public string Name { get; }

        public DocumentMetadata Metadata { get; }

        Document Generate();
    }
}
