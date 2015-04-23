using System.Collections.Generic;

namespace Anmat.Server.Core.Data
{
    public class DocumentMetadata : DataEntity
    {
        public string DocumentName { get; set; }

        public bool HasHeader { get; set; }

        public List<DocumentColumnMetadata> Columns { get; set; }

		public IEnumerable<DocumentColumnMetadata> GetColumns()
		{
			return this.Columns;
		}

		public void AddColumn(DocumentColumnMetadata column)
		{
			this.Columns.Add (column);
		}
    }
}
