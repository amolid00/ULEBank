/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.command;

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
     * @throws Exception
     *
     */
    public void execute() throws Exception;

    /**
     * @throws Exception
     *
     */
    public void undo() throws Exception;

    /**
     * @throws Exception
     *
     */
    public void redo() throws Exception;
}
