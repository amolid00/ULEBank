package es.unileon.ulebank.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.client.PersonHandler;
import es.unileon.ulebank.exceptions.ClientNotFoundException;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.CommandHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.handler.OfficeHandler;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CreditCard;
import es.unileon.ulebank.payments.DebitCard;

public class CancelCardCommandTest {
	private Handler handler1;
	private Handler handler2;
	private Office office;
	private Handler dni;
	private Handler accountHandler;
	private Client client;
	private Account account;
	private Card card1;
	private Card card2;
	private CancelCardCommand test;
	private Bank bank;

	private String accountNumber = "0000000000";

	@Before
	public void setUp() throws NumberFormatException, CommissionException, IOException, InvalidFeeException, MalformedHandlerException, WrongArgsException {
		Handler bankHandler = new BankHandler("1234");
		handler1 = new CardHandler(bankHandler, "01", "123456789");
		handler2 = new CardHandler(bankHandler, "01", "123456788");
		this.bank = new Bank(bankHandler);
		this.office = new Office(new OfficeHandler("1234"), this.bank);
		this.dni = new PersonHandler(71557005, 'A');
		this.client = new Person(71557005,'A');
		this.office.addClient(client);
		this.account = new Account(office, bank, accountNumber, client);
		this.accountHandler = account.getID();
		this.client.add(account);
		this.card1 = new DebitCard(handler1, client, account, 400.0, 1000.0, 400.0, 1000.0, 25, 0, 0);
		this.card2 = new CreditCard(handler2, client, account, 400.0, 1000.0, 400.0, 1000.0, 25, 0, 0);
		account.addCard(card1);
		account.addCard(card2);
	}

	@Test 
	public void testCommandNotNull() throws ClientNotFoundException {
		test = new CancelCardCommand(handler1, office, dni, accountHandler);
		assertNotNull(test);
	}

	@Test 
	public void testCommandNull() throws ClientNotFoundException {
		assertNull(test);
	}

	@Test
	public void testCommandId() throws ClientNotFoundException {
		test = new CancelCardCommand(handler1, office, dni, accountHandler);
		CommandHandler handler = (CommandHandler) test.getID();
		assertTrue(handler.getId().compareTo(card1.getId()) == 0);
	}

	@Test
	public void testCancelDebitCard() throws ClientNotFoundException {
		test = new CancelCardCommand(handler1, office, dni, accountHandler);
		assertEquals(2, account.getCardAmount());
		test.execute();
		assertEquals(1, account.getCardAmount());
	}

	@Test (expected = UnsupportedOperationException.class)
	public void testUndoCancelDebitCard() throws ClientNotFoundException {
		test = new CancelCardCommand(handler1, office, dni, accountHandler);
		test.execute();
		test.undo();
	}

	@Test (expected = UnsupportedOperationException.class)
	public void testRedoCancelDebitCard() throws ClientNotFoundException {
		test = new CancelCardCommand(handler1, office, dni, accountHandler);
		test.execute();
		test.redo();
	}

	@Test
	public void testCancelCreditCard() throws ClientNotFoundException {
		test = new CancelCardCommand(handler2, office, dni, accountHandler);
		assertEquals(2, account.getCardAmount());
		test.execute();
		assertEquals(1, account.getCardAmount());
	}

	@Test (expected = UnsupportedOperationException.class)
	public void testUndoCancelCreditCard() throws ClientNotFoundException {
		test = new CancelCardCommand(handler2, office, dni, accountHandler);
		test.execute();
		test.undo();
	}

	@Test (expected = UnsupportedOperationException.class)
	public void testRedoCancelCreditCard() throws ClientNotFoundException {
		test = new CancelCardCommand(handler2, office, dni, accountHandler);
		test.execute();
		test.redo();
	}
}
