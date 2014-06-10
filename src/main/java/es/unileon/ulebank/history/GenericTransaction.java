/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.history;

import java.util.Date;

import es.unileon.ulebank.account.DetailedInformation;
import es.unileon.ulebank.exceptions.TransactionException;

/**
 * Generic transaction
 *
 * @author runix
 */
public class GenericTransaction extends Transaction {

    /**
     * Create a new generic transaction
     *
     * @param amount
     *            ( Transaction amount )
     * @param date
     *            ( Transaction date )
     * @param subject
     *            ( Transaction subject )
     */
    public GenericTransaction(double amount, Date date, String subject)
            throws TransactionException {
        super(amount, date, subject);
    }

    /**
     * Create a new generic transaction
     *
     * @param amount
     *            ( Transaction amount )
     * @param date
     *            ( Transaction date )
     * @param subject
     *            ( Transaction subject )
     */
    public GenericTransaction(double amount, Date date, String subject,
            DetailedInformation extraInfo) throws TransactionException {
        super(amount, date, subject, extraInfo);
    }
}
