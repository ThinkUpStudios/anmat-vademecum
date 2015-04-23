using System;

namespace Anmat.Server.Core.Exceptions
{
    public class DocumentFormatException : Exception
    {
        public DocumentFormatException ()
		{
		}

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
