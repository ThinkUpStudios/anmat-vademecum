using System;

namespace Anmat.Server.Core.Exceptions
{
	public class DocumentGenerationException : ApplicationException
	{
		public DocumentGenerationException ()
		{
		}

        public DocumentGenerationException(String message, Exception e) 
            : base(message, e)
        {
        }

        public DocumentGenerationException(String message)
            : base(message)
        {
        }
	}
}
