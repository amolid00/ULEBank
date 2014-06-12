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
import es.unileon.ulebank.assets.strategy.commission.exception.CommissionException;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class FrenchMethodTest {

	private FrenchMethod frenchMethod1, frenchMethod2, testLoanOpening2;
	private Loan loan1, loan2, testLoanOpening;
	private LoanIdentificationNumberCode linc1, linc2;
	private FinancialProductHandler financialProduct1, financialProduct2,
			financialProduct3;

	
	private Bank bank;
	private Office office;
	private Account commercialAccount;
	private GenericHandler authorizedHandler1;
	private Client authorized1;
	private String description1,description2,description3;
	
	@Before
	public void setUp() throws LINCMalformedException, CommissionException,
			LoanException, MalformedHandlerException, WrongArgsException {

		
		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);
		this.authorizedHandler1 = new GenericHandler("Miguel");
		this.authorized1 = new Person(71560136,'Y');
		this.commercialAccount = new Account(office, bank, "0000000000", authorized1);
		

		this.linc1 = new LoanIdentificationNumberCode("LN", "ES");
		this.linc2 = new LoanIdentificationNumberCode("LN", "NZ");

		this.financialProduct1 = new FinancialProductHandler(this.linc1);
		this.financialProduct2 = new FinancialProductHandler(this.linc2);
		this.financialProduct3 = new FinancialProductHandler(this.linc1);

		this.description1 = "Compra BMW-M3";
		this.description2 = "Compra moto";
		this.description3 = "Hipoteca";
		loan2 = new Loan(this.financialProduct1, 100000, 0.8,
				PaymentPeriod.MONTHLY, 10, this.commercialAccount,description1);
		loan1 = new Loan(this.financialProduct2, 50000, 0.1,
				PaymentPeriod.MONTHLY, 10, this.commercialAccount,description2);

		testLoanOpening = new Loan(financialProduct3, 100000, 0.08,
				PaymentPeriod.MONTHLY, 10, this.commercialAccount,description3);

		testLoanOpening2 = new FrenchMethod(testLoanOpening);

		frenchMethod1 = new FrenchMethod(loan1);
		frenchMethod2 = new FrenchMethod(loan2);
		
		frenchMethod1.doCalculationOfPayments();
		frenchMethod2.doCalculationOfPayments();
		testLoanOpening2.doCalculationOfPayments();
	}

//	@Test
//	public void testCalcInterestEf() {
//
//		assertEquals(frenchMethod1.calculateInterestEf(), 0.00666, 0.1);
//
//	}
//
//	@Test
//	public void testCalcMonthlyFee() {
//
//		assertEquals(frenchMethod1.calculateMonthlyFee(), 10370.32, 0.01);
//	}

	@Test
	public void testFirstPaymentLoan1() {
		assertEquals(10370.32, frenchMethod1.doCalculationOfPayments().get(0)
				.getImportOfTerm(), 0.01);
		assertEquals(9703.65, frenchMethod1.doCalculationOfPayments().get(0)
				.getAmortization(), 0.01);
		assertEquals(666.67, frenchMethod1.doCalculationOfPayments().get(0)
				.getInterests(), 0.01);
		assertEquals(90296.35, frenchMethod1.doCalculationOfPayments().get(0)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void testLastPaymentLoan1() {
		assertEquals(10370.32, frenchMethod1.doCalculationOfPayments().get(9)
				.getImportOfTerm(), 0.01);
		assertEquals(10301.64, frenchMethod1.doCalculationOfPayments().get(9)
				.getAmortization(), 0.01);
		assertEquals(68.68, frenchMethod1.doCalculationOfPayments().get(9)
				.getInterests(), 0.01);
		assertEquals(0.00, frenchMethod1.doCalculationOfPayments().get(9)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void testFirstPaymentLoan2() {
		assertEquals(5232.02, frenchMethod2.doCalculationOfPayments().get(0)
				.getImportOfTerm(), 0.01);
		assertEquals(4815.35, frenchMethod2.doCalculationOfPayments().get(0)
				.getAmortization(), 0.01);
		assertEquals(416.67, frenchMethod2.doCalculationOfPayments().get(0)
				.getInterests(), 0.01);
		assertEquals(45184.65, frenchMethod2.doCalculationOfPayments().get(0)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void middlePaymentOtherValues() {
		assertEquals(5232.02, frenchMethod2.doCalculationOfPayments().get(4)
				.getImportOfTerm(), 0.01);
		assertEquals(4977.88, frenchMethod2.doCalculationOfPayments().get(4)
				.getAmortization(), 0.01);
		assertEquals(254.14, frenchMethod2.doCalculationOfPayments().get(4)
				.getInterests(), 0.01);
		assertEquals(25518.60, frenchMethod2.doCalculationOfPayments().get(4)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void testLastPaymentLoan2() {
		assertEquals(5232.02, frenchMethod2.doCalculationOfPayments().get(9)
				.getImportOfTerm(), 0.01);
		assertEquals(5188.78, frenchMethod2.doCalculationOfPayments().get(9)
				.getAmortization(), 0.01);
		assertEquals(43.24, frenchMethod2.doCalculationOfPayments().get(9)
				.getInterests(), 0.01);
		assertEquals(0.0, frenchMethod2.doCalculationOfPayments().get(9)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void openingAndStudyComissionTest() {
		assertEquals(10474.02, testLoanOpening2.doCalculationOfPayments()
				.get(0).getImportOfTerm(), 0.01);
		assertEquals(9800.69, testLoanOpening2.doCalculationOfPayments().get(0)
				.getAmortization(), 0.01);
		assertEquals(673.33, testLoanOpening2.doCalculationOfPayments().get(0)
				.getInterests(), 0.01);
		assertEquals(91199.31, testLoanOpening2.doCalculationOfPayments()
				.get(0).getOutstandingCapital(), 0.01);
	}

}
