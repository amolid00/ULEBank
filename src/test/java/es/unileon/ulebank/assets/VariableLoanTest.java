package es.unileon.ulebank.assets;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.financialproducts.Euribor;
import es.unileon.ulebank.assets.financialproducts.InterestRate;
import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.tasklist.Task;
import es.unileon.ulebank.tasklist.TaskList;
import es.unileon.ulebank.time.Time;

public class VariableLoanTest {

	private Bank bank;
	private Office office;
	private GenericHandler authorizedHandler1;
	private Person authorized1;
	private Account commercialAccount1;
	private String description1;
	private VariableLoan variableLoan;
	private InterestRate interestRate;
	private Date dayCreation;

	@Before
	public void setUp() throws Exception {
		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);
		
		this.authorized1 = new Person(71560136,'Y');
		SimpleDateFormat  format=new SimpleDateFormat("yyyy/mm/dd");
		
		dayCreation=format.parse("2014/01/10");
		Time.getInstance().setTime(dayCreation.getTime());
		
		
		this.commercialAccount1 = new Account(this.office, this.bank,
				"0000000000", authorized1);
		
		

		// we do a transaction of 40000 euros to the account1
		Transaction transaction1 = new GenericTransaction(40000, new Date(),
				"Salary");
		transaction1.setEffectiveDate(new Date());
		this.commercialAccount1.doTransaction(transaction1);

		// we do a transaction of 200000 euros to the account2
		
		
		this.description1 = "Compra BMW-M3";

		// we create a loan of 100000 euros linked to the account
		
		this.interestRate=new Euribor();
		this.variableLoan = new VariableLoan(new FinancialProductHandler("LN", "ES"),
				168000, 0.20, PaymentPeriod.ANNUAL, 72, this.commercialAccount1,description1, interestRate,PaymentPeriod.ANNUAL);
		
	}
	

	@After
//	public void tearDown() throws Exception {
//		Time.getInstance().setTime(System.currentTimeMillis());
//		TaskList.DeleteTaskList();
//		
//	}

	@Test
	public void testVariableLoan() throws Exception {
		assertEquals(this.variableLoan.getCreatinngDate(), dayCreation);
		assertEquals(this.variableLoan.getLastRecalculateInterest(), dayCreation);
		List<Task> tasklist= TaskList.getInstance().getTaskList();
		Task task=tasklist.get(0);
		Date commandDate=task.getEffectiveDate();
		SimpleDateFormat  format=new SimpleDateFormat("yyyy/mm/dd");
		assertEquals(commandDate, format.parse("2015/01/10"));
		testVariableLoanExecuteTask();
		
	}
	
	
	public void testVariableLoanExecuteTask() throws Exception {
		
		SimpleDateFormat  format=new SimpleDateFormat("yyyy/mm/dd");
		TaskList tasklist=TaskList.getInstance();
		Time.getInstance().setTime(format.parse("2015/01/10").getTime());
		tasklist.executeTasks();
		assertEquals(this.variableLoan.getLastRecalculateInterest(),format.parse("2015/01/10"));
		
		
	}

}
