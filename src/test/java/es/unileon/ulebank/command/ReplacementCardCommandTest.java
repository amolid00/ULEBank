package es.unileon.ulebank.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.security.auth.login.AccountNotFoundException;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.client.PersonHandler;
import es.unileon.ulebank.exceptions.ClientNotFoundException;
import es.unileon.ulebank.exceptions.CommandException;
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
import es.unileon.ulebank.utils.CardProperties;

public class ReplacementCardCommandTest {

	private Handler handler1;
	private Handler handler2;
	private Office office;
	private Handler dni;
	private Client client;
	private Handler accountHandler;
	private Account account;
	private Card card1;
	private Card card2;
	private ReplacementCardCommand test;
	private Bank bank;

	private String accountNumber = "0000000000";

	@Before
	public void setUp() throws NumberFormatException, CommissionException, IOException, InvalidFeeException, MalformedHandlerException, WrongArgsException {
		CardProperties properties = new CardProperties();
		properties.setCvvSize(3);
		properties.setPinSize(4);
		properties.setMinimumLimit(200.0);
		properties.setExpirationYear(3);
		Handler bankHandler = new BankHandler("1234");
		this.bank = new Bank(bankHandler);
		this.handler1 = new CardHandler(bankHandler, "01", "123456789");
		this.handler2 = new CardHandler(bankHandler, "01", "987654321");
		this.office =  new Office(new OfficeHandler("1234"), this.bank);
		this.dni = new PersonHandler(71557005, 'A');
		this.client = new Person(71557005, 'A');
		this.office.addClient(client);
		this.account = new Account(office, bank, accountNumber, client);
		this.accountHandler = account.getID();
		this.client.add(account);
		this.card1 = new DebitCard(handler1, client, account, 400.0, 1000.0, 400.0, 1000.0, 25, 0, 0);
		this.card2 = new CreditCard(handler2, client, account, 400.0, 1000.0, 400.0, 1000.0, 25, 0, 0);
		account.addCard(card1);
		account.addCard(card2);
		try {
			card1.setCvv("213");
			card2.setCvv("123");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			card1.setPin("1234");
			card2.setPin("0000");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test 
	public void testCommandNotNull() throws ClientNotFoundException {
		test = new ReplacementCardCommand(handler1, office, dni, accountHandler);
		assertNotNull(test);
	}

	@Test
	public void testCommandNull() throws AccountNotFoundException {
		assertNull(test);
	}

	@Test
	public void testCommandId() throws Exception {
		test = new ReplacementCardCommand(handler1, office, dni, accountHandler);
		CommandHandler handler = (CommandHandler) test.getID();
		assertTrue(handler1.compareTo(handler.getId()) == 0);
	}

	@Test
	public void testReplacementCreditCard() throws Exception {
		test = new ReplacementCardCommand(handler2, office, dni, accountHandler);
		assertEquals("123", card2.getCvv());
		assertEquals("0000", card2.getPin());
		test.execute();
		assertTrue(!card2.getCvv().equals("123"));
		assertTrue(!card2.getPin().equals("0000"));
	}

	@Test
	public void testUndoReplacementCreditCardOk() throws Exception {
		test = new ReplacementCardCommand(handler2, office, dni, accountHandler);
		assertEquals("123", card2.getCvv());
		assertEquals("0000", card2.getPin());
		test.execute();
		assertTrue(!card2.getCvv().equals("123"));
		assertTrue(!card2.getPin().equals("0000"));
		test.undo();
		assertEquals("123", card2.getCvv());
		assertEquals("0000", card2.getPin());
	}

	@Test (expected = CommandException.class)
	public void testUndoReplacementCreditCardFail() throws CommandException, ClientNotFoundException, IOException {
		test = new ReplacementCardCommand(handler2, office, dni, accountHandler);
		assertEquals("123", card2.getCvv());
		assertEquals("0000", card2.getPin());
		test.undo();
	}

	@Test
	public void testRedoReplacementCreditCardOk() throws Exception {
		test = new ReplacementCardCommand(handler2, office, dni, accountHandler);
		assertEquals("123", card2.getCvv());
		assertEquals("0000", card2.getPin());
		test.execute();
		assertTrue(!card2.getCvv().equals("123"));
		assertTrue(!card2.getPin().equals("0000"));
		test.undo();
		assertEquals("123", card2.getCvv());
		assertEquals("0000", card2.getPin());
		test.redo();
		assertTrue(!card2.getCvv().equals("123"));
		assertTrue(!card2.getPin().equals("0000"));
	}

	@Test (expected = CommandException.class)
	public void testRedoReplacementCreditCardFail() throws Exception {
		test = new ReplacementCardCommand(handler2, office, dni, accountHandler);
		assertEquals("123", card2.getCvv());
		assertEquals("0000", card2.getPin());
		test.redo();
	}

	@Test
	public void testReplacementDebitCard() throws Exception {
		test = new ReplacementCardCommand(handler1, office, dni, accountHandler);
		assertEquals("213", card1.getCvv());
		assertEquals("1234", card1.getPin());
		test.execute();
		assertTrue(!card1.getCvv().equals("213"));
		assertTrue(!card1.getPin().equals("1234"));
	}

	@Test
	public void testUndoReplacementDebitCardOk() throws Exception {
		test = new ReplacementCardCommand(handler1, office, dni, accountHandler);
		assertEquals("213", card1.getCvv());
		assertEquals("1234", card1.getPin());
		test.execute();
		assertTrue(!card1.getCvv().equals("213"));
		assertTrue(!card1.getPin().equals("1234"));
		test.undo();
		assertEquals("213", card1.getCvv());
		assertEquals("1234", card1.getPin());
	}

	@Test (expected = CommandException.class)
	public void testUndoReplacementDebitCardFail() throws Exception {
		test = new ReplacementCardCommand(handler1, office, dni, accountHandler);
		assertEquals("213", card1.getCvv());
		assertEquals("1234", card1.getPin());
		test.undo();
	}

	@Test
	public void testRedoReplacementDebitCardOk() throws Exception {
		test = new ReplacementCardCommand(handler1, office, dni, accountHandler);
		assertEquals("213", card1.getCvv());
		assertEquals("1234", card1.getPin());
		test.execute();
		assertTrue(!card1.getCvv().equals("213"));
		assertTrue(!card1.getPin().equals("1234"));
		test.undo();
		assertEquals("213", card1.getCvv());
		assertEquals("1234", card1.getPin());
		test.redo();
		assertTrue(!card1.getCvv().equals("213"));
		assertTrue(!card1.getPin().equals("1234"));
	}

	@Test (expected = CommandException.class)
	public void testRedoReplacementDebitCardFail() throws Exception {
		test = new ReplacementCardCommand(handler1, office, dni, accountHandler);
		assertEquals("213", card1.getCvv());
		assertEquals("1234", card1.getPin());
		test.redo();
	}
}
