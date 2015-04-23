using Anmat.Server.Core.Context;
using Xunit;
using System.IO;
using System.Diagnostics;

namespace Anmat.Server.Core.IntegrationTests
{
	public class IntegrationSpec
	{
		[Fact]
		public void when_generating_database_from_existing_files_then_succeeds()
		{
			var stopwatch = new Stopwatch ();

			stopwatch.Start ();

			var context = AnmatContext.Initialize ();
			var databaseFileName = context.SQLGenerator.GenerateDatabase (context.DocumentGenerators);
			
			stopwatch.Stop ();

			var script = context.SQLGenerator.Script;

			Debug.WriteLine (string.Format ("ANMAT Data Generation duration: {0} secs", stopwatch.ElapsedMilliseconds / 1000));

			Assert.False(string.IsNullOrEmpty(script));
			Assert.False(string.IsNullOrEmpty(databaseFileName));
			Assert.True (File.Exists (databaseFileName));
		}
	}
}
