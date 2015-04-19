using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core.Tests.TestModels
{
    public class TestEntity : DataEntity
    {
        public string Name { get; set; }

        public string DisplayName { get; set; }

        public bool IsValid { get; set; }
    }
}
