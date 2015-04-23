using Anmat.Server.Core.Data;

namespace Anmat.Server.Core
{
    public interface IDocumentReader
    {
		string FileExtension { get; }

		/// <exception cref="DocumentFormatException">DocumentFormatException</exception>
		/// <exception cref="FieldFormatException">FieldFormatException</exception>
		/// <exception cref="ArgumentException">ArgumentException</exception>
        Document Read(string path, DocumentMetadata metadata);
    }
}
