using System;
using System.Collections.Generic;

namespace Anmat.Server.Core
{
    public class Row
    {
        private readonly IList<Cell> cells;

        public IEnumerable<Cell> Cells { get { return this.cells; } }

        public Row()
        {
            this.cells = new List<Cell>();    
        }

		public void Add(string value, Type type)
        {
            this.cells.Add(new Cell
            {
                Type = type,
                Value = value
            });
        }

        public void Add<T>(string value)
        {
			this.Add (value, typeof (T));
        }
    }
}
