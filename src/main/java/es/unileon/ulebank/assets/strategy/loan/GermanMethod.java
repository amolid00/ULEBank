package es.unileon.ulebank.assets.strategy.loan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.documents.GeneratePDFDocument;
import es.unileon.ulebank.assets.handler.ScheduledPaymentHandler;
import es.unileon.ulebank.assets.support.DateWrap;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.time.Time;

/**
 * Implementation of one of the strategies for calculate the payments for a
 * determine loan
 * 
 * @author CarlitosMayo
 * 
 */
public class GermanMethod implements StrategyLoan {

	/**
	 * Object reference to the loan that wait calculating the fees
	 */
	private Loan loan;
	/**
	 * Array where we store the payments of the loan
	 */
	private ArrayList<ScheduledPayment> payments;

	/**
	 * Date when the loan starts
	 */
	private DateWrap date;

	/**
	 * Constructor of the class
	 * 
	 * @param loan
	 *            The loan implemented for the strategy
	 */
	public GermanMethod(Loan loan) {
		this.loan = loan;
		this.payments = new ArrayList<ScheduledPayment>();
		this.date = new DateWrap(new Date(Time.getInstance().getTime()), this.loan.getPaymentPeriod());
	}

	/**
	 * This interface method calls to the method to calculate the payments
	 */
	@Override
	public ArrayList<ScheduledPayment> doCalculationOfPayments() {
		return doNewGermanMethod();
	}

	/**
	 * Method that calculates the fees for a determinated loan
	 * 
	 * @return payments The arraylist with the payments
	 */
	private ArrayList<ScheduledPayment> doNewGermanMethod() {
		ArrayList<ScheduledPayment> paymentsGerman = new ArrayList<ScheduledPayment>();

		double capital = this.loan.getDebt();
		double amortizationTerm = this.loan.getDebt() * this.loan.getInterest();
		amortizationTerm = round(amortizationTerm, 1);

		double interestsMoney = this.loan.getDebt() * this.loan.getInterest();
		interestsMoney = round(interestsMoney, 1);

		double amortization = amortizationTerm - interestsMoney;
		amortization = round(amortization, 1);

		capital = round(capital, 1);

		paymentsGerman
				.add(new ScheduledPayment(this.date.getDate(),
						amortizationTerm, amortization, interestsMoney,
						capital, new ScheduledPaymentHandler(this.loan.getId(),
								this.loan.getLinkedAccount().getTitulars(),
								this.date.getDate())));
		this.date.updateDate();

		amortizationTerm = (this.loan.getDebt() * this.loan.getInterest())
				/ (1.0 - Math.pow((1.0 - this.loan.getInterest()),
						this.loan.getAmortizationTime()));

		amortizationTerm = round(amortizationTerm, 1);

		int amortizationTime = this.loan.getAmortizationTime();
		int constant = 1;

		while (capital > 0) {
			// Calculo de la cualtia de capital amortizado
			capital = (amortizationTerm * (1.0 - Math.pow(
					1 - this.loan.getInterest(), amortizationTime - constant)))
					/ this.loan.getInterest();
			capital = round(capital, 1);
			constant++;

			interestsMoney = capital * this.loan.getInterest();
			interestsMoney = round(interestsMoney, 1);

			amortization = amortizationTerm - interestsMoney;
			amortization = round(amortization, 1);

			paymentsGerman.add(new ScheduledPayment(this.date.getDate(),
					amortizationTerm, amortization, interestsMoney, capital,
					new ScheduledPaymentHandler(this.loan.getId(), this.loan
							.getLinkedAccount().getTitulars(), this.date
							.getDate())));
			this.date.updateDate();
		}

		this.payments = paymentsGerman;
		return paymentsGerman;

	}

	/**
	 * This method is used for round some number to do more exactly the
	 * calculation of the fees and the final capital
	 * 
	 * @param num
	 *            Number that you want round
	 * @param factor
	 *            Number of decimals that you wait
	 * @return Round number
	 */
	private static double round(double num, int factor) {
		num = Math.round(num * factor);
		return num / factor;
	}

	/**
	 * This method return the payments list
	 * 
	 * @return payments List with every payments for a loan
	 */
	public ArrayList<ScheduledPayment> getPayments() {
		return this.payments;
	}

	/**
	 * To String method
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		for (ScheduledPayment payment : this.payments) {
			sb.append(payment.toString());
			sb.append("\n");
		}

		return sb.toString();
	}

}
