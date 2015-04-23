using System;

namespace Anmat.Server.Core.Data
{
    public class DocumentColumnMetadata
    {
        public int ColumnNumber { get; set; }

        public string Name { get; set; }

        public string Type { get; set; }

        public bool IsNullable { get; set; }

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
