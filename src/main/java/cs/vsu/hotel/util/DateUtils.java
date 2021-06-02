package cs.vsu.hotel.util;

import java.sql.Date;
import java.util.Calendar;

public class DateUtils {

    public static Date parseDate(String dateSrt) {
        if (dateSrt == null || dateSrt.isEmpty()) {
            return null;
        }
        return Date.valueOf(dateSrt);
    }

    public static String dateToDdMmYyyyStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        day = day.length() == 1 ? "0" + day : day;
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        month = month.length() == 1 ? "0" + month : month;
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        return String.format("%s-%s-%s", day, month, year);
    }

    public static String formatDate(String dateSrt) {
        return dateToDdMmYyyyStr(parseDate(dateSrt));
    }

    public static int getDaysBetween(Date date1, Date date2) {
        return (int) (Math.max(date1.getTime(), date2.getTime()) - Math.min(date1.getTime(), date2.getTime())) / (1000 * 60 * 60 * 24);
    }
}
