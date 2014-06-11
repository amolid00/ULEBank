package es.unileon.ulebank.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

public class ModifyBuyLimitCommandTest {
	private Card testCard;
	private Handler handler;
	private Office office;
	private Client client;
	private Account account;
	private ModifyBuyLimitCommand test;
	private ModifyBuyLimitCommand test2;
	private Bank bank;

	private String accountNumber = "0000000000";

	@Before
	public void setUp() throws CommissionException, InvalidFeeException, MalformedHandlerException, WrongArgsException {
		Handler bankHandler = new BankHandler("1234");
		this.bank = new Bank(bankHandler);
		this.handler = new CardHandler(bankHandler, "01", "123456789");
		this.office = new Office(new GenericHandler("1234"), this.bank);
		this.client = new Person(71557005, 'A');
		this.office.addClient(client);
		this.account = new Account(office, bank, accountNumber, client);
		this.client.add(account);
		testCard = new CreditCard(handler, client, account, 400.0, 1000.0, 400.0, 1000.0, 25, 0, 0);
		account.addCard(testCard);
	}

	@Test
	public void testCommandNotNull()  {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 100.0, "diary");
		assertNotNull(test);
	}

	@Test
	public void testCommandtNull() {
		assertNull(test);
	}

	@Test
	public void testCommandId() {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 200.0, "diary");
		CommandHandler commandId = (CommandHandler) test.getID();
		String date = commandId.getDate();
		assertTrue(0 == test.getID().toString().compareTo(handler.toString() + " " + date));
	}

	@Test
	public void testLimitDiaryModified() throws CardNotFoundException, IncorrectLimitException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 200.0, "diary");
		assertEquals(400.0, this.testCard.getBuyLimitDiary(), 0.0001);
		test.execute();
		assertEquals(200.0, testCard.getBuyLimitDiary(), 0.0001);
	}

	@Test (expected = IncorrectLimitException.class)
	public void testLimitDiaryNotModified() throws  IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 1100.0, "Diary");
		assertEquals(400.0, this.testCard.getBuyLimitDiary(), 0.0001);
		test.execute();
	}

	@Test
	public void testLimitMonthlyModified() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 2000.0, "Monthly");
		assertEquals(1000.0, this.testCard.getBuyLimitMonthly(), 0.0001);
		test.execute();
		assertEquals(2000.0, this.testCard.getBuyLimitMonthly(), 0.0001);
	}

	@Test (expected = IncorrectLimitException.class)
	public void testLimitMonthlyNotModified() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 300.0, "Monthly");
		assertEquals(1000.0, this.testCard.getBuyLimitMonthly(), 0.0001);
		test.execute();
	}

	@Test
	public void testTypeOK() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 300.0, "DIARY");
		test.execute();
		assertNotNull(this.testCard);
		assertEquals(300.0, this.testCard.getBuyLimitDiary(), 0.0001);
	}

	@Test
	public void testTypeNotOK() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 300.0, "");
		test.execute();
		//Any changes in both limits
		assertEquals(400.0, testCard.getBuyLimitDiary(), 0.0001);
		assertEquals(1000.0, testCard.getBuyLimitMonthly(), 0.0001);
		test2 = new ModifyBuyLimitCommand(this.handler, this.testCard, 500.0, "123");
		test2.execute();
		assertEquals(400.0, testCard.getBuyLimitDiary(), 0.0001);
		assertEquals(1000.0, testCard.getBuyLimitMonthly(), 0.0001);
	}

	@Test
	public void undoDiaryTest() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 300.0, "diary");
		test.execute();
		assertEquals(300.0, testCard.getBuyLimitDiary(), 0.0001);
		test.undo();
		assertEquals(400.0, testCard.getBuyLimitDiary(), 0.0001);
	}

	@Test (expected = CommandException.class)
	public void canNotUndoDiaryTest() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 300.0, "diary");
		test.undo();
	}

	@Test
	public void redoDiaryTest() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 300.0, "diary");
		test.execute();
		assertEquals(300.0, testCard.getBuyLimitDiary(), 0.0001);
		test.undo();
		assertEquals(400.0, testCard.getBuyLimitDiary(), 0.0001);
		test.redo();
		assertEquals(300.0, testCard.getBuyLimitDiary(), 0.0001);
	}

	@Test (expected = CommandException.class)
	public void canNotRedoDiaryTest() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 300.0, "diary");
		test.execute();
		test.redo();
	}

	@Test
	public void undoMonthlyTest() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 3000.0, "monthly");
		test.execute();
		assertEquals(3000.0, testCard.getBuyLimitMonthly(), 0.0001);
		test.undo();
		assertEquals(1000.0, testCard.getBuyLimitMonthly(), 0.0001);
	}

	@Test (expected = CommandException.class)
	public void canNotUndoMonthlyTest() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 300.0, "monthly");
		test.undo();
	}

	@Test
	public void redoMonthlyTest() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 3000.0, "monthly");
		test.execute();
		assertEquals(3000.0, testCard.getBuyLimitMonthly(), 0.0001);
		test.undo();
		assertEquals(1000.0, testCard.getBuyLimitMonthly(), 0.0001);
		test.redo();
		assertEquals(3000.0, testCard.getBuyLimitMonthly(), 0.0001);
	}

	@Test (expected = CommandException.class)
	public void canNotRedoMonthlyTest() throws IncorrectLimitException, CommandException, CardNotFoundException {
		test = new ModifyBuyLimitCommand(this.handler, this.testCard, 3000.0, "monthly");
		test.execute();
		test.redo();
	}
}
