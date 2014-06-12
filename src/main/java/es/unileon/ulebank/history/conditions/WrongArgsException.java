/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.history.conditions;

import es.unileon.ulebank.exceptions.CommandException;


/**
 *
 * @author runix
 */
public class WrongArgsException extends CommandException {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param msg
     */
    public WrongArgsException(String msg) {
        super(msg);
    }
}
