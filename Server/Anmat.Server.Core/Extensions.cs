using System.Globalization;

namespace Anmat.Server.Core
{
	public static class Extensions
	{
		public static string ToNumberString(this int number)
		{
			var formatInfo = new CultureInfo( "en-US", false).NumberFormat;

			formatInfo.NumberGroupSeparator = string.Empty;
			formatInfo.NumberDecimalDigits = 0;

			return number.ToString ("N", formatInfo);
		}

		public static string ToNumberString(this double number)
		{
			var formatInfo = new CultureInfo( "en-US", false).NumberFormat;

			formatInfo.NumberGroupSeparator = string.Empty;
			formatInfo.NumberDecimalSeparator = ".";
			formatInfo.NumberDecimalDigits = 2;

			return number.ToString ("N", formatInfo);
		}
	}
}
