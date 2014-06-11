/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.exceptions;

/**
 *
 * @author runix
 */
public class TransactionException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param msg
     */
    public TransactionException(String msg) {
        super(msg);
    }
}
