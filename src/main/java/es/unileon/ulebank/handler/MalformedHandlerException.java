/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.handler;

import es.unileon.ulebank.exceptions.CommandException;

/**
 *
 * @author runix
 */
public class MalformedHandlerException extends CommandException {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param msg
     */
    public MalformedHandlerException(String msg) {
        super(msg);
    }
}
