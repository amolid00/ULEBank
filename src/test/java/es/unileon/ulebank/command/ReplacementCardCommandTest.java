package es.unileon.ulebank.command;

import java.io.IOException;

import javax.security.auth.login.AccountNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.client.PersonHandler;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.office.OfficeHandler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CreditCard;
import es.unileon.ulebank.payments.DebitCard;
import es.unileon.ulebank.payments.exceptions.PaymentException;
import es.unileon.ulebank.payments.handler.CardHandler;
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

    private final String accountNumber = "0000000000";

    @Before
    public void setUp() throws NumberFormatException, CommissionException,
    IOException, InvalidFeeException, MalformedHandlerException,
    WrongArgsException, PaymentException {
        final CardProperties properties = new CardProperties();
        properties.setCvvSize(3);
        properties.setPinSize(4);
        properties.setMinimumLimit(200.0);
        properties.setExpirationYear(3);
        final Handler bankHandler = new BankHandler("1234");
        this.bank = new Bank(bankHandler);
        this.handler1 = new CardHandler(bankHandler, "01", "123456789");
        this.handler2 = new CardHandler(bankHandler, "01", "987654321");
        this.office = new Office(new OfficeHandler("1234"), this.bank);
        this.dni = new PersonHandler(71557005, 'A');
        this.client = new Person(71557005, 'A');
        this.office.addClient(this.client);
        this.account = new Account(this.office, this.bank, this.accountNumber,
                this.client);
        this.accountHandler = this.account.getID();
        this.client.add(this.account);
        this.card1 = new DebitCard(this.handler1, this.client, this.account);
        this.card2 = new CreditCard(this.handler2, this.client, this.account);
        this.account.addCard(this.card1);
        this.account.addCard(this.card2);
        this.card1.setCvv("213");
        this.card2.setCvv("123");
        this.card1.setPin("1234");
        this.card2.setPin("0000");
    }

    @Test
    public void testCommandNotNull() throws CommandException {
        this.test = new ReplacementCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertNotNull(this.test);
    }

    @Test
    public void testCommandNull() throws AccountNotFoundException {
        Assert.assertNull(this.test);
    }

    @Test
    public void testCommandId() throws Exception {
        this.test = new ReplacementCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        final CommandHandler handler = (CommandHandler) this.test.getID();
        Assert.assertTrue(this.handler1.compareTo(handler.getId()) == 0);
    }

    @Test
    public void testReplacementCreditCard() throws Exception {
        this.test = new ReplacementCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("123", this.card2.getCvv());
        Assert.assertEquals("0000", this.card2.getPin());
        this.test.execute();
        Assert.assertNotEquals(this.account.searchCard(handler2).getPin(), "0000");
        Assert.assertNotEquals(this.account.searchCard(handler2).getCvv(), "123");
    }

    @Test
    public void testUndoReplacementCreditCardOk() throws Exception {
        this.test = new ReplacementCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("123", this.card2.getCvv());
        Assert.assertEquals("0000", this.card2.getPin());
        this.test.execute();
        Assert.assertNotEquals(this.account.searchCard(handler2).getCvv(), "123");
        Assert.assertNotEquals(this.account.searchCard(handler2).getPin(), "0000");
        this.test.undo();
        Assert.assertEquals("123", this.card2.getCvv());
        Assert.assertEquals("0000", this.card2.getPin());
    }

    @Test(expected = CommandException.class)
    public void testUndoReplacementCreditCardFail() throws CommandException, IOException {
        this.test = new ReplacementCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("123", this.card2.getCvv());
        Assert.assertEquals("0000", this.card2.getPin());
        this.test.undo();
    }

    @Test
    public void testRedoReplacementCreditCardOk() throws Exception {
        this.test = new ReplacementCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("123", this.card2.getCvv());
        Assert.assertEquals("0000", this.card2.getPin());
        this.test.execute();
        Assert.assertNotEquals(this.account.searchCard(handler2).getPin(), "0000");
        Assert.assertNotEquals(this.account.searchCard(handler2).getCvv(), "123");
        this.test.undo();
        Assert.assertEquals("123", this.card2.getCvv());
        Assert.assertEquals("0000", this.card2.getPin());
        this.test.redo();
        Assert.assertNotEquals(this.account.searchCard(handler2).getPin(), "0000");
        Assert.assertNotEquals(this.account.searchCard(handler2).getCvv(), "123");
    }

    @Test(expected = CommandException.class)
    public void testRedoReplacementCreditCardFail() throws Exception {
        this.test = new ReplacementCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("123", this.card2.getCvv());
        Assert.assertEquals("0000", this.card2.getPin());
        this.test.redo();
    }

    @Test
    public void testReplacementDebitCard() throws Exception {
        this.test = new ReplacementCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("213", this.card1.getCvv());
        Assert.assertEquals("1234", this.card1.getPin());
        this.test.execute();
        Assert.assertTrue(!this.card1.getCvv().equals("213"));
        Assert.assertTrue(!this.card1.getPin().equals("1234"));
    }

    @Test
    public void testUndoReplacementDebitCardOk() throws Exception {
        this.test = new ReplacementCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("213", this.card1.getCvv());
        Assert.assertEquals("1234", this.card1.getPin());
        this.test.execute();
        Assert.assertNotEquals(this.account.searchCard(handler1).getPin(), "1234");
        Assert.assertNotEquals(this.account.searchCard(handler1).getCvv(), "213");
        this.test.undo();
        Assert.assertEquals("213", this.card1.getCvv());
        Assert.assertEquals("1234", this.card1.getPin());
    }

    @Test(expected = CommandException.class)
    public void testUndoReplacementDebitCardFail() throws Exception {
        this.test = new ReplacementCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("213", this.card1.getCvv());
        Assert.assertEquals("1234", this.card1.getPin());
        this.test.undo();
    }

    @Test
    public void testRedoReplacementDebitCardOk() throws Exception {
        this.test = new ReplacementCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("213", this.card1.getCvv());
        Assert.assertEquals("1234", this.card1.getPin());
        this.test.execute();
        Assert.assertTrue(!this.card1.getCvv().equals("213"));
        Assert.assertTrue(!this.card1.getPin().equals("1234"));
        this.test.undo();
        Assert.assertEquals("213", this.card1.getCvv());
        Assert.assertEquals("1234", this.card1.getPin());
        this.test.redo();
        Assert.assertTrue(!this.card1.getCvv().equals("213"));
        Assert.assertTrue(!this.card1.getPin().equals("1234"));
    }

    @Test(expected = CommandException.class)
    public void testRedoReplacementDebitCardFail() throws Exception {
        this.test = new ReplacementCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("213", this.card1.getCvv());
        Assert.assertEquals("1234", this.card1.getPin());
        this.test.redo();
    }
}
