/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.exceptions;

import es.unileon.ulebank.payments.exceptions.PaymentException;

/**
 *
 * @author runix
 */
public class TransactionException extends PaymentException {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param msg
     */
    public TransactionException(String msg) {
        super(msg);
    }
}
