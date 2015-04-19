namespace Anmat.Server.Core
{
    public interface ISQLGenerator
    {
		string DatabaseName { get; }
	
        string Script { get; }

        void GenerateDatabase(params IDocumentGenerator[] documentGenerators);
    }
}
