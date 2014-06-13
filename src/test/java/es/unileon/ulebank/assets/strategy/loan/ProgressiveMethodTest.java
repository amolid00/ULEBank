package es.unileon.ulebank.assets.strategy.loan;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.exceptions.LoanException;
import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.handler.LoanIdentificationNumberCode;
import es.unileon.ulebank.assets.handler.exceptions.LINCMalformedException;
import es.unileon.ulebank.assets.iterator.LoanIterator;
import es.unileon.ulebank.assets.strategy.commission.exception.CommissionException;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class ProgressiveMethodTest {

	private ProgressiveMethod progressiveMethod1, progressiveMethod2;
	private Loan loan1, loan2;
	private LoanIdentificationNumberCode linc1, linc2;
	private FinancialProductHandler financialProduct1, financialProduct2;
	
	private Bank bank;
	private Office office;
	private Account commercialAccount;
	private GenericHandler authorizedHandler1;
	private Person authorized1;
	private String description1,description2;
	
	@Before
	public void setUp() throws LINCMalformedException, CommissionException,
			LoanException, MalformedHandlerException, WrongArgsException {
		
		this.bank = new Bank( new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);
		this.authorizedHandler1 = new GenericHandler("Dani");
		this.authorized1 = new Person(71560136,'Y');
		this.commercialAccount = new Account(office, bank, "0000000000", authorized1);
		

		this.linc1 = new LoanIdentificationNumberCode("LN", "ES");
		this.linc2 = new LoanIdentificationNumberCode("LN", "FR");

		this.financialProduct1 = new FinancialProductHandler("LN", "ES");
		this.financialProduct2 = new FinancialProductHandler("LN", "FR");
		this.description1 = "Compra BMW-M3";
		this.description2 = "Compra moto";
		
		 this.loan2 = new Loan(this.financialProduct1, 100000, 0.08,
	                PaymentPeriod.MONTHLY, 10, this.commercialAccount,
	                this.authorized1, this.description1);
	        this.loan1 = new Loan(this.financialProduct2, 50000, 0.1,
	                PaymentPeriod.MONTHLY, 10, this.commercialAccount,
	                this.authorized1, this.description2);
		this.progressiveMethod1 = new ProgressiveMethod(loan1, 5);
		this.progressiveMethod2 = new ProgressiveMethod(loan2, 5);

		this.progressiveMethod1.doCalculationOfPayments();
		this.progressiveMethod2.doCalculationOfPayments();
		
	}

	/*
	 * @Test public void testProgressiveMethod() { fail("Not yet implemented");
	 * }
	 * 
	 * @Test public void testCalculateMonthlyFee() {
	 * fail("Not yet implemented"); }
	 * 
	 * @Test public void testDoCalculationOfPayments() {
	 * fail("Not yet implemented"); }
	 */

	@Test
	public void testPayments() {
		LoanIterator loanIterator1 = this.loan1.iterator();
		LoanIterator loanIterator2 = this.loan2.iterator();
		while (loanIterator1.hasNext()) {
			assertEquals(5232.02, loanIterator1.next().getImportOfTerm(), 0.1);
		}
		while (loanIterator2.hasNext()) {
			assertEquals(10370.32, loanIterator2.next().getImportOfTerm(), 0.1);
		}
	}

	/*
	 * @Test public void testAmortization() { LoanIterator loanIterator1 =
	 * this.loan1.iterator(); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator1.next().getAmortization(), 0.1);
	 * 
	 * LoanIterator loanIterator2 = this.loan2.iterator(); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1); assertEquals(,
	 * loanIterator2.next().getAmortization(), 0.1);
	 * 
	 * ArrayList<ScheduledPayment> payments1 =
	 * this.progressiveMethod1.doCalculationOfPayments(); double
	 * amortizationCapital1 = 0.0; for (ScheduledPayment payment : payments1) {
	 * amortizationCapital1 += payment.getAmortization(); }
	 * 
	 * assertEquals(10000, amortizationCapital1, 1);
	 * 
	 * ArrayList<ScheduledPayment> payments2 =
	 * this.progressiveMethod2.doCalculationOfPayments(); double
	 * amortizationCapital2 = 0.0; for (ScheduledPayment payment : payments2) {
	 * amortizationCapital2 += payment.getAmortization(); }
	 * 
	 * assertEquals(50000, amortizationCapital2, 1);
	 * 
	 * }
	 */

	@Test
	public void testInterests() {
		LoanIterator loanIterator1 = this.loan1.iterator();
		assertEquals(416.67, loanIterator1.next().getInterests(), 0.1);
		assertEquals(376.54, loanIterator1.next().getInterests(), 0.1);
		assertEquals(336.08, loanIterator1.next().getInterests(), 0.1);
		assertEquals(295.28, loanIterator1.next().getInterests(), 0.1);
		assertEquals(254.14, loanIterator1.next().getInterests(), 0.1);
		assertEquals(212.65, loanIterator1.next().getInterests(), 0.1);
		assertEquals(170.83, loanIterator1.next().getInterests(), 0.1);
		assertEquals(128.65, loanIterator1.next().getInterests(), 0.1);
		assertEquals(86.12, loanIterator1.next().getInterests(), 0.1);
		assertEquals(43.24, loanIterator1.next().getInterests(), 0.1);

		LoanIterator loanIterator2 = this.loan2.iterator();
		assertEquals(666.67, loanIterator2.next().getInterests(), 0.1);
		assertEquals(601.98, loanIterator2.next().getInterests(), 0.1);
		assertEquals(536.85, loanIterator2.next().getInterests(), 0.1);
		assertEquals(471.3, loanIterator2.next().getInterests(), 0.1);
		assertEquals(405.3, loanIterator2.next().getInterests(), 0.1);
		assertEquals(338.87, loanIterator2.next().getInterests(), 0.1);
		assertEquals(271.99, loanIterator2.next().getInterests(), 0.1);
		assertEquals(204.67, loanIterator2.next().getInterests(), 0.1);
		assertEquals(136.9, loanIterator2.next().getInterests(), 0.1);
		assertEquals(68.68, loanIterator2.next().getInterests(), 0.1);

	}

	@Test
	public void testCapital() {
		LoanIterator loanIterator1 = this.loan1.iterator();
		assertEquals(45184.65, loanIterator1.next().getOutstandingCapital(), 0.1);
		assertEquals(40329.17, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(35433.22, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(30496.48, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(25518.6, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(20499.23, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(15438.04, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(10334.67, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(5188.77, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(0, loanIterator1.next().getOutstandingCapital(), 0.1);

		LoanIterator loanIterator2 = this.loan2.iterator();
		assertEquals(90296.35, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(80528.0, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(70694.53, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(60795.51, loanIterator2.next().getOutstandingCapital(),
				0.1);	
		assertEquals(50830.5, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(40799.05, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(30700.72, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(20535.07, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(10301.65, loanIterator2.next().getOutstandingCapital(), 0.1);
		assertEquals(0.01, loanIterator2.next().getOutstandingCapital(), 0.1);

	}

	@Test
	public void firstPaymentFirstLoanTest() {
		assertEquals(202.73, progressiveMethod1.doCalculationOfPayments()
				.get(0).getAmortization(), 0.01);
		assertEquals(202.73,
				progressiveMethod1.doCalculationOfPayments().get(0)
						.getAmortization(), 0.01);
		assertEquals(4.17, progressiveMethod1.doCalculationOfPayments().get(0)
				.getInterests(), 0.01);
		assertEquals(49797.27, progressiveMethod1.doCalculationOfPayments()
				.get(0).getOutstandingCapital(), 0.01);
	}

	@Test
	public void firstPaymentSecondLoanTest() {
		assertEquals(827.45, progressiveMethod2.doCalculationOfPayments().get(0)
				.getImportOfTerm(), 0.01);
		assertEquals(820.78, progressiveMethod2.doCalculationOfPayments().get(0)
				.getAmortization(), 0.01);
		assertEquals(6.67, progressiveMethod2.doCalculationOfPayments().get(0)
				.getInterests(), 0.01);
		assertEquals(99179.22, progressiveMethod2.doCalculationOfPayments().get(0)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void middlePaymentFirstLoanTest() {
		assertEquals(206.9, progressiveMethod1.doCalculationOfPayments()
				.get(4).getImportOfTerm(), 0.01);
		assertEquals(202.8,
				progressiveMethod1.doCalculationOfPayments().get(4)
						.getAmortization(), 0.01);
		assertEquals(4.1,
				progressiveMethod1.doCalculationOfPayments().get(4)
						.getInterests(), 0.01);
		assertEquals(48986.17, progressiveMethod1.doCalculationOfPayments()
				.get(4).getOutstandingCapital(), 0.01);
	}

	@Test
	public void middlePaymentSecondLoanTest() {
		assertEquals(827.45, progressiveMethod2.doCalculationOfPayments().get(4)
				.getImportOfTerm(), 0.01);
		assertEquals(821.0, progressiveMethod2.doCalculationOfPayments().get(4)
				.getAmortization(), 0.01);
		assertEquals(6.45, progressiveMethod2.doCalculationOfPayments().get(4)
				.getInterests(), 0.01);
		assertEquals(95895.54, progressiveMethod2.doCalculationOfPayments().get(4)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void lastPaymentTest() {
		assertEquals(206.9, progressiveMethod1.doCalculationOfPayments()
				.get(9).getImportOfTerm(), 0.01);
		assertEquals(202.89, progressiveMethod1.doCalculationOfPayments()
				.get(9).getAmortization(), 0.01);
		assertEquals(4.01,
				progressiveMethod1.doCalculationOfPayments().get(9)
						.getInterests(), 0.01);
		assertEquals(47971.91, progressiveMethod1.doCalculationOfPayments().get(9)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void lastPaymentSecondLoanTest() {
		assertEquals(827.45, progressiveMethod2.doCalculationOfPayments().get(9)
				.getImportOfTerm(), 0.01);
		assertEquals(821.28, progressiveMethod2.doCalculationOfPayments().get(9)
				.getAmortization(), 0.01);
		assertEquals(6.17, progressiveMethod2.doCalculationOfPayments().get(9)
				.getInterests(), 0.01);
		assertEquals(91789.7, progressiveMethod2.doCalculationOfPayments().get(9)
				.getOutstandingCapital(), 0.01);
	}

}