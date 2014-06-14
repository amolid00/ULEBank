/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.history;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import es.unileon.ulebank.exceptions.TransactionException;

/**
 * Generic transaction
 *
 * @author runix
 */
@Entity
@Table(name = "TRANSACTIONS", catalog = "ULEBANK_FINAL")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value="GenericTransaction") 
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
            String extraInfo) throws TransactionException {
        super(amount, date, subject, extraInfo);
    }

    public GenericTransaction() {

    }
}
