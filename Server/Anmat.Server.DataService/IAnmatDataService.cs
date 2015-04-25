using System;
using System.ServiceModel;
using System.ServiceModel.Web;

namespace Anmat.Server.DataService
{
	[ServiceContract]
	public interface IAnmatDataService
	{
		[OperationContract]
		[WebGet(UriTemplate = "/isnewdataavailable?version={version}",
			RequestFormat = WebMessageFormat.Json, 
			ResponseFormat = WebMessageFormat.Json)]
		bool IsNewDataAvailable (int version);
 
		[OperationContract]
		[WebGet(UriTemplate = "/getdata", 
			RequestFormat = WebMessageFormat.Json, 
			ResponseFormat = WebMessageFormat.Json)]
		AnmatData GetData ();

		[OperationContract]
		[WebGet(RequestFormat = WebMessageFormat.Json, UriTemplate = "/processjob?id={id}")]
		void ProcessJob (Guid id);
	}
}
