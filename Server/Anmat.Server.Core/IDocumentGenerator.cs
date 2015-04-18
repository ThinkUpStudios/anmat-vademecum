using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    public interface IDocumentGenerator
    {
        string Name { get; }

        DocumentMetadata Metadata { get; }

        Document Generate();
    }
}
