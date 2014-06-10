/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.time;

import java.text.ParseException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author runix
 */
public class TimeUtilsTest {

    @Test
    public void testGetYear() {
        Assert.assertEquals(TimeUtils.getYear(new Date(0)), 1970);
        Assert.assertEquals(
                TimeUtils.getYear(new Date(24l * 60l * 60l * 1000l * 370l)),
                1971);
    }

    @Test
    public void testGetDay() {
        Assert.assertEquals(TimeUtils.getDay(new Date(0)), 1);
        Assert.assertEquals(
                TimeUtils.getDay(new Date((24l * 60l * 60l * 1000l) + 10)), 2);
    }

    @Test
    public void testGetMonth() {
        Assert.assertEquals(TimeUtils.getMonth(new Date(0)), 1);
        Assert.assertEquals(
                TimeUtils.getMonth(new Date(24l * 60l * 60l * 1000l * 32)), 2);
    }

    @Test
    public void testGetTimestamp() throws ParseException {
        Assert.assertEquals(TimeUtils.getTimestamp(1970, 1, 1),
                -(60l * 60l) * 1000);
        Assert.assertEquals(TimeUtils.getTimestamp(1970, 1, 2), 82800000);
        Assert.assertEquals(TimeUtils.getTimestamp(2014, 4, 13), 1397340000000l);
    }

}
