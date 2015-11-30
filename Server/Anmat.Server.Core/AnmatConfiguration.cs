using System.IO;
using System.Text;

namespace Anmat.Server.Core
{
	public class AnmatConfiguration
	{
		public static string GetTempVersionPath(int version)
		{
			var versionValue = string.Format("v.{0}.0", version);
			var tempPath = Path.Combine(Path.GetTempPath (), "Anmat");
			var tempVersionPath = Path.Combine (tempPath, versionValue);

			if (!Directory.Exists (tempVersionPath)) {
				Directory.CreateDirectory (tempVersionPath);
			}

			return tempVersionPath;
		}

		public string AnmatDataServiceUrl { get; set; }

		public string DocumentsPath { get; set; }

		public string TargetDatabaseName { get; set; }

		public string TargetMedicinesTableName { get; set; }

		public string TargetActiveComponentsTableName { get; set; }

		public bool ReplaceExistingTargetDatabase { get; set; }

		public bool FullInitialize { get; set; }

		public string DefaultCulture { get; set; }

		public string DefaultTextEncoding { get; set; }

		public string GetVersionPath(int version)
		{
			var versionValue = string.Format("v.{0}.0", version);
			var versionPath = Path.Combine (this.DocumentsPath, versionValue);

			if (!Directory.Exists (versionPath)) {
				Directory.CreateDirectory (versionPath);
			}

			return versionPath;
		}

		public Encoding GetDefaultEncoding()
		{
			var encoding = Encoding.UTF8;

			if (!string.IsNullOrWhiteSpace (this.DefaultTextEncoding)) {
				try {
					encoding = Encoding.GetEncoding (this.DefaultTextEncoding);
				} catch {
				}
			}

			return encoding;
		}
	}
}
