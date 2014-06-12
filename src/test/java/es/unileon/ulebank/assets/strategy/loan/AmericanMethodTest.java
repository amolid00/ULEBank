package es.unileon.ulebank.assets.strategy.loan;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.exceptions.LoanException;
import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.handler.exceptions.LINCMalformedException;
import es.unileon.ulebank.assets.strategy.commission.LockValueCommission;
import es.unileon.ulebank.assets.strategy.commission.exception.CommissionException;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class AmericanMethodTest {

	private Loan loan1; 
	
    private Bank bank;
    private Office office;
    private Person authorized1;
    private GenericHandler authorizedhandler1;
    private Account commercialAccount1, commercialAccount2;
    private FinancialProductHandler handlerLoan1;
	private FinancialProductHandler handlerLoan2;
	private FinancialProductHandler handlerLoan3;
	private FinancialProductHandler handlerLoan4;
	private Loan loanTest;
	private Loan loanTest2;
	private Loan loanOpenTest;
	private AmericanMethod americanMethod;
	private AmericanMethod test;
	private AmericanMethod test2;
	private AmericanMethod testOpen;
	private String description1,description2,description3,description4;
	
	@Before
	public void setUp() throws LINCMalformedException, CommissionException, LoanException, MalformedHandlerException, TransactionException, WrongArgsException {

		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);
		this.authorizedhandler1 = new GenericHandler("Carlitos");
		this.authorized1 = new Person(71560136,'Y');
		
		this.commercialAccount1 = new Account(office, bank, "0000000000", authorized1);
		this.commercialAccount2 = new Account(office, bank, "0000000001", authorized1);
		
	    this.handlerLoan1 = new FinancialProductHandler("LN", "ES");
		this.handlerLoan2 = new FinancialProductHandler("LN", "ES");
		this.handlerLoan3 = new FinancialProductHandler("LN", "ES");
		this.handlerLoan4 = new FinancialProductHandler("LN", "FR");

		new LockValueCommission(0.2);
		new LockValueCommission(0.2);
		new LockValueCommission(0.2);
		new LockValueCommission(0.2);
		this.description1 = "Compra BMW-M3";
		this.description2 = "Compra moto";
		this.description3 = "Hipoteca";
		this.description4 = "Compra local";
		
		this.loan1 = new Loan(this.handlerLoan1,
				10000000, 0.10, PaymentPeriod.ANNUAL, 5, this.commercialAccount1,description1);
		this.loanTest = new Loan(handlerLoan2, 100000,
				0.08, PaymentPeriod.MONTHLY, 10,this.commercialAccount1,description2);
		this.loanTest2 = new Loan(handlerLoan3, 50000,
				0.10, PaymentPeriod.MONTHLY, 10,this.commercialAccount1,description3);
		this.loanOpenTest = new Loan(handlerLoan4, 100000,
				0.08, PaymentPeriod.MONTHLY, 10,this.commercialAccount1,description4);

		this.americanMethod = new AmericanMethod(this.loan1, 12);
		this.test = new AmericanMethod(this.loanTest, 12);
		this.test2 = new AmericanMethod(loanTest2, 12);
		this.testOpen = new AmericanMethod(loanOpenTest, 12);
		this.americanMethod.doCalculationOfPayments();
		this.test.doCalculationOfPayments();
		this.test2.doCalculationOfPayments();
		this.testOpen.doCalculationOfPayments();
	}
	
	@Test
	public void testPaymentInterest() {		
		ArrayList<ScheduledPayment> payments = this.americanMethod.doCalculationOfPayments();
		for (ScheduledPayment payment : payments) {
			assertEquals(1000000, payment.getInterests(), 0.0);
		}
	}

	@Test
	public void testPaymentAmortization() {
		ArrayList<ScheduledPayment> payments = this.americanMethod
				.doCalculationOfPayments();
		for (ScheduledPayment payment : payments) {
			assertEquals(1574097.32, payment.getAmortization(), 0.0);
		}
	}

	@Test
	public void testPayments() {
		ArrayList<ScheduledPayment> payments = this.americanMethod
				.doCalculationOfPayments();
		for (ScheduledPayment payment : payments) {
			assertEquals(2574097.32, payment.getImportOfTerm(), 0.0);
		}
	}

	@Test
	public void testCapital() {
		ArrayList<ScheduledPayment> payments = this.americanMethod.doCalculationOfPayments();;
		assertEquals(8425903.0, payments.get(0).getOutstandingCapital(), 0.1);
		assertEquals(6662914.0, payments.get(1).getOutstandingCapital(), 0.1);
		assertEquals(4688366.0, payments.get(2).getOutstandingCapital(), 0.1);
		assertEquals(2476873.0, payments.get(3).getOutstandingCapital(), 0.1);
		assertEquals(0.0, payments.get(4).getOutstandingCapital(), 0.1);

		double outstandingCapital = 0.0;
		for (ScheduledPayment payment : payments) {
			outstandingCapital = outstandingCapital * (1 + 0.12)
					+ payment.getAmortization();
		}

		assertEquals(10000000.0, outstandingCapital, 0.1);

	}
	
	@Test
	public void testCancelLoan() throws LoanException, TransactionException {
		Transaction transaction = new GenericTransaction(200000000, new Date(System.currentTimeMillis()), "Salary");
		transaction.setEffectiveDate(new Date(System.currentTimeMillis()));
		this.commercialAccount1.doTransaction(transaction);
		assertEquals(10000000, this.loan1.cancelLoan(), 0.1);
	}

}
