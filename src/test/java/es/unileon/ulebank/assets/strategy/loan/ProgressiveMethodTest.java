package es.unileon.ulebank.assets.strategy.loan;

import static org.junit.Assert.assertEquals;

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
		
		this.loan1 = new Loan(this.financialProduct1, 100000, 0.08,
				PaymentPeriod.MONTHLY, 10, this.commercialAccount,description1);
		this.loan2 = new Loan(this.financialProduct2, 50000, 0.1,
				PaymentPeriod.MONTHLY, 10, this.commercialAccount,description2);
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
			assertEquals(10003.67, loanIterator1.next().getImportOfTerm(), 0.1);
		}
		while (loanIterator2.hasNext()) {
			assertEquals(5002.29, loanIterator2.next().getImportOfTerm(), 0.1);
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
		assertEquals(6.67, loanIterator1.next().getInterests(), 0.1);
		assertEquals(6.0, loanIterator1.next().getInterests(), 0.1);
		assertEquals(5.33, loanIterator1.next().getInterests(), 0.1);
		assertEquals(4.67, loanIterator1.next().getInterests(), 0.1);
		assertEquals(4.0, loanIterator1.next().getInterests(), 0.1);
		assertEquals(3.33, loanIterator1.next().getInterests(), 0.1);
		assertEquals(2.67, loanIterator1.next().getInterests(), 0.1);
		assertEquals(2.0, loanIterator1.next().getInterests(), 0.1);
		assertEquals(1.33, loanIterator1.next().getInterests(), 0.1);
		assertEquals(0.67, loanIterator1.next().getInterests(), 0.1);

		LoanIterator loanIterator2 = this.loan2.iterator();
		assertEquals(4.17, loanIterator2.next().getInterests(), 0.1);
		assertEquals(3.75, loanIterator2.next().getInterests(), 0.1);
		assertEquals(3.33, loanIterator2.next().getInterests(), 0.1);
		assertEquals(2.92, loanIterator2.next().getInterests(), 0.1);
		assertEquals(2.5, loanIterator2.next().getInterests(), 0.1);
		assertEquals(2.08, loanIterator2.next().getInterests(), 0.1);
		assertEquals(1.67, loanIterator2.next().getInterests(), 0.1);
		assertEquals(1.25, loanIterator2.next().getInterests(), 0.1);
		assertEquals(0.83, loanIterator2.next().getInterests(), 0.1);
		assertEquals(0.42, loanIterator2.next().getInterests(), 0.1);

	}

	@Test
	public void testCapital() {
		LoanIterator loanIterator1 = this.loan1.iterator();
		assertEquals(90003.0, loanIterator1.next().getOutstandingCapital(), 0.1);
		assertEquals(80005.33, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(70006.99, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(60007.99, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(50008.32, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(40007.99, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(30006.98, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(20005.31, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(10002.98, loanIterator1.next().getOutstandingCapital(),
				0.1);
		assertEquals(0, loanIterator1.next().getOutstandingCapital(), 0.1);

		LoanIterator loanIterator2 = this.loan2.iterator();
		assertEquals(45001.87, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(40003.33, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(35004.38, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(30005.01, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(25005.22, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(20005.01, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(15004.39, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(10003.35, loanIterator2.next().getOutstandingCapital(),
				0.1);
		assertEquals(5001.89, loanIterator2.next().getOutstandingCapital(), 0.1);
		assertEquals(0, loanIterator2.next().getOutstandingCapital(), 0.1);

	}

	@Test
	public void firstPaymentFirstLoanTest() {
		assertEquals(12219.63, progressiveMethod1.doCalculationOfPayments()
				.get(0).getAmortization(), 0.01);
		assertEquals(4219.63,
				progressiveMethod1.doCalculationOfPayments().get(0)
						.getAmortization(), 0.01);
		assertEquals(8000, progressiveMethod1.doCalculationOfPayments().get(0)
				.getInterests(), 0.01);
		assertEquals(95780.37, progressiveMethod1.doCalculationOfPayments()
				.get(0).getOutstandingCapital(), 0.01);
	}

	@Test
	public void firstPaymentSecondLoanTest() {
		assertEquals(8000, progressiveMethod2.doCalculationOfPayments().get(0)
				.getImportOfTerm(), 0.01);
		assertEquals(0.0, progressiveMethod2.doCalculationOfPayments().get(0)
				.getAmortization(), 0.01);
		assertEquals(8000, progressiveMethod2.doCalculationOfPayments().get(0)
				.getInterests(), 0.01);
		assertEquals(92000, progressiveMethod2.doCalculationOfPayments().get(0)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void middlePaymentFirstLoanTest() {
		assertEquals(14853.04, progressiveMethod1.doCalculationOfPayments()
				.get(4).getImportOfTerm(), 0.01);
		assertEquals(8693.49,
				progressiveMethod1.doCalculationOfPayments().get(4)
						.getAmortization(), 0.01);
		assertEquals(6159.55,
				progressiveMethod1.doCalculationOfPayments().get(4)
						.getInterests(), 0.01);
		assertEquals(68300.91, progressiveMethod1.doCalculationOfPayments()
				.get(4).getOutstandingCapital(), 0.01);
	}

	@Test
	public void middlePaymentSecondLoanTest() {
		assertEquals(8000, progressiveMethod2.doCalculationOfPayments().get(4)
				.getImportOfTerm(), 0.01);
		assertEquals(0.0, progressiveMethod2.doCalculationOfPayments().get(4)
				.getAmortization(), 0.01);
		assertEquals(8000, progressiveMethod2.doCalculationOfPayments().get(4)
				.getInterests(), 0.01);
		assertEquals(92000, progressiveMethod2.doCalculationOfPayments().get(4)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void lastPaymentTest() {
		assertEquals(18956.66, progressiveMethod1.doCalculationOfPayments()
				.get(9).getImportOfTerm(), 0.01);
		assertEquals(17552.46, progressiveMethod1.doCalculationOfPayments()
				.get(9).getAmortization(), 0.01);
		assertEquals(1404.20,
				progressiveMethod1.doCalculationOfPayments().get(9)
						.getInterests(), 0.01);
		assertEquals(0.0, progressiveMethod1.doCalculationOfPayments().get(9)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void lastPaymentSecondLoanTest() {
		assertEquals(8000, progressiveMethod2.doCalculationOfPayments().get(9)
				.getImportOfTerm(), 0.01);
		assertEquals(0.0, progressiveMethod2.doCalculationOfPayments().get(9)
				.getAmortization(), 0.01);
		assertEquals(8000, progressiveMethod2.doCalculationOfPayments().get(9)
				.getInterests(), 0.01);
		assertEquals(92000, progressiveMethod2.doCalculationOfPayments().get(9)
				.getOutstandingCapital(), 0.01);
	}

}