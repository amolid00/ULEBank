package es.unileon.ulebank.assets.history;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unileon.ulebank.assets.iterator.LoanIterator;
import es.unileon.ulebank.assets.iterator.LoanIteratorDates;
import es.unileon.ulebank.assets.strategy.loan.ScheduledPayment;

public class LoanHistory {
	/**
	 * Array where we store the fees of a determined loan
	 */
	private ArrayList<ScheduledPayment> payments;

	public LoanHistory() {
		this.payments = new ArrayList<ScheduledPayment>();
	}

	/**
	 * Add a new fee to the history
	 * 
	 * @param payment
	 *            An unique fee
	 * @return True if the payment is added correctly, False if the payment is
	 *         not added
	 */
	public boolean addPayment(ScheduledPayment payment) {
		return this.payments.add(payment);
	}

	/**
	 * Add the complete array of fees to the history of the loans
	 * 
	 * @param payments2
	 *            The loan payments complete
	 * @return True if the array is added, False if the array is not added to
	 *         the history
	 */
	public boolean addAllPayments(List<ScheduledPayment> payments2) {
		return this.payments.addAll(payments2);
	}

	/**
	 * Delete a specified payment
	 * 
	 * @param payment
	 *            Payment to delete
	 * @return True if the payment is deleted from the history, false if the
	 *         payment is not deleted from the history
	 */
	public boolean removePayment(ScheduledPayment payment) {
		//TODO Include a comprobation that payment exists
		return this.payments.remove(payment);
	}

	/**
	 * 
	 * @param startDate 
	 * @param endDate
	 * @return 
	 */

	public LoanIteratorDates iterator(Date startDate, Date endDate) {
		return new LoanIteratorDates(this.payments, startDate, endDate);
	}

	public LoanIterator iterator() {
		return new LoanIterator(this.payments);
	}

	/**
	 * Method to know how many payments are stored in the loans history
	 * @return number of payments stored in the history
	 */
	public int historySize() {
		return this.payments.size();
	}
}
