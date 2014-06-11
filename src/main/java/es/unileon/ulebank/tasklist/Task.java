/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.tasklist;

import java.util.Date;

import es.unileon.ulebank.command.Command;
import es.unileon.ulebank.handler.Handler;

/**
 *
 * @author runix
 */
public class Task {

    /**
     * The command
     */
    private final Command command;
    /**
     * The Task's effective date. ( Date for executing the command )
     */
    private final Date effectiveDate;

    /**
     * Create a new Task.
     * 
     * @param effectiveDate
     *            ( Date for executing the command )
     * @param command
     *            ( Command to execute )
     */
    public Task(Date effectiveDate, Command command) {
        this.effectiveDate = effectiveDate;
        this.command = command;
    }

    /**
     * Get the task id. (Command's id)
     * 
     * @return
     */
    public Handler getID() {
        return this.command.getID();
    }

    /**
     * Execute the command
     * 
     * @throws Exception
     */
    public void execute() throws Exception {
        this.command.execute();
    }

    /**
     * Undo the command
     * 
     * @throws Exception
     */
    public void undo() throws Exception {
        this.command.undo();
    }

    /**
     * Redo the command
     * 
     * @throws Exception
     */
    public void redo() throws Exception {
        this.command.redo();
    }

    /**
     * Get the effective date
     * 
     * @return
     */
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }
}
