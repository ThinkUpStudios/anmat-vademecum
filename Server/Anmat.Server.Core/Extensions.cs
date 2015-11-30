using System;
using System.Globalization;

namespace Anmat.Server.Core
{
	public static class Extensions
	{
		public static string ToNumberString(this int number)
		{
			var formatInfo = new NumberFormatInfo ();

			formatInfo.NumberGroupSeparator = "";
			formatInfo.NumberDecimalDigits = 0;

			return number.ToString ("N", formatInfo);
		}

		public static string ToNumberString(this double number)
		{
			var formatInfo = new NumberFormatInfo();

			formatInfo.NumberGroupSeparator = "";
			formatInfo.NumberDecimalSeparator = ".";
			formatInfo.NumberDecimalDigits = 2;

			return number.ToString ("N", formatInfo);
		}

		public static object GetDefault (this Type type)
		{
			if (type.IsValueType) {
				return Activator.CreateInstance (type);
			}

			return null;
		}

		public static bool IsNullOrEmpty(this string[] values)
		{
			if(values == null || values.Length == 0) {
				return true;
			}

			var empty = true;

			for(var i = 0; i < values.Length; i++) {
				empty = empty && string.IsNullOrWhiteSpace (values[i]);

				if (!empty) break;
			}

			return empty;
		}
	}
}
