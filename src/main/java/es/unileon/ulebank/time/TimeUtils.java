/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author runix
 */
public final class TimeUtils {

    private TimeUtils() {

    }

    /**
     * Gets the date's month
     *
     * @param date
     *            ( the date )
     *
     * @return
     */
    public static int getMonth(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("M");
        return Integer.parseInt(sdf.format(date));
    }

    /**
     * Gets the date's day
     *
     * @param date
     *            ( the date )
     *
     * @return
     */
    public static int getDay(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return Integer.parseInt(sdf.format(date));
    }

    /**
     * Gets the date's year
     *
     * @param date
     *            ( the date )
     *
     * @return
     */
    public static int getYear(Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return Integer.parseInt(sdf.format(date));
    }

    /**
     * Gets the timestamp that represent those paramenters
     * 
     * @param year
     *            ( the year )
     * @param month
     *            ( the month )
     * @param day
     *            ( the day )
     * 
     * @return
     */
    public static long getTimestamp(int year, int month, int day)
            throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyy");
        final Date d = sdf.parse(day + "/" + month + "/" + year);
        return d.getTime();
    }
}
