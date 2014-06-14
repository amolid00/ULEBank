package es.unileon.ulebank.history;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import es.unileon.ulebank.exceptions.TransactionException;

/**
 * Transaction for the Card
 * 
 * @author Rober dCR
 * @date 8/05/2014
 * @brief Class that allows all monetary transactions with cards
 */
@Entity
@Table(name = "TRANSACTIONS", catalog = "ULEBANK_FINAL")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "CardTransaction")
public class CardTransaction extends Transaction {

    /**
     * Class constructor
     * 
     * @param amount
     * @param datezz
     * @param subject
     * @param type
     * @param senderAccount
     * @param receiverAccount
     * @throws TransactionException
     * @throws es.unileon.ulebank.exceptions.TransactionException
     */
    public CardTransaction(double amount, Date date, String subject)
            throws TransactionException {
        super(amount, date, subject);
    }
    
    public CardTransaction() {
        
    }

}
