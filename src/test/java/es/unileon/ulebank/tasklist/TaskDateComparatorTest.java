package es.unileon.ulebank.tasklist;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class TaskDateComparatorTest {

    private final TaskDateComparator comparator;

    public TaskDateComparatorTest() {
        this.comparator = new TaskDateComparator();
    }

    @Test
    public void testEquals() {
        final Date time = new Date();
        final Task t1 = new Task(time, null);
        Task t2 = new Task(time, null);
        Assert.assertEquals(this.comparator.compare(t1, t2), 0);
        Assert.assertEquals(this.comparator.compare(t2, t1), 0);
        final Date time2 = new Date(time.getTime());
        t2 = new Task(time2, null);
        Assert.assertEquals(this.comparator.compare(t1, t2), 0);
        Assert.assertEquals(this.comparator.compare(t2, t1), 0);
    }

    @Test
    public void testDiferentTimes() {
        final Date time = new Date();
        final Task t1 = new Task(time, null);
        Task t2 = new Task(new Date(time.getTime() + 1), null);
        Assert.assertEquals(this.comparator.compare(t1, t2), -1);
        Assert.assertEquals(this.comparator.compare(t2, t1), 1);
        final Date time2 = new Date(time.getTime() + 1000);
        t2 = new Task(time2, null);
        Assert.assertEquals(this.comparator.compare(t1, t2), -1);
        Assert.assertEquals(this.comparator.compare(t2, t1), 1);
    }

}
