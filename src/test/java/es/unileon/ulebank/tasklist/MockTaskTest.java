package es.unileon.ulebank.tasklist;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;

public class MockTaskTest {

    private Date effectiveDate;
    private MockTask task;
    private Handler id;

    @Before
    public void setUp() {
        this.id = new GenericHandler("mock");
        this.effectiveDate = new Date();
        this.task = new MockTask(this.effectiveDate, this.id);
    }

    @Test
    public void testUndo() {
        this.task.undo();
        Assert.assertEquals(this.task.getState(), MockTask.STATE_UNDO);
    }

    @Test
    public void testExecute() {
        this.task.execute();
        Assert.assertEquals(this.task.getState(), MockTask.STATE_EXECUTE);
    }

    @Test
    public void testRedo() {
        this.task.redo();
        Assert.assertEquals(this.task.getState(), MockTask.STATE_REDO);
    }

    @Test
    public void testGetEffectiveDate() {
        Assert.assertEquals(this.effectiveDate.getTime(), this.task
                .getEffectiveDate().getTime());
    }

    @Test
    public void testHandler() {
        Assert.assertEquals(this.id.compareTo(this.task.getID()), 0);
    }

    @Test
    public void testCombined() {
        this.task.undo();
        Assert.assertEquals(this.task.getState(), MockTask.STATE_UNDO);
        this.task.execute();
        Assert.assertEquals(this.task.getState(), MockTask.STATE_EXECUTE);
        this.task.redo();
        Assert.assertEquals(this.task.getState(), MockTask.STATE_REDO);
        this.task.undo();
        Assert.assertEquals(this.task.getState(), MockTask.STATE_UNDO);
        this.task.execute();
        Assert.assertEquals(this.task.getState(), MockTask.STATE_EXECUTE);
        this.task.redo();
        Assert.assertEquals(this.task.getState(), MockTask.STATE_REDO);
    }
}
