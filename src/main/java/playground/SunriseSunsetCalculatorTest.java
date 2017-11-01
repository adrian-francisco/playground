/*
 * Name : SunriseSunsetCalculator.java
 * Author : Adrian Francisco
 * Created: Oct 31, 2017
 */
package playground;

import java.util.Calendar;
import java.util.TimeZone;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

public class SunriseSunsetCalculatorTest {

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EDT"));
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 14);
        cal.set(Calendar.HOUR_OF_DAY, 0);

        Location location = new Location("43.63", "-79.37");

        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "America/New_York");

        System.out.println(calculator.getOfficialSunriseForDate(cal));
        System.out.println(calculator.getOfficialSunsetForDate(cal));
    }
}
