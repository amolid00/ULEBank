/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.handler;

/**
 *
 * @author runix
 */
public class MalformedHandlerException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param msg
     */
    public MalformedHandlerException(String msg) {
        super(msg);
    }
}
