using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    interface IDocumentReader
    {
        Document Read(string path, DocumentMetadata metadata);
    }
}
