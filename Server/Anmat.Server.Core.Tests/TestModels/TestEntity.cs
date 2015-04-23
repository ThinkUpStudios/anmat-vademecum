using Anmat.Server.Core.Data;

namespace Anmat.Server.Core.Tests.TestModels
{
    public class TestEntity : DataEntity
    {
        public string Name { get; set; }

        public string DisplayName { get; set; }

        public bool IsValid { get; set; }
    }
}
