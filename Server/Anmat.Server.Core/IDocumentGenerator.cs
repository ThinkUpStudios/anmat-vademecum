using Anmat.Server.Core.Data;

namespace Anmat.Server.Core
{
    public interface IDocumentGenerator
    {
        string Name { get; }

        DocumentMetadata Metadata { get; }

		/// <exception cref="DocumentGenerationException">DocumentGenerationException</exception>
        Document Generate();
    }
}
