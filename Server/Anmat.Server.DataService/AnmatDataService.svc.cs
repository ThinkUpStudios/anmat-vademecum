using System;
using System.IO;
using Anmat.Server.Core;
using Anmat.Server.Core.Context;
using Anmat.Server.Core.Data;
using Anmat.Server.DataService.Properties;

namespace Anmat.Server.DataService
{
	public class AnmatDataService : IAnmatDataService
	{
		private static readonly IAnmatContext context;

		static AnmatDataService()
		{
			context = AnmatContext.Initialize ();
		}

		public bool IsNewDataAvailable (int version)
		{
			var latestVersion = context.VersionService.GetLatestVersion ();

			return latestVersion.Number > version;
		}

		public AnmatData GetData ()
		{
			var latestVersion = context.VersionService.GetLatestVersion ();
			var latestVersionPath = context.Configuration.GetVersionPath (latestVersion.Number);
			var databaseFileName = Path.Combine (latestVersionPath, context.Configuration.TargetDatabaseName + context.SQLGenerator.FileExtension);

			if (!File.Exists (databaseFileName)) {
				return new AnmatData { ContentSize = 0, Content = new byte[] {} };
			}

			var databaseContent = File.ReadAllBytes (databaseFileName);

			return new AnmatData {
				ContentSize = databaseContent.Length,
				Content = databaseContent
			};
		}

		public void ProcessJob (Guid id)
		{
			var latestJob = context.JobService.GetLatestJob ();

			if (latestJob.Id != id) {
				return;
			}

			try {
				var tempPath = AnmatConfiguration.GetTempVersionPath (latestJob.Version);
				var tempFiles = Directory.EnumerateFiles (tempPath);
				var destinationPath = context.Configuration.GetVersionPath (latestJob.Version);

				foreach (var tempFile in tempFiles) {
					var destinationFile = Path.Combine(destinationPath, Path.GetFileName(tempFile));

					File.Copy(tempFile, destinationFile, overwrite: true);
				}

				context.SQLGenerator.GenerateDatabase (context.DocumentGenerators);

				latestJob.Status = DataGenerationJobStatus.Completed;
				latestJob.Message = Resources.AnmatGenerationService_JobCompleted;
			} catch (Exception ex) {
				latestJob.Status = DataGenerationJobStatus.Failed;
				latestJob.Message = ex.Message;
			}

			
			latestJob.DateFinished = DateTime.Now;
			context.JobService.UpdateJob (latestJob);
		}
	}
}
