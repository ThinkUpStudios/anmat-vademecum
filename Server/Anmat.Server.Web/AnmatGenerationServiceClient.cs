using System;
using System.ServiceModel;
using System.ServiceModel.Description;
using Anmat.Server.Core;
using Anmat.Server.DataService;

namespace Anmat.Server.Web
{
	public class AnmatGenerationServiceClient : IAnmatGenerationServiceClient
	{
		private readonly AnmatConfiguration configuration;
		private readonly ChannelFactory<IAnmatDataService> channelFactory;

		public AnmatGenerationServiceClient (AnmatConfiguration configuration)
		{
			this.configuration = configuration;
			this.channelFactory = new ChannelFactory<IAnmatDataService> (new WebHttpBinding (), this.configuration.AnmatDataServiceUrl);

			this.channelFactory.Endpoint.Behaviors.Add(new WebHttpBehavior());
		}

		public void ProcessJob (Guid id)
		{
			var channel = this.channelFactory.CreateChannel ();

			channel.ProcessJob (id);
		}
	}
}