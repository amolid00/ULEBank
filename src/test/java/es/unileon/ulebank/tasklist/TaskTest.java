package es.unileon.ulebank.tasklist;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.command.MockCommand;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;

public class TaskTest {

    private MockCommand command;
    private Handler id;
    private Task task;
    private Date effectiveDate;

    @Before
    public void setUp() throws Exception {
        this.id = new GenericHandler("handler");
        this.command = new MockCommand(this.id);
        this.effectiveDate = new Date();
        this.task = new Task(this.effectiveDate, this.command);
    }

    @Test
    public void testUndo() throws Exception {
        this.task.undo();
        Assert.assertEquals(this.command.getState(), MockCommand.STATE_UNDO);
    }

    @Test
    public void testExecute() throws Exception {
        this.task.execute();
        Assert.assertEquals(this.command.getState(), MockCommand.STATE_EXECUTE);
    }

    @Test
    public void testRedo() throws Exception {
        this.task.redo();
        Assert.assertEquals(this.command.getState(), MockCommand.STATE_REDO);
    }

    @Test
    public void testGetId() {
        Assert.assertEquals(this.id.compareTo(this.task.getID()), 0);
    }

    @Test
    public void testCombined() throws Exception {
        this.task.undo();
        Assert.assertEquals(this.command.getState(), MockCommand.STATE_UNDO);
        this.task.execute();
        Assert.assertEquals(this.command.getState(), MockCommand.STATE_EXECUTE);
        this.task.redo();
        Assert.assertEquals(this.command.getState(), MockCommand.STATE_REDO);
        this.task.undo();
        Assert.assertEquals(this.command.getState(), MockCommand.STATE_UNDO);
        this.task.execute();
        Assert.assertEquals(this.command.getState(), MockCommand.STATE_EXECUTE);
        this.task.redo();
        Assert.assertEquals(this.command.getState(), MockCommand.STATE_REDO);
    }

    @Test
    public void testGetEffectiveDate() {
        Assert.assertEquals(this.effectiveDate.getTime(), this.task
                .getEffectiveDate().getTime());
    }
}
