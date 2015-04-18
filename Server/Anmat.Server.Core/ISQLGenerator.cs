using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    public interface ISQLGenerator
    {
        string Script { get; }

        void Generate(params IDocumentGenerator[] documentGenerators);
    }
}
