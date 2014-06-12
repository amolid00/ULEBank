package es.unileon.ulebank.command;

import javax.security.auth.login.AccountNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.command.exceptions.CommandException;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CreditCard;
import es.unileon.ulebank.payments.exceptions.IncorrectLimitException;
import es.unileon.ulebank.payments.handler.CardHandler;

public class ModifyCashLimitCommandTest {
    private Card testCard;
    private Handler handler;
    private Office office;
    private Client client;
    private Account account;
    private ModifyCashLimitCommand test;
    private ModifyCashLimitCommand test2;
    private Bank bank;

    private final String accountNumber = "0000000000";

    @Before
    public void setUp() throws CommissionException, InvalidFeeException,
    MalformedHandlerException, WrongArgsException {
        final Handler bankHandler = new BankHandler("1234");
        this.bank = new Bank(bankHandler);
        this.handler = new CardHandler(bankHandler, "01", "987654321");
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.client = new Person(71557005, 'A');
        this.office.addClient(this.client);
        this.account = new Account(this.office, this.bank, this.accountNumber,
                this.client);
        this.client.add(this.account);
        this.testCard = new CreditCard(this.handler, this.client, this.account,
                400.0, 1000.0, 400.0, 1000.0, 25, 0, 0);
        this.account.addCard(this.testCard);
    }

    @Test
    public void testCommandNotNull() throws AccountNotFoundException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                100.0, "diary");
        Assert.assertNotNull(this.test);
    }

    @Test
    public void testCommandNull() throws AccountNotFoundException {
        Assert.assertNull(this.test);
    }

    @Test
    public void testCommandId() throws AccountNotFoundException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                200.0, "diary");
        final CommandHandler commandId = (CommandHandler) this.test.getID();
        final String date = commandId.getDate();
        Assert.assertTrue(0 == this.test.getID().toString()
                .compareTo(this.handler.toString() + " " + date));
    }

    @Test
    public void testLimitDiaryModified() throws
    IncorrectLimitException, AccountNotFoundException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                200.0, "Diary");
        Assert.assertEquals(400.0, this.testCard.getCashLimitDiary(), 0.0001);
        this.test.execute();
        Assert.assertEquals(200.0, this.testCard.getCashLimitDiary(), 0.0001);
    }

    @Test(expected = IncorrectLimitException.class)
    public void testLimitDiaryNotModified() throws
    IncorrectLimitException, AccountNotFoundException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                1100.0, "Diary");
        Assert.assertEquals(400.0, this.testCard.getCashLimitDiary(), 0.0001);
        this.test.execute();
    }

    @Test
    public void testLimitMonthlyModified() throws AccountNotFoundException,
    IncorrectLimitException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                2000.0, "Monthly");
        Assert.assertEquals(1000.0, this.testCard.getCashLimitMonthly(), 0.0001);
        this.test.execute();
        Assert.assertEquals(2000.0, this.testCard.getCashLimitMonthly(), 0.0001);
    }

    @Test(expected = IncorrectLimitException.class)
    public void testLimitMonthlyNotModified() throws AccountNotFoundException,
    IncorrectLimitException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                300.0, "Monthly");
        Assert.assertEquals(1000.0, this.testCard.getCashLimitMonthly(), 0.0001);
        this.test.execute();
    }

    @Test
    public void testTypeOK() throws AccountNotFoundException,
    IncorrectLimitException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                300.0, "DIARY");
        this.test.execute();
        Assert.assertTrue(this.testCard != null);
        Assert.assertEquals(300.0, this.testCard.getCashLimitDiary(), 0.0001);
    }

    @Test
    public void testTypeNotOK() throws AccountNotFoundException,
    IncorrectLimitException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                300.0, "");
        this.test.execute();
        // Any changes in both limits
        Assert.assertEquals(400, this.testCard.getCashLimitDiary(), 0.0001);
        Assert.assertEquals(1000, this.testCard.getCashLimitMonthly(), 0.0001);
        this.test2 = new ModifyCashLimitCommand(this.handler, this.testCard,
                500.0, "123");
        this.test2.execute();
        Assert.assertEquals(400.0, this.testCard.getCashLimitDiary(), 0.0001);
        Assert.assertEquals(1000.0, this.testCard.getCashLimitMonthly(), 0.0001);
    }

    @Test
    public void undoDiaryTest() throws
    IncorrectLimitException, AccountNotFoundException, CommandException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                300.0, "diary");
        this.test.execute();
        Assert.assertEquals(300.0, this.testCard.getCashLimitDiary(), 0.0001);
        this.test.undo();
        Assert.assertEquals(400.0, this.testCard.getCashLimitDiary(), 0.0001);
    }

    @Test(expected = CommandException.class)
    public void canNotUndoDiaryTest() throws
    IncorrectLimitException, AccountNotFoundException, CommandException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                300.0, "diary");
        this.test.undo();
    }

    @Test
    public void redoDiaryTest() throws
    IncorrectLimitException, AccountNotFoundException, CommandException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                300.0, "diary");
        this.test.execute();
        Assert.assertEquals(300.0, this.testCard.getCashLimitDiary(), 0.0001);
        this.test.undo();
        Assert.assertEquals(400.0, this.testCard.getCashLimitDiary(), 0.0001);
        this.test.redo();
        Assert.assertEquals(300.0, this.testCard.getCashLimitDiary(), 0.0001);
    }

    @Test(expected = CommandException.class)
    public void canNotRedoDiaryTest() throws
    IncorrectLimitException, AccountNotFoundException, CommandException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                300.0, "diary");
        this.test.execute();
        this.test.redo();
    }

    @Test
    public void undoMonthlyTest() throws
    IncorrectLimitException, AccountNotFoundException, CommandException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                3000.0, "monthly");
        this.test.execute();
        Assert.assertEquals(3000.0, this.testCard.getCashLimitMonthly(), 0.0001);
        this.test.undo();
        Assert.assertEquals(1000.0, this.testCard.getCashLimitMonthly(), 0.0001);
    }

    @Test(expected = CommandException.class)
    public void canNotUndoMonthlyTest() throws
    IncorrectLimitException, AccountNotFoundException, CommandException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                300.0, "monthly");
        this.test.undo();
    }

    @Test
    public void redoMonthlyTest() throws 
    IncorrectLimitException, AccountNotFoundException, CommandException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                3000.0, "monthly");
        this.test.execute();
        Assert.assertEquals(3000.0, this.testCard.getCashLimitMonthly(), 0.0001);
        this.test.undo();
        Assert.assertEquals(1000.0, this.testCard.getCashLimitMonthly(), 0.0001);
        this.test.redo();
        Assert.assertEquals(3000.0, this.testCard.getCashLimitMonthly(), 0.0001);
    }

    @Test(expected = CommandException.class)
    public void canNotRedoMonthlyTest() throws 
    IncorrectLimitException, AccountNotFoundException, CommandException {
        this.test = new ModifyCashLimitCommand(this.handler, this.testCard,
                3000.0, "monthly");
        this.test.execute();
        this.test.redo();
    }
}
