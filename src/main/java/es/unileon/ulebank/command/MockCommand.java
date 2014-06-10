package es.unileon.ulebank.command;

import es.unileon.ulebank.handler.Handler;

public class MockCommand implements Command {

    public static final int STATE_EXECUTE = 0;
    public static final int STATE_REDO = 1;
    public static final int STATE_UNDO = 2;
    public static final int STATE_NORMAL = 4;
    private int state;
    private final Handler id;

    public MockCommand(Handler id) {
        this.state = MockCommand.STATE_NORMAL;
        this.id = id;
    }

    @Override
    public Handler getID() {
        return this.id;
    }

    @Override
    public void execute() {
        this.state = MockCommand.STATE_EXECUTE;
    }

    @Override
    public void undo() {
        this.state = MockCommand.STATE_UNDO;
    }

    @Override
    public void redo() {
        this.state = MockCommand.STATE_REDO;
    }

    public int getState() {
        return this.state;
    }
}
