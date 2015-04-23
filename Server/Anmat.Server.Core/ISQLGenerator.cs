using System.Collections.Generic;

namespace Anmat.Server.Core
{
    public interface ISQLGenerator
    {
		string FileExtension { get; }

        string Script { get; }

		/// <exception cref="SQLGenerationException">SQLGenerationException</exception>
        string GenerateDatabase(IEnumerable<IDocumentGenerator> documentGenerators);

		/// <exception cref="SQLGenerationException">SQLGenerationException</exception>
        string GenerateDatabase(params IDocumentGenerator[] documentGenerators);
    }
}
