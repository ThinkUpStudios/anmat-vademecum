using System;
using System.ServiceModel;
using System.ServiceModel.Description;
using System.ServiceModel.Web;

namespace Anmat.Server.DataService.ConsoleHost
{
	public class Program
	{
		public static void Main (string[] args)
		{
			var uri = new Uri("http://localhost:8000/");
			var host = new WebServiceHost(typeof(AnmatDataService), uri);
			var binding = new WebHttpBinding();

			host.AddServiceEndpoint(typeof(IAnmatDataService), binding, "");
			
			var debugBehavior = host.Description.Behaviors.Find<ServiceDebugBehavior>();

			debugBehavior.HttpHelpPageEnabled = false;

			host.Open();

			Console.WriteLine("Anmat Data Service Console started...");
			Console.WriteLine ("Press any key to finish...");

			Console.ReadKey();

			host.Close();
		}
	}
}
