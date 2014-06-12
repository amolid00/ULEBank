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
import es.unileon.ulebank.client.ClientNotFoundException;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.client.PersonHandler;
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
import es.unileon.ulebank.payments.DebitCard;
import es.unileon.ulebank.payments.handler.CardHandler;
import es.unileon.ulebank.utils.CardProperties;

public class RenovateCardCommandTest {
    private Handler handler1;
    private Handler handler2;
    private Office office;
    private Handler dni;
    private Client client;
    private Handler accountHandler;
    private Account account;
    private Card card1;
    private Card card2;
    private RenovateCardCommand test;
    private Bank bank;

    private final String accountNumber = "0000000000";

    @Before
    public void setUp() throws NumberFormatException, CommissionException,
            IOException, InvalidFeeException, MalformedHandlerException,
            WrongArgsException, ClientNotFoundException {
        final CardProperties properties = new CardProperties();
        properties.setCvvSize(3);
        final Handler bankHandler = new BankHandler("1234");
        this.bank = new Bank(bankHandler);
        this.handler1 = new CardHandler(bankHandler, "01", "987654321");
        this.handler2 = new CardHandler(bankHandler, "01", "123456789");
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.dni = new PersonHandler(71557005, 'A');
        this.client = new Person(71557005, 'A');
        this.office.addClient(this.client);
        this.account = new Account(this.office, this.bank, this.accountNumber,
                this.client);
        this.accountHandler = this.account.getID();
        this.client.add(this.account);
        this.card1 = new DebitCard(this.handler1, this.client, this.account,
                400.0, 1000.0, 400.0, 1000.0, 25, 0, 0);
        this.card2 = new CreditCard(this.handler2, this.client, this.account,
                400.0, 1000.0, 400.0, 1000.0, 25, 0, 0);
        this.account.addCard(this.card1);
        this.account.addCard(this.card2);
        try {
            this.card1.setCvv("213");
            this.card2.setCvv("123");
        } catch (final IOException e) {
            e.printStackTrace();
        }
        this.card1.setExpirationDate("04/14");
        this.card2.setExpirationDate("04/14");
    }

    @Test
    public void testCommandNotNull() throws CommandException {
        this.test = new RenovateCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertNotNull(this.test);
    }

    @Test
    public void testCommandNull() throws AccountNotFoundException {
        Assert.assertNull(this.test);
    }

    @Test
    public void testCommandId() throws Exception {
        this.test = new RenovateCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        final CommandHandler handler = (CommandHandler) this.test.getID();
        Assert.assertTrue(this.handler1.compareTo(handler.getId()) == 0);
    }

    @Test
    public void testRenovateCreditCard() throws Exception {
        this.test = new RenovateCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("04/14", this.card2.getExpirationDate());
        Assert.assertEquals("123", this.card2.getCvv());
        this.test.execute();
        Assert.assertTrue(!this.card2.getExpirationDate().equals("04/14"));
        Assert.assertTrue(!this.card2.getCvv().equals("123"));
    }

    @Test
    public void testUndoRenovateCreditCardOk() throws Exception {
        this.test = new RenovateCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("04/14", this.card2.getExpirationDate());
        Assert.assertEquals("123", this.card2.getCvv());
        this.test.execute();
        Assert.assertTrue(!this.card2.getExpirationDate().equals("04/14"));
        Assert.assertTrue(!this.card2.getCvv().equals("123"));
        this.test.undo();
        Assert.assertEquals("04/14", this.card2.getExpirationDate());
        Assert.assertEquals("123", this.card2.getCvv());
    }

    @Test(expected = CommandException.class)
    public void testUndoRenovateCreditCardFail() throws Exception {
        this.test = new RenovateCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        this.test.undo();
    }

    @Test
    public void testRedoRenovateCreditCardOk() throws Exception {
        this.test = new RenovateCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("04/14", this.card2.getExpirationDate());
        Assert.assertEquals("123", this.card2.getCvv());
        this.test.execute();
        Assert.assertTrue(!this.card2.getExpirationDate().equals("04/14"));
        Assert.assertTrue(!this.card2.getCvv().equals("123"));
        this.test.undo();
        Assert.assertEquals("04/14", this.card2.getExpirationDate());
        Assert.assertEquals("123", this.card2.getCvv());
        this.test.redo();
        Assert.assertTrue(!this.card2.getExpirationDate().equals("04/14"));
        Assert.assertTrue(!this.card2.getCvv().equals("123"));
    }

    @Test(expected = CommandException.class)
    public void testRedoRenovateCreditCardFail() throws Exception {
        this.test = new RenovateCardCommand(this.handler2, this.office,
                this.dni, this.accountHandler);
        this.test.redo();
    }

    @Test
    public void testRenovateDebitCard() throws Exception {
        this.test = new RenovateCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("04/14", this.card1.getExpirationDate());
        Assert.assertEquals("213", this.card1.getCvv());
        this.test.execute();
        Assert.assertTrue(!this.card1.getExpirationDate().equals("04/14"));
        Assert.assertTrue(!this.card1.getCvv().equals("213"));
    }

    @Test
    public void testUndoRenovateDebitCardOk() throws Exception {
        this.test = new RenovateCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("04/14", this.card1.getExpirationDate());
        Assert.assertEquals("213", this.card1.getCvv());
        this.test.execute();
        Assert.assertTrue(!this.card1.getExpirationDate().equals("04/14"));
        Assert.assertTrue(!this.card1.getCvv().equals("213"));
        this.test.undo();
        Assert.assertEquals("04/14", this.card1.getExpirationDate());
        Assert.assertEquals("213", this.card1.getCvv());
    }

    @Test(expected = CommandException.class)
    public void testUndoRenovateDebitCardFail() throws Exception {
        this.test = new RenovateCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("04/14", this.card1.getExpirationDate());
        Assert.assertEquals("213", this.card1.getCvv());
        this.test.undo();
    }

    @Test
    public void testRedoRenovateDebitCardOk() throws Exception {
        this.test = new RenovateCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        Assert.assertEquals("04/14", this.card1.getExpirationDate());
        Assert.assertEquals("213", this.card1.getCvv());
        this.test.execute();
        Assert.assertTrue(!this.card1.getExpirationDate().equals("04/14"));
        Assert.assertTrue(!this.card1.getCvv().equals("213"));
        this.test.undo();
        Assert.assertEquals("04/14", this.card1.getExpirationDate());
        Assert.assertEquals("213", this.card1.getCvv());
        this.test.redo();
        Assert.assertTrue(!this.card1.getExpirationDate().equals("04/14"));
        Assert.assertTrue(!this.card1.getCvv().equals("213"));
    }

    @Test(expected = CommandException.class)
    public void testRedoRenovateDebitCardFail() throws Exception {
        this.test = new RenovateCardCommand(this.handler1, this.office,
                this.dni, this.accountHandler);
        this.test.redo();
    }
}
