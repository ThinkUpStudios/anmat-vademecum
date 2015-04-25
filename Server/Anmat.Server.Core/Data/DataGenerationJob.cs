using System;

namespace Anmat.Server.Core.Data
{
	public class DataGenerationJob : DataEntity
	{
		public int Version { get; set; }

		public DataGenerationJobStatus Status { get; set; }

		public DateTime DateStarted { get; set; }

		public DateTime DateFinished { get; set; }

		public string Message { get; set; }
	}
}
