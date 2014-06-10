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
     *
     */
    public void execute();

    /**
     *
     */
    public void undo();

    /**
     *
     */
    public void redo();
}
