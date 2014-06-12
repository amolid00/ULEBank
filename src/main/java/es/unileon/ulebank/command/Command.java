/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.command;

import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.handler.Handler;

/**
 *
 * @author runix
 */
public interface Command {

    /**
     *
     * @return
     */
    public Handler getID();

    /**
     * @throws CommandException
     *
     */
    public void execute() throws CommandException;

    /**
     * @throws CommandException
     *
     */
    public void undo() throws CommandException;

    /**
     * @throws CommandException
     *
     */
    public void redo() throws CommandException;
}
