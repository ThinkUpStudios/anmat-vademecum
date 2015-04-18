using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Anmat.Server.Core
{
    public class DocumentFormatException : Exception
    {
        public DocumentFormatException(String message, Exception e)
            : base(message, e)
        {

        }

        public DocumentFormatException(String message)
            : base(message)
        {

        }
    }
}
