package es.unileon.ulebank.assets.strategy.loan;

import java.util.ArrayList;
import java.util.Date;

import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.documents.GeneratePDFDocument;
import es.unileon.ulebank.assets.handler.ScheduledPaymentHandler;
import es.unileon.ulebank.assets.support.DateWrap;
import es.unileon.ulebank.assets.support.PaymentPeriod;

public class AmericanMethod implements StrategyLoan {

	/**
	 * Object reference to the loan that wait calculating the fees
	 */
	private Loan loan;
	/**
	 * Array with all payments that you need for pay this loan
	 */

	private ArrayList<ScheduledPayment> payments;

	/**
	 * Effective interest for calculate the fees with this method
	 */
	private double effectiveRate;
	/**
	 * Object with the date for do the payments
	 */
	private DateWrap date;
	
	/**
	 * Constructor of the class
	 * @param loan
	 * @param effectiveRate
	 */
	public AmericanMethod(Loan loan, double effectiveRate) {
		this.payments = new ArrayList<ScheduledPayment>();
		this.effectiveRate = effectiveRate / 100;
		this.loan = loan;
		this.date = new DateWrap(new Date(), this.loan.getPaymentPeriod());
	}

	/**
	 * Calculate the fee that the client are going to pay in the loan
	 */
	@Override
	public ArrayList<ScheduledPayment> doCalculationOfPayments() {
		ArrayList<ScheduledPayment> paymentsAmerican = new ArrayList<ScheduledPayment>();

		double money = this.loan.getDebt();
		double paymentInterest = this.loan.getDebt() * this.loan.getInterest();
		double fracc = ((Math.pow((1.0 + this.effectiveRate), this.loan.getAmortizationTime())) - 1.0) / this.effectiveRate;
		double paymentAmortization = this.loan.getDebt() / fracc;
		double payment = paymentInterest + paymentAmortization;

		double factor = 0;

		while (factor < this.loan.getDebt()) {
			paymentAmortization = round(paymentAmortization, 100);
			paymentInterest = round(paymentInterest, 100);
			payment = round(payment, 100);

			factor = factor * (1 + this.effectiveRate) + paymentAmortization;

			factor = AmericanMethod.round(factor, 1);

			money = this.loan.getDebt() - factor;

			paymentsAmerican.add(new ScheduledPayment(this.date.getDate(),
					payment, paymentAmortization, paymentInterest, money,
					new ScheduledPaymentHandler(this.loan.getId(), this.loan.getLinkedAccount()
							.getTitulars(), this.date.getDate())));

			this.date.updateDate();
		}

		this.payments = paymentsAmerican;
		return paymentsAmerican;

	}
	/**
	 * This method round number to calculate the fees more exactly
	 * @param num Num to round
	 * @param factor Where number you want to round
	 * @return The rounded number
	 */
	private static double round(double num, int factor) {
		num = Math.round(num * factor);
		return num / factor;
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
