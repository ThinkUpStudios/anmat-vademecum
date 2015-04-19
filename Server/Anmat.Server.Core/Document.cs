using System.Collections.Generic;

namespace Anmat.Server.Core
{
    public class Document
    {
		private readonly IList<Row> rows;

        public Document()
        {
            this.rows = new List<Row>();
        }

        public IEnumerable<Row> Rows { get { return this.rows; } }

        public void Add(Row row)
        {
            this.rows.Add(row);
        }
    }
}
