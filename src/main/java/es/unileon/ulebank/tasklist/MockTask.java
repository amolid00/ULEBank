package es.unileon.ulebank.tasklist;

import java.util.Date;

import es.unileon.ulebank.command.MockCommand;
import es.unileon.ulebank.handler.Handler;

public class MockTask extends Task {

    public static final int STATE_EXECUTE = 0;
    public static final int STATE_REDO = 1;
    public static final int STATE_UNDO = 2;
    public static final int STATE_NORMAL = 4;

    private int state;

    public MockTask(Date date, Handler id) {
        super(date, new MockCommand(id));
        this.state = MockTask.STATE_NORMAL;
    }

    @Override
    public void execute() {
        this.state = MockTask.STATE_EXECUTE;
    }

    @Override
    public void redo() {
        this.state = MockTask.STATE_REDO;
    }

    @Override
    public void undo() {
        this.state = MockTask.STATE_UNDO;
    }

    public int getState() {
        return this.state;
    }
}
