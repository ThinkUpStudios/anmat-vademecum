using System;

namespace Anmat.Server.Core.Exceptions
{
    public class FieldFormatException : ApplicationException
    {
		public FieldFormatException ()
		{
		}

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
