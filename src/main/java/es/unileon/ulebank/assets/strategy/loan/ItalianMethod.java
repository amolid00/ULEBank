package es.unileon.ulebank.assets.strategy.loan;

import java.util.ArrayList;
import java.util.Date;

import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.handler.ScheduledPaymentHandler;
import es.unileon.ulebank.assets.support.DateWrap;
import es.unileon.ulebank.time.Time;

public class ItalianMethod implements StrategyLoan {
	/**
	 * Object reference to the loan that wait calculating the fees
	 */
	private Loan loan;

	/**
	 * Array with all payments that you need for pay this loan
	 */
	private ArrayList<ScheduledPayment> payments;

	/**
	 * Object with the date for do the payments
	 */
	private DateWrap date;

	/**
	 * Constructor of italian method
	 * 
	 * @param Loan
	 *            The loan that decides calculates the fees with this method
	 */
	public ItalianMethod(Loan loan) {
		this.loan = loan;
		this.date = new DateWrap(new Date(Time.getInstance().getTime()), this.loan.getPaymentPeriod());
		this.payments = new ArrayList<ScheduledPayment>();
	}

	/**
	 * Calculate the import of the fees
	 * 
	 * @return paymentsItalian The list with every fees
	 */
	private ArrayList<ScheduledPayment> doItalianMethod() {
		ArrayList<ScheduledPayment> paymentsItalian = new ArrayList<ScheduledPayment>();

		double moneyPending = this.loan.getDebt();
		double moneyInterest = moneyPending * this.loan.getInterest();
		double moneyAmortization = this.loan.getDebt()
				/ this.loan.getAmortizationTime();
		double amortization = moneyInterest + moneyAmortization;

		amortization = round(amortization, 100);
		moneyPending = round(moneyPending, 100);
		moneyInterest = round(moneyInterest, 100);
		moneyAmortization = round(moneyAmortization, 100);

		moneyPending -= moneyAmortization;
		moneyPending = round(moneyPending, 100);
		paymentsItalian
				.add(new ScheduledPayment(this.date.getDate(), amortization,
						moneyAmortization, moneyInterest, moneyPending,
						new ScheduledPaymentHandler(this.loan.getId(),
								this.loan.getLinkedAccount().getTitulars(),
								this.date.getDate())));

		this.date.updateDate();

		while (moneyPending >= 0) {

			moneyInterest = moneyPending * this.loan.getInterest();
			amortization = moneyAmortization + moneyInterest;

			amortization = round(amortization, 100);
			moneyPending = round(moneyPending, 100);
			moneyInterest = round(moneyInterest, 100);
			moneyAmortization = round(moneyAmortization, 100);

			moneyPending -= moneyAmortization;
			moneyPending = round(moneyPending, 100);

			paymentsItalian.add(new ScheduledPayment(this.date.getDate(),
					amortization, moneyAmortization, moneyInterest,
					moneyPending, new ScheduledPaymentHandler(
							this.loan.getId(), this.loan.getLinkedAccount()
									.getTitulars(), this.date.getDate())));
			this.date.updateDate();
		}

		this.payments = paymentsItalian;
		return paymentsItalian;

	}

	/**
	 * Interface method that calls the method to calculate the fees for a
	 * determinated loan
	 */
	@Override
	public ArrayList<ScheduledPayment> doCalculationOfPayments() {
		return doItalianMethod();
	}

	/**
	 * To String method
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();

		for (ScheduledPayment payment : this.payments) {
			sb.append(payment.toString());
			sb.append("\n");
		}

		return sb.toString();
	}

	/**
	 * This method give us the list of the fees
	 * 
	 * @return
	 */
	private ArrayList<ScheduledPayment> getPayments() {
		return this.payments;
	}

	/**
	 *Method used for round the fees to two decimal number
	 * 
	 * @param num
	 * @param factor
	 * @return Number rounded (double)
	 */
	private static double round(double num, int factor) {
		num = Math.round(num * factor);
		return num / factor;
	}


}
