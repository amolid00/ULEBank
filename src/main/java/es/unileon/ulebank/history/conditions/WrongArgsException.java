/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.history.conditions;


/**
 *
 * @author runix
 */
public class WrongArgsException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param msg
     */
    public WrongArgsException(String msg) {
        super(msg);
    }
}
