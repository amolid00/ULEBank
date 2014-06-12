package es.unileon.ulebank.history;

import java.util.Date;

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
            Account senderAccount, Account receiverAccount)
            throws TransferException, TransactionException {
        super(amount, date, subject);

        if (!senderAccount.equals(receiverAccount)) {
            this.senderAccount = senderAccount;
        } else {
            throw new TransferException(
                    "Sender Account number and Receiver Account number are the same.");
        }
    }

    /**
     * Getter Sender Account
     * 
     * @return
     */
    public Account getSenderAccount() {
        return this.senderAccount;
    }

}
