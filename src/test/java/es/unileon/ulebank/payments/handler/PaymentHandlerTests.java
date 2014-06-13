package es.unileon.ulebank.payments.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.office.OfficeHandler;
import es.unileon.ulebank.payments.CreditCard;
import es.unileon.ulebank.payments.handler.CardHandler;
import es.unileon.ulebank.payments.handler.PaymentHandler;

public class PaymentHandlerTests {

	CreditCard testCard;
	Handler cardHandler;
	private Office office;
	private Bank bank;
	private String accountNumber = "0000000000";
	private Handler handler;
	private Date paymentDate;

	@Before
	public void setUp() throws CommandException, MalformedHandlerException, WrongArgsException{
		this.bank = new Bank(new BankHandler("1234"));
		this.office = new Office(new OfficeHandler("1234"), this.bank);
		cardHandler = new CardHandler("123401123456789");
		Client client = new Person(71451559, 'N');
		Account account = new Account(office, bank, accountNumber, client);
		testCard = new CreditCard(cardHandler, client, account);
		this.paymentDate = new Date();
		this.handler = new PaymentHandler(this.testCard.getId(), this.paymentDate);
	}

	@Test
	public void correctHandlerTest() {
		assertNotNull(this.handler);
		assertEquals(15, this.handler.toString().length());
	}

	@Test 
	public void compareHandlerTest(){
		assertFalse(this.handler.compareTo(cardHandler)==0);
		assertTrue(this.handler.compareTo(handler)==0);
	}

	@Test (expected = MalformedHandlerException.class)
	public void incorrectHandlerTest() throws MalformedHandlerException{
		Handler card = new CardHandler(new BankHandler("1234"), "01", "1234567890");
		@SuppressWarnings("unused")
		PaymentHandler test = new PaymentHandler(card ,new Date());
	}
}
