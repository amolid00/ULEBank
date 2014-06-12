package es.unileon.ulebank.assets.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.exceptions.LoanException;
import es.unileon.ulebank.assets.handler.GenericLoanHandler;
import es.unileon.ulebank.assets.strategy.loan.ScheduledPayment;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class LoanListTest {

	private LoanList<Loan> loanList;
	private Loan loan1, loan2;

	
	private Bank bank;
	private Office office;
	private Account account1, account2;
	private GenericHandler authorizedHandler1, authorizedHandler2;
	private Person authorized1, authorized2;
	private String description1,description2;


	@Before
	public void setUp() throws LoanException, MalformedHandlerException, TransactionException, WrongArgsException {

		
		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);

		
		this.authorizedHandler1 = new GenericHandler("Miguel");
		this.authorized1 = new Person(71560136,'Y');

		this.authorizedHandler2 = new GenericHandler("Antonio");
		this.authorized2 = new Person(71557005,'A');
		
		this.account1 = new Account(this.office, this.bank, "0000000000", authorized1);
		this.account2 = new Account(this.office, this.bank, "0000000001", authorized2);

		

		this.account1.addTitular(this.authorized1);
		this.account2.addTitular(this.authorized2);
		this.description1 = "Compra BMW-M3";
		this.description2 = "Compra moto";
		this.loan1 = new Loan(new GenericLoanHandler("LN-4-2014-ES-VF3TK-5"),
				10000, 0.14, PaymentPeriod.MONTHLY, 24, this.account1,description1);

		this.loan2 = new Loan(new GenericLoanHandler("LN-4-2014-ES-8T27V-0"),
				15000, 0.24, PaymentPeriod.ANNUAL, 4, this.account2,description2);

		this.loanList = new LoanList<Loan>();

		this.loanList.addLoan(this.loan1);
		this.loanList.addLoan(this.loan2);

		Transaction transaction1 = new GenericTransaction(20000, new Date(
				System.currentTimeMillis()), "Salary");
		transaction1.setEffectiveDate(new Date(System.currentTimeMillis()));
		this.account1.doTransaction(transaction1);
		
		Transaction transaction2 = new GenericTransaction(20000, new Date(
				System.currentTimeMillis()), "Salary");
		transaction2.setEffectiveDate(new Date(System.currentTimeMillis()));
		this.account2.doTransaction(transaction2);
	}

	@Test
	public void numberOfLoans() {
		assertEquals(2, this.loanList.numberOfLoans());
	}
	
	@Test
	public void addLoan() throws LoanException, MalformedHandlerException {
		assertEquals(2, this.loanList.numberOfLoans());
		boolean isNotAdd = this.loanList.addLoan(new Loan(new GenericLoanHandler("LN-4-2014-ES-VF3TK-5"),
				10000, 0.14, PaymentPeriod.MONTHLY, 24, this.account1,description1));
		assertFalse(isNotAdd);
		assertEquals(2, this.loanList.numberOfLoans());
	}
	
	@Test
	public void getLoan() throws MalformedHandlerException {
		assertNull(this.loanList.getLoan(new GenericLoanHandler("LN-4-2014-ES-CTXEV-0")));
		assertNotNull(this.loanList.getLoan(new GenericLoanHandler("LN-4-2014-ES-8T27V-0")));
	}

	@Test
	public void getTodayPayment() throws MalformedHandlerException {

		Date date = new Date(System.currentTimeMillis());

		ScheduledPayment paymentLoan1 = this.loanList.getPayment(
				new GenericLoanHandler("LN-4-2014-ES-VF3TK-5"), date);

		ScheduledPayment paymentLoan2 = this.loanList.getPayment(
				new GenericLoanHandler("LN-4-2014-ES-8T27V-0"), date);

		assertNotNull(paymentLoan1);
		assertNotNull(paymentLoan2);
	}

	@Test
	public void getAllPayments() throws MalformedHandlerException {

		List<ScheduledPayment> paymentsLoan1 = this.loanList
				.getPayments(new GenericLoanHandler("LN-4-2014-ES-VF3TK-5"));

		List<ScheduledPayment> paymentsLoan2 = this.loanList
				.getPayments(new GenericLoanHandler("LN-4-2014-ES-8T27V-0"));

		assertEquals(24, paymentsLoan1.size());
		assertEquals(4, paymentsLoan2.size());
	}

	@Test
	public void doTodayPayment() throws MalformedHandlerException {
		
		Date date = new Date(System.currentTimeMillis());
		DateWrap dateWrapMonthly = new DateWrap(date, PaymentPeriod.MONTHLY);
		DateWrap dateWrapAnnual = new DateWrap(date, PaymentPeriod.ANNUAL);
		
		
		ScheduledPayment paymentLoan1 = this.loanList.getPayment(
				new GenericLoanHandler("LN-4-2014-ES-VF3TK-5"), dateWrapMonthly.getDate());

		ScheduledPayment paymentLoan2 = this.loanList.getPayment(
				new GenericLoanHandler("LN-4-2014-ES-8T27V-0"), dateWrapAnnual.getDate());
		
		// is not null
		assertNotNull(paymentLoan1);
		assertNotNull(paymentLoan2);

		// is not paid
		assertFalse(paymentLoan1.isPaid());
		assertFalse(paymentLoan2.isPaid());
		
		this.loanList.doLoanPayments();

		// is paid
		assertTrue(paymentLoan1.isPaid());
		assertTrue(paymentLoan2.isPaid());
		
		this.loanList.forwardMonths(1);
		dateWrapMonthly.updateDate();
		
		paymentLoan1 = this.loanList.getPayment(
				new GenericLoanHandler("LN-4-2014-ES-VF3TK-5"), dateWrapMonthly.getDate());
		paymentLoan2 = this.loanList.getPayment(
				new GenericLoanHandler("LN-4-2014-ES-8T27V-0"), dateWrapMonthly.getDate());
		
		
		assertNotNull(paymentLoan1);
		assertNull(paymentLoan2);
		assertTrue(paymentLoan1.isPaid());
		
		dateWrapAnnual.updateDate();
		this.loanList.setDate(new Date(System.currentTimeMillis()));
		this.loanList.forwardYears(1);
		
		paymentLoan2 = this.loanList.getPayment(
				new GenericLoanHandler("LN-4-2014-ES-8T27V-0"), dateWrapAnnual.getDate());
		
		assertNotNull(paymentLoan2);
		assertTrue(paymentLoan2.isPaid());
	
	}
	
	@Test
	public void removeLoan() throws MalformedHandlerException {
		
		assertEquals(2, this.loanList.numberOfLoans());
		this.loanList.removeLoan(this.loan1);
		assertEquals(1, this.loanList.numberOfLoans());
		
	}
	
	@Test
	public void removeLoanHandler() throws MalformedHandlerException {
		
		assertEquals(2, this.loanList.numberOfLoans());
		this.loanList.removeLoan(new GenericLoanHandler("LN-4-2014-ES-8T27V-0"));
		assertEquals(1, this.loanList.numberOfLoans());
		
	}


}
