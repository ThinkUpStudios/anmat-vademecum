using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    public class DocumentMetadata : DataEntity
    {
        public string DocumentName { get; set; }

        public bool HasHeader { get; set; }

        public List<DocumentColumnMetadata> Columns { get; set; }
    }

}
