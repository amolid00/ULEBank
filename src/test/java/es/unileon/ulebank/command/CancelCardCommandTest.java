package es.unileon.ulebank.command;

import java.io.IOException;

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
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.office.OfficeHandler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CreditCard;
import es.unileon.ulebank.payments.DebitCard;
import es.unileon.ulebank.payments.handler.CardHandler;

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

    private final String accountNumber = "0000000000";

    @Before
    public void setUp() throws NumberFormatException, CommissionException,
            IOException, InvalidFeeException, MalformedHandlerException,
            WrongArgsException, ClientNotFoundException {
        final Handler bankHandler = new BankHandler("1234");
        this.handler1 = new CardHandler(bankHandler, "01", "123456789");
        this.handler2 = new CardHandler(bankHandler, "01", "123456788");
        this.bank = new Bank(bankHandler);
        this.office = new Office(new OfficeHandler("1234"), this.bank);
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
    }

    @Test
    public void testCommandNotNull() throws CommandException {
        this.test = new CancelCardCommand(this.handler1, this.office, this.dni,
                this.accountHandler);
        Assert.assertNotNull(this.test);
    }

    @Test
    public void testCommandNull() throws ClientNotFoundException {
        Assert.assertNull(this.test);
    }

    @Test
    public void testCommandId() throws CommandException {
        this.test = new CancelCardCommand(this.handler1, this.office, this.dni,
                this.accountHandler);
        final CommandHandler handler = (CommandHandler) this.test.getID();
        Assert.assertTrue(handler.getId().compareTo(this.card1.getId()) == 0);
    }

    @Test
    public void testCancelDebitCard() throws CommandException {
        this.test = new CancelCardCommand(this.handler1, this.office, this.dni,
                this.accountHandler);
        Assert.assertEquals(2, this.account.getCardAmount());
        this.test.execute();
        Assert.assertEquals(1, this.account.getCardAmount());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUndoCancelDebitCard() throws CommandException {
        this.test = new CancelCardCommand(this.handler1, this.office, this.dni,
                this.accountHandler);
        this.test.execute();
        this.test.undo();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRedoCancelDebitCard() throws CommandException {
        this.test = new CancelCardCommand(this.handler1, this.office, this.dni,
                this.accountHandler);
        this.test.execute();
        this.test.redo();
    }

    @Test
    public void testCancelCreditCard() throws CommandException {
        this.test = new CancelCardCommand(this.handler2, this.office, this.dni,
                this.accountHandler);
        Assert.assertEquals(2, this.account.getCardAmount());
        this.test.execute();
        Assert.assertEquals(1, this.account.getCardAmount());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUndoCancelCreditCard() throws CommandException {
        this.test = new CancelCardCommand(this.handler2, this.office, this.dni,
                this.accountHandler);
        this.test.execute();
        this.test.undo();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRedoCancelCreditCard() throws CommandException {
        this.test = new CancelCardCommand(this.handler2, this.office, this.dni,
                this.accountHandler);
        this.test.execute();
        this.test.redo();
    }
}
