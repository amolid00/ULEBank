package es.unileon.ulebank.history;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.payments.exceptions.TransferException;

/**
 * Transaction for the Transfer
 * 
 * @author Rober dCR
 * @date 8/05/2014
 * @brief Class that allows all monetary transactions with accounts
 */
@Entity
@Table(name = "TRANSACTIONS", catalog = "ULEBANK_FINAL")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "TransferTransaction")
public class TransferTransaction extends GenericTransaction {

    /**
     * Account from transfer the money
     */
    private Account senderAccount;

    /**
     * Class constructor
     * 
     * @param amount
     * @param date
     * @param subject
     * @param senderAccount
     * @param receiverAccount
     * @throws TransferException
     * @throws TransactionException
     */
    public TransferTransaction(double amount, Date date, String subject,
            Account senderAccount) throws TransferException,
            TransactionException {
        super(amount, date, subject);
    }

    public TransferTransaction() {

    }

    /**
     * Getter Sender Account
     * 
     * @return
     */
    public Account getSenderAccount() {
        return this.senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }
}
