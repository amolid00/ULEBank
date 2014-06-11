package es.unileon.ulebank.payments;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.exceptions.TransferException;
import es.unileon.ulebank.history.TransferTransaction;

/**
 * Transfer Class
 * @author Rober dCR
 * @date 01/04/2014
 * @brief Class about transfers between two accounts
 */
public class Transfer {

	/**
	 * Account from transfer the money
	 */
	private Account senderAccount;
	/**
	 * Account which receives the money
	 */
	private Account receiverAccount;
	/**
	 * Quantity of the transfer
	 */
	private double quantity;
	/**
	 * Transaction of the transfer
	 */
	private TransferTransaction transaction;

	/**
	 * Class constructor
	 * @param sender
	 * @param receiver
	 * @param quantity
	 * @throws TransferException 
	 */
	public Transfer(Account sender, Account receiver, double quantity) throws TransferException{
		if (!sender.equals(receiver)){
			this.senderAccount = sender;
			this.receiverAccount = receiver;
			this.quantity = quantity;
		} else {
			throw new TransferException("Sender Account number and Receiver Account number are the same.");
		}
	}
	
	/**
	 * Getter senderAccount
	 * @return senderAccount
	 */
	public Account getSenderAccount() {
		return senderAccount;
	}
	
	/**
	 * Getter receiverAccount
	 * @return Account
	 */
	public Account getReceiverAccount() {
		return receiverAccount;
	}

	/**
	 * Getter quantity
	 * @return double
	 */
	public double getQuantity() {
		return quantity;
	}
	
	/**
	 * Method that transfers money from sender to receiver
	 * @param sender
	 * @param receiver
	 * @param quantity
	 * @throws es.unileon.ulebank.history.TransactionException 
	 */
	public void make(String concept) throws TransferException, TransactionException{
		
//		try{
			//Discount the quantity from sender account
//			this.senderAccount.doWithdrawal(new TransferTransaction(quantity, new Date(), concept, this.receiverAccount, this.senderAccount));
			//Add the money to receiver account
//			this.receiverAccount.doDeposit(new TransferTransaction(quantity, new Date(), concept, this.receiverAccount, this.senderAccount));
//		}catch(TransactionException e){
//			throw new TransferException("Denegate Transaction");
//		}
	}

	/**
	 * Getter transaction
	 * @return TransferTransaction
	 */
	public TransferTransaction getTransaction() {
		return transaction;
	}

	/**
	 * Setter transaction
	 * @param transaction
	 */
	public void setTransaction(TransferTransaction transaction) {
		this.transaction = transaction;
	}

}
