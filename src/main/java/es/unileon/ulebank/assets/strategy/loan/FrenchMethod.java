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
 * Implementation of strategy for calculated all fee of the loan following the
 * french method
 * 
 * 
 * v1.0 Initial version
 */
public class FrenchMethod implements StrategyLoan {

	/**
	 * Object reference to the loan that wait calculating the fees
	 */
	private Loan loan;
	
	/**
	 * Object with the date for do the payments
	 */
	private DateWrap dateWrap;

	/**
	 * Array with all payments that you need for pay this loan
	 */
	private ArrayList<ScheduledPayment> payments;

	/**
	 * Constructor of FrenchMethod
	 * 
	 * @param loan
	 *            Loan that wait calculating the fees
	 */
	public FrenchMethod(Loan loan) {
		this.loan = loan;
		this.payments = new ArrayList<ScheduledPayment>();
		this.dateWrap = new DateWrap(new Date(Time.getInstance().getTime()), this.loan.getPaymentPeriod());
	}

	/**
	 * Method used to calculating the effective interest of do the fees
	 * 
	 * @return Double with the value of this effective interest
	 */
	private double calculateInterestEf() {
		double interestEf = 0;
		interestEf = this.loan.getInterest()
				/ this.loan.getPaymentPeriod().getPeriod();
		return interestEf;
	}

	/**
	 * Method used to calculating the fees of loan
	 * 
	 * @return Double with the value of this fees of loan
	 */
	private double calculateMonthlyFee() {
		double fee = 0;
		double interesEf = this.calculateInterestEf();
		int numFee = (this.loan.getAmortizationTime() / this.loan.getPaymentPeriod().getTime());
		double fracc = ((Math.pow((1 + interesEf), numFee)) * interesEf)
				/ (Math.pow(1 + interesEf, numFee) - 1);
		fee = this.loan.getAmountOfMoney() * fracc;
		return fee;
	}

	
	

	/**
	 * Method used to calculating the fees of loan and give dates for perform
	 * this payments
	 * 
	 * @return
	 */
	@Override
	public ArrayList<ScheduledPayment> doCalculationOfPayments() {
		ArrayList<ScheduledPayment> paymentsFrench = new ArrayList<ScheduledPayment>();

		int monthToAdd = 12 / this.loan.getPaymentPeriod().getPeriod();
		double fee = this.calculateMonthlyFee();
		double interestEf = this.calculateInterestEf();
		double interest = 0;
		double amortized = 0;
		double totalLoan = this.loan.getAmountOfMoney();
		double totalCapital = fee * this.loan.getAmortizationTime();
		
		for (int i = 0; i < this.loan.getAmortizationTime(); i++) {
			interest = totalLoan * interestEf;
			amortized = fee - interest;
			totalLoan -= amortized;

			totalCapital = round(totalLoan, 100);
			fee = round(fee, 100);
			amortized = round(amortized, 100);
			interest = round(interest, 100);
			ScheduledPayment scheduledPayment=new ScheduledPayment(this.dateWrap.getDate(), fee,
					amortized, interest, totalCapital,
					new ScheduledPaymentHandler(this.loan.getId(), this.loan.getLinkedAccount()
							.getTitulars(),this.dateWrap.getDate()));
			if(i==0){
			this.loan.setNextPayment(scheduledPayment);
			}
			
			
			paymentsFrench.add(scheduledPayment);
			this.dateWrap.updateDate();
		}
		this.dateWrap = new DateWrap(new Date(Time.getInstance().getTime()), this.loan.getPaymentPeriod());
		this.payments = paymentsFrench;
		return paymentsFrench;
	}

	/**
	 * Method used to round a number
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

}
