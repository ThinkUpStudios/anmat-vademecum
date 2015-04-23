using System;
using System.IO;
using System.Text;

namespace Anmat.Server.Core
{
	public class AnmatConfiguration
	{
		public string DocumentsPath { get; set; }

		public string SourceDatabaseName { get; set; }

		public string TargetDatabaseName { get; set; }

		public string TargetMedicinesTableName { get; set; }

		public string TargetActiveComponentsTableName { get; set; }

		public bool ReplaceExistingTargetDatabase { get; set; }

		public bool FullInitialize { get; set; }

		public string DefaultTextEncoding { get; set; }

		public string GetVersionPath(int version)
		{
			var versionValue = string.Format("v.{0}.0", version);
			var expandedDocumentsPath = Environment.ExpandEnvironmentVariables (this.DocumentsPath);

			return Path.Combine (expandedDocumentsPath, versionValue);
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
