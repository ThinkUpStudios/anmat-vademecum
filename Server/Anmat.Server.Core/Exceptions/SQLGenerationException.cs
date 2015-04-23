using System;

namespace Anmat.Server.Core
{
	public class SQLGenerationException : ApplicationException
	{
		public SQLGenerationException ()
		{
		}

        public SQLGenerationException(String message, Exception e) 
            : base(message, e)
        {
        }

        public SQLGenerationException(String message)
            : base(message)
        {
        }
	}
}
