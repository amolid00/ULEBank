/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author runix
 */
public class TimeTest {

    private Time time;

    @Before
    public void setUp() {
        this.time = Time.getInstance();
    }

    @Test
    public void testForwardDays() {
        this.time.updateTime();
        final long time = this.time.getTime();
        final int days = 2;
        this.time.forwardDays(days);
        final long updatedTime = this.time.getTime();
        final long diff = updatedTime
                - (time + (days * 24l * 60l * 60l * 1000l));
        // Assume that the time between this.time.forwardDays and updatedTime is
        // less than 5 millis
        Assert.assertTrue(diff < 5);
    }

    @Test
    public void testBackwardDays() {
        this.time.updateTime();
        final long time = this.time.getTime();
        final int days = -2;
        this.time.forwardDays(days);
        final long updatedTime = this.time.getTime();
        final long diff = updatedTime
                - (time + (days * 24l * 60l * 60l * 1000l));
        // Assume that the time between this.time.forwardDays and updatedTime is
        // less than 5 millis
        Assert.assertTrue(diff < 5);
    }

    @Test
    public void testForward() {
        this.time.updateTime();
        final long time = this.time.getTime();
        final int millis = 20000;
        this.time.forward(millis);
        final long updatedTime = this.time.getTime();
        final long diff = updatedTime
                - (time + (millis * 24l * 60l * 60l * 1000l));
        // Assume that the time between this.time.forwardDays and updatedTime is
        // less than 5 millis
        Assert.assertTrue(diff < 5);
    }

    @Test
    public void testBackward() {
        this.time.updateTime();
        final long time = this.time.getTime();
        final int millis = 20000;
        this.time.forward(millis);
        final long updatedTime = this.time.getTime();
        final long diff = updatedTime
                - (time + (millis * 24l * 60l * 60l * 1000l));
        // Assume that the time between this.time.forwardDays and updatedTime is
        // less than 5 millis
        Assert.assertTrue(diff < 5);
    }

    @Test
    public void setTime() {
        final long timestamp = 1000;
        this.time.setTime(timestamp);
        final long diff = this.time.getTime() - timestamp;
        Assert.assertTrue(diff < 5);
    }

    @Test
    public void testUpdate() {
        this.time.updateTime();
        final long time = this.time.getTime();
        final long timeToSleep = 1000;
        try {
            Thread.sleep(timeToSleep);
        } catch (final InterruptedException ex) {
            Assert.fail("Fail when try to sleep for trying update method");
        }
        this.time.updateTime();
        final long timeUpdated = this.time.getTime();
        final long diff = timeUpdated - (timeToSleep + time);
        // Assume that the time between this.time.forward and updatedTime is
        // less than 5 millis
        Assert.assertTrue(diff < 5);
    }
}
