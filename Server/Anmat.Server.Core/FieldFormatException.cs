using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Anmat.Server.Core
{
    public class FieldFormatException : ApplicationException
    {
        public FieldFormatException(String message, Exception e) 
            : base(message, e)
        {

        }

        public FieldFormatException(String message)
            : base(message)
        {

        }
    }
}
