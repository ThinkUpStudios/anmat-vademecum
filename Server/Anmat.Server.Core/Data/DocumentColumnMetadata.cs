using System;

namespace Anmat.Server.Core.Data
{
    public class DocumentColumnMetadata: DataEntity
    {
        public int ColumnNumber { get; set; }

        public string Name { get; set; }

        public string Type { get; set; }

        public bool IsNullable { get; set; }

        public bool RemovableAccents { get; set; }

        public bool UpperCase { get; set; }

		public virtual DocumentMetadata Metadata { get; set; }

		public void AddType(Type type)
		{
			this.Type = type.ToString ();
		}

		public new Type GetType()
		{
			return System.Type.GetType (this.Type);
		}
    }
}
