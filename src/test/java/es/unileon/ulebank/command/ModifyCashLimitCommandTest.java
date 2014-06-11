package es.unileon.ulebank.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.security.auth.login.AccountNotFoundException;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.CardNotFoundException;
import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.exceptions.IncorrectLimitException;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.CommandHandler;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CreditCard;

public class ModifyCashLimitCommandTest {
	private Card testCard;
	private Handler handler;
	private Office office;
	private Client client;
	private Account account;
	private ModifyCashLimitCommand test;
	private ModifyCashLimitCommand test2;
	private Bank bank;

	private String accountNumber = "0000000000";

	@Before
	public void setUp() throws CommissionException, InvalidFeeException, MalformedHandlerException, WrongArgsException {
		Handler bankHandler = new BankHandler("1234");
		this.bank = new Bank(bankHandler);
		this.handler = new CardHandler(bankHandler, "01", "987654321");
		this.office = new Office(new GenericHandler("1234"), this.bank);
		client = new Person(71557005, 'A');
		this.office.addClient(client);
		account = new Account(office, bank, accountNumber, client);
		this.client.add(account);
		testCard = new CreditCard(handler, client, account, 400.0, 1000.0, 400.0, 1000.0, 25, 0, 0);
		account.addCard(testCard);
	}

	@Test
	public void testCommandNotNull() throws AccountNotFoundException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 100.0, "diary");
		assertNotNull(test);
	}

	@Test
	public void testCommandNull() throws AccountNotFoundException {
		assertNull(test);
	}

	@Test
	public void testCommandId() throws AccountNotFoundException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 200.0, "diary");
		CommandHandler commandId = (CommandHandler) test.getID();
		String date = commandId.getDate();
		assertTrue(0 == test.getID().toString().compareTo(handler.toString() + " " + date));
	}

	@Test
	public void testLimitDiaryModified() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 200.0, "Diary");
		assertEquals(400.0, this.testCard.getCashLimitDiary(), 0.0001);
		test.execute();
		assertEquals(200.0, testCard.getCashLimitDiary(), 0.0001);
	}

	@Test (expected = IncorrectLimitException.class)
	public void testLimitDiaryNotModified() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 1100.0, "Diary");
		assertEquals(400.0, this.testCard.getCashLimitDiary(), 0.0001);
		test.execute();
	}

	@Test
	public void testLimitMonthlyModified() throws AccountNotFoundException, CardNotFoundException, IncorrectLimitException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 2000.0, "Monthly");
		assertEquals(1000.0, this.testCard.getCashLimitMonthly(), 0.0001);
		test.execute();
		assertEquals(2000.0, this.testCard.getCashLimitMonthly(), 0.0001);
	}

	@Test (expected = IncorrectLimitException.class)
	public void testLimitMonthlyNotModified() throws AccountNotFoundException, CardNotFoundException, IncorrectLimitException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 300.0, "Monthly");
		assertEquals(1000.0, this.testCard.getCashLimitMonthly(), 0.0001);
		test.execute();
	}

	@Test
	public void testTypeOK() throws AccountNotFoundException, CardNotFoundException, IncorrectLimitException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 300.0, "DIARY");
		test.execute();
		assertTrue(this.testCard != null);
		assertEquals(300.0, this.testCard.getCashLimitDiary(), 0.0001);
	}

	@Test
	public void testTypeNotOK() throws AccountNotFoundException, CardNotFoundException, IncorrectLimitException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 300.0, "");
		test.execute();
		//Any changes in both limits
		assertEquals(400, testCard.getCashLimitDiary(), 0.0001);
		assertEquals(1000, testCard.getCashLimitMonthly(), 0.0001);
		test2 = new ModifyCashLimitCommand(this.handler, this.testCard, 500.0, "123");
		test2.execute();
		assertEquals(400.0, testCard.getCashLimitDiary(), 0.0001);
		assertEquals(1000.0, testCard.getCashLimitMonthly(), 0.0001);
	}

	@Test
	public void undoDiaryTest() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException, CommandException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 300.0, "diary");
		test.execute();
		assertEquals(300.0, testCard.getCashLimitDiary(), 0.0001);
		test.undo();
		assertEquals(400.0, testCard.getCashLimitDiary(), 0.0001);
	}

	@Test (expected = CommandException.class)
	public void canNotUndoDiaryTest() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException, CommandException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 300.0, "diary");
		test.undo();
	}

	@Test
	public void redoDiaryTest() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException, CommandException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 300.0, "diary");
		test.execute();
		assertEquals(300.0, testCard.getCashLimitDiary(), 0.0001);
		test.undo();
		assertEquals(400.0, testCard.getCashLimitDiary(), 0.0001);
		test.redo();
		assertEquals(300.0, testCard.getCashLimitDiary(), 0.0001);
	}

	@Test (expected = CommandException.class)
	public void canNotRedoDiaryTest() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException, CommandException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 300.0, "diary");
		test.execute();
		test.redo();
	}

	@Test
	public void undoMonthlyTest() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException, CommandException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 3000.0, "monthly");
		test.execute();
		assertEquals(3000.0, testCard.getCashLimitMonthly(), 0.0001);
		test.undo();
		assertEquals(1000.0, testCard.getCashLimitMonthly(), 0.0001);
	}

	@Test (expected = CommandException.class)
	public void canNotUndoMonthlyTest() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException, CommandException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 300.0, "monthly");
		test.undo();
	}

	@Test
	public void redoMonthlyTest() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException, CommandException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 3000.0, "monthly");
		test.execute();
		assertEquals(3000.0, testCard.getCashLimitMonthly(), 0.0001);
		test.undo();
		assertEquals(1000.0, testCard.getCashLimitMonthly(), 0.0001);
		test.redo();
		assertEquals(3000.0, testCard.getCashLimitMonthly(), 0.0001);
	}

	@Test (expected = CommandException.class)
	public void canNotRedoMonthlyTest() throws CardNotFoundException, IncorrectLimitException, AccountNotFoundException, CommandException {
		test = new ModifyCashLimitCommand(this.handler, this.testCard, 3000.0, "monthly");
		test.execute();
		test.redo();
	}
}
