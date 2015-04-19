using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Anmat.Server.Core
{
	public class SQLGenerationException : Exception
	{
		private   string p;

		public SQLGenerationException (string p)
		{
			// TODO: Complete member initialization
			this.p = p;
		}
	}
}
