using System.Runtime.Serialization;

namespace Anmat.Server.DataService
{
	[DataContract]
	public class AnmatData
	{
		[DataMember]
		public int ContentSize { get; set; }
 
		[DataMember]
		public string Content { get; set; }
	}
}