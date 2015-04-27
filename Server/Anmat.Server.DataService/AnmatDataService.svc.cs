using System;
using System.Collections.Generic;
using System.IO;
using Anmat.Server.Core;
using Anmat.Server.Core.Context;
using Anmat.Server.Core.Data;
using Anmat.Server.DataService.Properties;
using System.Linq;

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
				return new AnmatData { ContentSize = 0, Content = string.Empty };
			}

			var databaseContent = File.ReadAllBytes (databaseFileName);

			return new AnmatData {
				ContentSize = databaseContent.Length,
				Content = Convert.ToBase64String(databaseContent)
			};
		}

		public void ProcessJob (Guid id)
		{
			var latestJob = context.JobService.GetLatestJob ();

			if (latestJob.Id != id) {
				return;
			}

			var expectedFiles = new List<string> { 
				context.Configuration.TargetMedicinesTableName, 
				context.Configuration.TargetActiveComponentsTableName 
			};
			var tempFiles = this.GetVersionTempFiles (latestJob.Version)
				.Where(f => expectedFiles.Any(e => e == Path.GetFileNameWithoutExtension(f)));
			var destinationPath = context.Configuration.GetVersionPath (latestJob.Version);

			foreach (var tempFile in tempFiles) {
				var destinationFile = Path.Combine(destinationPath, Path.GetFileName(tempFile));

				File.Copy(tempFile, destinationFile, overwrite: true);
			}

			var missingFiles = expectedFiles.Where (e => !tempFiles.Any (t => Path.GetFileNameWithoutExtension(t) == e));

			if (missingFiles.Any ()) {
				var previousVersionFiles = this.GetVersionFiles (latestJob.Version - 1);

				foreach (var missingFile in missingFiles) {
					var sourceFile = previousVersionFiles.FirstOrDefault(f => Path.GetFileNameWithoutExtension(f) == missingFile);
					var destinationFile = Path.Combine(destinationPath, Path.GetFileName(sourceFile));
				
					File.Copy(sourceFile, destinationFile, overwrite: true);
				}
			}

			try {
				context.SQLGenerator.GenerateDatabase (context.DocumentGenerators);

				latestJob.Status = DataGenerationJobStatus.Completed;
				latestJob.Message = Resources.AnmatGenerationService_JobCompleted;
			} catch (Exception ex) {
				latestJob.Status = DataGenerationJobStatus.Failed;
				latestJob.Message = ex.Message;
			}

			
			latestJob.DateFinished = DateTime.Now.ToString();
			context.JobService.UpdateJob (latestJob);
		}

		private IEnumerable<string> GetVersionTempFiles (int version)
		{
			var tempPath = AnmatConfiguration.GetTempVersionPath (version);
			
			return Directory.EnumerateFiles (tempPath);
		}

		private IEnumerable<string> GetVersionFiles (int version)
		{
			var tempPath = context.Configuration.GetVersionPath (version);
			
			return Directory.EnumerateFiles (tempPath);
		}
	}
}
