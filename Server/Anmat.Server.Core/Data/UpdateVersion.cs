using System;

namespace Anmat.Server.Core.Data
{
	public class UpdateVersion : DataEntity
	{
		public UpdateVersion ()
		{
			this.Date = DateTime.UtcNow;
		}

		public int Number { get; set; }

		public DateTime Date { get; set; }
	}
}
