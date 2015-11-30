using System;
using System.ServiceModel;
using System.ServiceModel.Description;
using System.ServiceModel.Web;
using Anmat.Server.Core;
using Anmat.Server.DataService;

namespace Anmat.Server.Web
{
	public class AnmatGenerationServiceClient : IAnmatGenerationServiceClient
	{
		private readonly AnmatConfiguration configuration;
		private readonly WebChannelFactory<IAnmatDataService> channelFactory;

		public AnmatGenerationServiceClient (AnmatConfiguration configuration)
		{
			this.configuration = configuration;

			var binding = new WebHttpBinding ();

			binding.SendTimeout = TimeSpan.FromMinutes (10);

            this.channelFactory = new WebChannelFactory<IAnmatDataService> (binding, new Uri(this.configuration.AnmatDataServiceUrl));

			this.channelFactory.Endpoint.Behaviors.Add(new WebHttpBehavior());
		}

		public void ProcessJob (Guid id)
		{
			var channel = this.channelFactory.CreateChannel ();

			var updateAvailable = channel.IsNewDataAvailable (version: 1);

			channel.ProcessJob (id);
		}
	}
}