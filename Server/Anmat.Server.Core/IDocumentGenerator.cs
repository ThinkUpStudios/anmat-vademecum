namespace Anmat.Server.Core
{
    public interface IDocumentGenerator
    {
        string Name { get; }

        DocumentMetadata Metadata { get; }

        Document Generate();
    }
}
