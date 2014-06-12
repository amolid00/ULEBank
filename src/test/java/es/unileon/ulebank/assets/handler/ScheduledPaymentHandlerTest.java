package es.unileon.ulebank.assets.handler;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class ScheduledPaymentHandlerTest {
	
	private Bank bank;
	private Office office;
	private Account commercialAccount1, commercialAccount2;
	private GenericHandler authorizedHandler1, authorizedHandler2;
	private Person authorized1, authorized2;
	
	private ScheduledPaymentHandler scheduledPaymentHandler;
	
	@Before
	public void setUp() throws MalformedHandlerException, WrongArgsException {
		
		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);

		this.authorizedHandler1 = new GenericHandler("Miguel");
		this.authorized1 = new Person(71560136,'Y');

		this.authorizedHandler2 = new GenericHandler("Antonio");
		this.authorized2 = new Person(71557005,'A');
		
		this.commercialAccount1 = new Account(this.office, this.bank,
				"0000000000", authorized1);
		this.commercialAccount2 = new Account(this.office, this.bank,
				"0000000001", authorized2);

		
		this.commercialAccount1.addTitular(authorized1);
		this.commercialAccount1.addTitular(authorized2);
		
		ArrayList<Client> clients = new ArrayList<Client>();
		clients.add(authorized1);
		clients.add(authorized2);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 6, 20);
		this.scheduledPaymentHandler = new ScheduledPaymentHandler(new GenericLoanHandler("MG-4-2014-CA-P4YRK-0"), clients, calendar.getTime());
	}
	
	@Test
	public void testScheduledPaymentHandler() {
		String expected = "MG-4-2014-CA-P4YRK-0-Miguel-Antonio-2014-6-20";
		assertEquals(expected, this.scheduledPaymentHandler.toString());
	}
}
