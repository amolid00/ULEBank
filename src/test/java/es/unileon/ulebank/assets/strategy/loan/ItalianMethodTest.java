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
import es.unileon.ulebank.assets.strategy.commission.LockValueCommission;
import es.unileon.ulebank.assets.strategy.commission.StrategyCommission;
import es.unileon.ulebank.assets.strategy.commission.exception.CommissionException;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class ItalianMethodTest {

	private ItalianMethod loan, newLoan2;
	private Loan loan2, newLoan;

	private ItalianMethod italianMethod1, italianMethod2;
	private Loan loan1;
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

		
		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);
		this.authorizedHandler1 = new GenericHandler("Miguel");
		this.authorized1 = new Person(71560136,'Y');
		this.commercialAccount = new Account(office, bank, "0000000000", authorized1);
		

		this.linc1 = new LoanIdentificationNumberCode("LN", "ES");
		this.linc2 = new LoanIdentificationNumberCode("LN", "NZ");

		this.financialProduct1 = new FinancialProductHandler(this.linc1);
		this.financialProduct2 = new FinancialProductHandler(this.linc2);
		this.description1 = "Compra BMW-M3";
		this.description2 = "Compra moto";
		
		StrategyCommission cancelCommission = new LockValueCommission(0.2);
		StrategyCommission openningCommission = new LockValueCommission(0.2);
		StrategyCommission modifyCommission = new LockValueCommission(0.2);
		StrategyCommission studyCommission = new LockValueCommission(0.2);

		loan2 = new Loan(this.financialProduct1, 100000, 0.08,
				PaymentPeriod.MONTHLY, 10, this.commercialAccount,description1);
		loan = new ItalianMethod(loan2);
		newLoan = new Loan(this.financialProduct2, 50000, 0.1,
				PaymentPeriod.MONTHLY, 10, this.commercialAccount,description2);
		newLoan2 = new ItalianMethod(newLoan);

		loan.doCalculationOfPayments();
		newLoan2.doCalculationOfPayments();

		loan1 = new Loan(this.financialProduct1, 3200, 0.07,
				PaymentPeriod.MONTHLY, 12, this.commercialAccount,description1);
		italianMethod1 = new ItalianMethod(loan1);

		loan2 = new Loan(this.financialProduct2, 50000, 0.1,
				PaymentPeriod.MONTHLY, 12, this.commercialAccount,description2);
		italianMethod2 = new ItalianMethod(loan2);

		this.italianMethod1.doCalculationOfPayments();
		this.italianMethod2.doCalculationOfPayments();
	}

	@Test
	public void testFirstPaymentFirstLoan() {

		assertEquals(18000, loan.doCalculationOfPayments().get(0).getImportOfTerm(),0.01);
		assertEquals(10000, loan.doCalculationOfPayments().get(0).getAmortization(), 0.01);
		assertEquals(8000, loan.doCalculationOfPayments().get(0).getInterests(), 0.01);
		assertEquals(90000, loan.doCalculationOfPayments().get(0).getOutstandingCapital(),
				0.01);
	}

	@Test
	public void firstPaymentFirstLoanTest() {
		assertEquals(490.67, italianMethod1.doCalculationOfPayments().get(0)
				.getImportOfTerm(), 0.01);
		assertEquals(266.67, italianMethod1.doCalculationOfPayments().get(0)
				.getAmortization(), 0.01);
		assertEquals(224, italianMethod1.doCalculationOfPayments().get(0).getInterests(),
				0.01);
		assertEquals(2933.33, italianMethod1.doCalculationOfPayments().get(0)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void testLastPaymentFirstLoan() {

		assertEquals(10800, loan.doCalculationOfPayments().get(9).getImportOfTerm(), 0.01);
		assertEquals(10000, loan.doCalculationOfPayments().get(9).getAmortization(), 0.01);
		assertEquals(800, loan.doCalculationOfPayments().get(9).getInterests(), 0.01);
		assertEquals(0, loan.doCalculationOfPayments().get(9).getOutstandingCapital(), 0.01);
	}

	@Test
	public void lastPaymentFirstLoanTest() {

		assertEquals(285.33, italianMethod1.doCalculationOfPayments().get(11)
				.getImportOfTerm(), 0.01);
		assertEquals(266.67, italianMethod1.doCalculationOfPayments().get(11)
				.getAmortization(), 0.01);
		assertEquals(18.66,
				italianMethod1.doCalculationOfPayments().get(11).getInterests(), 0.01);
		assertEquals(0, italianMethod1.doCalculationOfPayments().get(11)
				.getOutstandingCapital(), 0.05);
	}

	@Test
	public void testFirstPaymentSecondLoan() {

		assertEquals(10000, newLoan2.doCalculationOfPayments().get(0).getImportOfTerm(),
				0.01);
		assertEquals(5000, newLoan2.doCalculationOfPayments().get(0).getAmortization(),
				0.01);
		assertEquals(5000, newLoan2.doCalculationOfPayments().get(0).getInterests(), 0.01);
		assertEquals(45000, newLoan2.doCalculationOfPayments().get(0)
				.getOutstandingCapital(), 0.01);
	}

	public void firstPaymentSecondLoanTest() {

		assertEquals(9166.67, italianMethod2.doCalculationOfPayments().get(0)
				.getImportOfTerm(), 0.01);
		assertEquals(4166.67, italianMethod2.doCalculationOfPayments().get(0)
				.getAmortization(), 0.01);
		assertEquals(5000, italianMethod2.doCalculationOfPayments().get(0).getInterests(),
				0.01);
		assertEquals(45833.33, italianMethod2.doCalculationOfPayments().get(0)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void testMiddlePaymentSecondLoanly() {
		assertEquals(8000, newLoan2.doCalculationOfPayments().get(4).getImportOfTerm(),
				0.01);
		assertEquals(5000, newLoan2.doCalculationOfPayments().get(4).getAmortization(),
				0.01);
		assertEquals(3000, newLoan2.doCalculationOfPayments().get(4).getInterests(), 0.01);
		assertEquals(25000, newLoan2.doCalculationOfPayments().get(4)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void testMiddlePaymentSecondLoan() {
		assertEquals(7500, italianMethod2.doCalculationOfPayments().get(4)
				.getImportOfTerm(), 0.01);
		assertEquals(4166.67, italianMethod2.doCalculationOfPayments().get(4)
				.getAmortization(), 0.01);
		assertEquals(3333.33, italianMethod2.doCalculationOfPayments().get(4)
				.getInterests(), 0.01);
		assertEquals(29166.65, italianMethod2.doCalculationOfPayments().get(4)
				.getOutstandingCapital(), 0.01);
	}

	@Test
	public void testLastPaymentSecondLoan() {
		assertEquals(5500, newLoan2.doCalculationOfPayments().get(9).getImportOfTerm(),
				0.01);
		assertEquals(5000, newLoan2.doCalculationOfPayments().get(9).getAmortization(),
				0.01);
		assertEquals(500, newLoan2.doCalculationOfPayments().get(9).getInterests(), 0.01);
		assertEquals(0, newLoan2.doCalculationOfPayments().get(9).getOutstandingCapital(),
				0.01);
	}

	public void lastPaymentSecondLoanTest() {
		assertEquals(4583.33, italianMethod2.doCalculationOfPayments().get(11)
				.getImportOfTerm(), 0.01);
		assertEquals(4166.67, italianMethod2.doCalculationOfPayments().get(11)
				.getAmortization(), 0.01);
		assertEquals(416.66, italianMethod2.doCalculationOfPayments().get(11)
				.getInterests(), 0.01);
		assertEquals(0, italianMethod2.doCalculationOfPayments().get(11)
				.getOutstandingCapital(), 0.05);

	}
}
