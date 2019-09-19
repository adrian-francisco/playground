using System;
using System.Globalization;

namespace ExcelReportPlay
{
    [Serializable]
    public class DateTimeInUtc
    {
        private static readonly string MIN_DATE_TIME_VOSTRING = "1000-01-01 00:00:00";

        private static readonly string MAX_DATE_TIME_VOSTRING = "9000-12-31 23:59:59";
        private static readonly string PREVIOUS_MAX_DATE_TIME_VOSTRING = "9999-12-31 23:59:59";

        public static readonly DateTimeInUtc MIN_DATE_TIME = fromVOString(MIN_DATE_TIME_VOSTRING);
        public static readonly DateTimeInUtc MAX_DATE_TIME = fromVOString(MAX_DATE_TIME_VOSTRING);

        private DateTime mLocal;

        public static DateTimeInUtc now()
        {
            return fromLocal(DateTime.Now);
        }

        public static DateTimeInUtc fromLocal(DateTime local)
        {
            return new DateTimeInUtc(local);
        }

        public DateTime getLocal()
        {
            return mLocal;
        }

        public DateTime getUtc()
        {
            var utc = mLocal.ToUniversalTime();
            return utc;
        }

        public static DateTimeInUtc fromVOString(string timestampString)
        {
            if (timestampString == null)
                return null;

            // Guard against a previously serialized max date time value.
            var timestampStringToUse = timestampString;
            if (timestampString.Trim() == PREVIOUS_MAX_DATE_TIME_VOSTRING)
                timestampStringToUse = MAX_DATE_TIME_VOSTRING;

            // Convert the appropriate string.
            var local = DateTime.ParseExact(timestampStringToUse, "yyyy-MM-dd HH:mm:ss", CultureInfo.InvariantCulture)
                .ToLocalTime();
            return new DateTimeInUtc(local);
        }

        public string toVOString()
        {
            var utc = mLocal.ToUniversalTime();
            var year = utc.Year;
            var month = utc.Month;
            var day = utc.Day;
            var hour = utc.Hour;
            var min = utc.Minute;
            var sec = utc.Second;

            return formatInt(year, 4) + "-" + formatInt(month, 2) + "-" +
                   formatInt(day, 2) + " " + formatInt(hour, 2) + ":" +
                   formatInt(min, 2) + ":" + formatInt(sec, 2);
        }

        public DateTimeInUtc startOfDay()
        {
            var temp = mLocal;
            temp = temp.AddHours(-temp.Hour);
            temp = temp.AddMinutes(-temp.Minute);
            temp = temp.AddSeconds(-temp.Second);

            return fromLocal(temp);
        }

        public DateTimeInUtc endOfDay()
        {
            var temp = mLocal;
            temp = temp.AddHours(-temp.Hour);
            temp = temp.AddMinutes(-temp.Minute);
            temp = temp.AddSeconds(-temp.Second);
            temp = temp.AddDays(1);
            temp = temp.AddSeconds(-1);

            return fromLocal(temp);
        }

        private DateTimeInUtc(DateTime local)
        {
            mLocal = local;
        }

        public string getYearAsString()
        {
            var utc = mLocal.ToUniversalTime();
            return formatInt(utc.Year, 4);
        }

        public string getMonthAsString()
        {
            var utc = mLocal.ToUniversalTime();
            return formatInt(utc.Month, 2);
        }

        public string getDayAsString()
        {
            var utc = mLocal.ToUniversalTime();
            return formatInt(utc.Day, 2);
        }

        public string getTimeAsString()
        {
            var utc = mLocal.ToUniversalTime();
            return formatInt(utc.Hour, 2) + ":" +
                   formatInt(utc.Minute, 2) + ":" + formatInt(utc.Second, 2);
        }

        public string getDateAsString()
        {
            var utc = mLocal.ToUniversalTime();
            return getYearAsString() + "-" +
                   getMonthAsString() + "-" + getDayAsString();
        }

        public long getUnixEpochTime()
        {
            return Convert.ToInt64((getUtc() -
                                    new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeKind.Utc)).TotalSeconds);
        }

        private static string formatInt(int intValue, int minDigits)
        {
            var str = "" + intValue;
            for (var i = str.Length; i < minDigits; i++)
                str = "0" + str;

            return str;
        }

        public override bool Equals(object obj)
        {
            if (obj == null || !(obj is DateTimeInUtc))
                return false;

            var that = (DateTimeInUtc) obj;
            if (mLocal.Equals(that.mLocal))
                return true;
            return false;
        }

        public override int GetHashCode()
        {
            return mLocal.GetHashCode();
        }
    }
}
