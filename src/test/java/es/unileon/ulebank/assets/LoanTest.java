package es.unileon.ulebank.assets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.exceptions.LoanException;
import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.handler.exceptions.LINCMalformedException;
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
import es.unileon.ulebank.tasklist.TaskList;
import es.unileon.ulebank.time.Time;

public class LoanTest {

	private Loan loanWrongInterest;
	private Loan loanCancel;
	private Loan loanAmortize;
	private Loan loanNotAllowed;
	private Loan genericLoan;
	
	
	private Bank bank;
	private Office office;
	private Account commercialAccount1, commercialAccount2,commercialAccountWithoutMoney;
	private GenericHandler authorizedHandler1, authorizedHandler2;
	private Person authorized1, authorized2;
	private String description1,description2,description3;
	private Date dayCreation;
	Time time;

	@Before
	public void setUp() throws MalformedHandlerException, TransactionException,
			LoanException, LINCMalformedException, WrongArgsException, ParseException {
		time=Time.getInstance();
		SimpleDateFormat  format=new SimpleDateFormat("dd/MM/yyyy");
		dayCreation=format.parse("01/01/2014");
		Time.getInstance().setTime(dayCreation.getTime());
		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);
		
		this.authorizedHandler1 = new GenericHandler("Miguel");
		this.authorized1 = new Person(71560136, 'Y');

		this.authorizedHandler2 = new GenericHandler("Antonio");
		this.authorized2 = new Person(71557005, 'A');

		this.commercialAccount1 = new Account(this.office, this.bank,
				"0000000000", authorized1);
		this.commercialAccount2 = new Account(this.office, this.bank,
				"0000000001", authorized2);
		this.commercialAccountWithoutMoney = new Account(this.office, this.bank,
				"0000000002", authorized2);

		

		// we do a transaction of 40000 euros to the account1
		Transaction transaction1 = new GenericTransaction(40000, new Date(),
				"Salary");
		transaction1.setEffectiveDate(new Date());
		this.commercialAccount1.doTransaction(transaction1);

		// we do a transaction of 200000 euros to the account2
		Transaction transaction2 = new GenericTransaction(200000, new Date(),
				"Salary");
		transaction2.setEffectiveDate(new Date());
		this.commercialAccount2.doTransaction(transaction2);
		
		
		
		this.description1 = "Compra BMW-M3";
		this.description2 = "Hipoteca";
		this.description3 = "Hipoteca cara";

		// we create a loan of 100000 euros linked to the account
		this.loanCancel = new Loan(new FinancialProductHandler("LN", "ES"),
				100000, 0.15, PaymentPeriod.MONTHLY, 23,
				this.commercialAccount1,description1);
		this.genericLoan = new Loan(new FinancialProductHandler("LN", "ES"),
				200000, 0.15, PaymentPeriod.MONTHLY, 23,
				this.commercialAccount1,description3);

		this.loanAmortize = new Loan(new FinancialProductHandler("LN", "ES"),
				168000, 0.20, PaymentPeriod.ANNUAL, 72, this.commercialAccount2,description2);
		
		
	}
	@Test(expected = LoanException.class)
	public void testLoanNotAllowed() throws LoanException,
			LINCMalformedException, MalformedHandlerException {
		this.loanNotAllowed = new Loan(new FinancialProductHandler("LN", "ES"), 100000000, 0.01,
				PaymentPeriod.MONTHLY, 30, this.commercialAccount1,description1);
	}

	@Test(expected = LoanException.class)
	public void testLoanWrongInterest() throws LoanException,
			LINCMalformedException, MalformedHandlerException {
		this.loanWrongInterest = new Loan(new FinancialProductHandler("LN", "ES"), 50000, 20,
				PaymentPeriod.MONTHLY, 23, this.commercialAccount1,description2);
	}
	

	@Test(expected = LoanException.class)
	public void testCancelLoanNotEnoughMoneyToPay() throws LoanException {
		this.loanCancel.cancelLoan();
	}

	@Test(expected = LoanException.class)
	public void testLiquidateLoanNotEnoughMoneyToPay() throws LoanException {
		this.loanCancel.amortize(50000);
	}

	@Test
	public void testCancelLoan() throws LoanException, TransactionException {
		Transaction transaction = new GenericTransaction(100000, new Date(),
				"Salary");
		transaction.setEffectiveDate(new Date());
		this.commercialAccount1.doTransaction(transaction);
		assertEquals(100000, this.loanCancel.getDebt(), 0);
		this.loanCancel.cancelLoan();
		assertEquals(0, this.loanCancel.getDebt(), 0);
	}

	@Test
	public void testAmortizeQuantityToTheLoan() throws LoanException {
		assertEquals(168000, this.loanAmortize.getDebt(), 0);
		this.loanAmortize.amortize(50000);
		assertEquals(118000, this.loanAmortize.getDebt(), 0);
		this.loanAmortize.amortize(118000);
		assertEquals(0, this.loanAmortize.getDebt(), 0);
	}
	
	@Test
	public void testDescriptionLoan() throws LoanException {
		
		assertSame("Compra BMW-M3", loanCancel.getDescription());
		this.loanCancel.setDescription("Compra local");
		assertSame("Compra local", loanCancel.getDescription());
		assertSame("Hipoteca", loanAmortize.getDescription());
		this.loanAmortize.setDescription("Compra moto");
		assertSame("Compra moto", loanAmortize.getDescription());
	}
	
	@Test
	public void testLoanPaids() throws LoanException, ParseException{
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		assertEquals(form.format(this.genericLoan.getNextPayment().getExpiration()), "01/01/2014");
		this.genericLoan.paid();
		assertEquals(form.format(this.genericLoan.getNextPayment().getExpiration()), "01/01/2014");
		assertEquals(this.genericLoan.getDebt(),192440.67,0.01);
		assertEquals(this.genericLoan.getNextPayment().getAmortization(),7954.45,.1);
		this.genericLoan.paid();
		assertEquals(this.genericLoan.getDebt(),184486.19,1);
		
	}
	
	@Test
	public void testLoanPaidsInTheTaskList() throws Exception{
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		assertEquals(form.format(this.genericLoan.getNextPayment().getExpiration()), "01/01/2014");
		this.genericLoan.paid();
		assertEquals(this.genericLoan.getDebt(),192440.67,0.01);
		assertEquals(this.genericLoan.getNextPayment().getAmortization(),7954.48,0);
		time.forwardDays(31);
		TaskList taskList=TaskList.getInstance();
		taskList.executeTasks();
//		assertEquals(this.genericLoan.getDebt(),184486.19,1);
		
	}
	@Test
	public void forwadDates() throws ParseException{
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		Date date1=form.parse("01/01/2014");
		assertEquals(form.format(this.genericLoan.forwardDate(date1, 
				PaymentPeriod.MONTHLY)).toString(), "01/02/2014");
		
		assertEquals(form.format(this.genericLoan.forwardDate(date1, 
				PaymentPeriod.ANNUAL)).toString(), "01/01/2015");
		
		date1=form.parse("01/12/2014");
		assertEquals(form.format(this.genericLoan.forwardDate(date1, 
				PaymentPeriod.MONTHLY)).toString(), "01/01/2015");
		
			
	
	}
	@After
	public void tearDown(){
		Time.getInstance().forwardDays(600);
		//TaskList.DeleteTaskList();
		
		Time.getInstance().setTime(Calendar.getInstance().getTimeInMillis());
		
	}
	

}
