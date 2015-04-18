using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    public class Document
    {
        public Document()
        {
            this.Rows = new List<Row>();
        }
        public IList<Row> Rows { get; private set; }

        public void Add(Row row)
        {
            this.Rows.Add(row);
        }
        

    }
}
