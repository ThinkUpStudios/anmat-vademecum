namespace Anmat.Server.Core
{
	public class DatabaseConfiguration
	{
		public string DatabaseName { get; set; }

		public string Version { get; set; }

		public string Path { get; set; }

		public bool ReplaceIfExist { get; set; }
	}
}
