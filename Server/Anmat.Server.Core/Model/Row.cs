using System;
using System.Collections.Generic;

namespace Anmat.Server.Core.Model
{
    public class Row
    {
        private readonly IList<Cell> cells;

        public Row()
        {
            this.cells = new List<Cell>();    
        }

		public IEnumerable<Cell> Cells { get { return this.cells; } }

		public void AddCell(string value, Type type)
        {
            this.cells.Add(new Cell
            {
                Type = type,
                Value = value
            });
        }

        public void AddCell<T>(string value)
        {
			this.AddCell (value, typeof (T));
        }
    }
}
