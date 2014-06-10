package es.unileon.ulebank.command;

import java.util.Date;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.account.DetailedInformation;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class DoTransactionCommandTest {

    private DoTransactionCommand command;
    private Bank bank;
    private double amount;
    private Office office;
    private Handler commandId;
    private Account account;
    private Client authorized;
    private String subject;
    private Date date;
    private DetailedInformation info;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1231"), this.bank);
        this.bank.addOffice(this.office);
        this.authorized = new Person(71524276, 'A');
        this.account = new Account(this.office, this.bank,
                this.office.getNewAccountNumber(), this.authorized);
        this.office.addAccount(this.account);
        this.date = new Date();
        this.subject = "subject";
        this.info = new DetailedInformation();
        this.command = new DoTransactionCommand(this.office, this.amount,
                this.account.getID(), this.date, this.subject, this.info,
                this.commandId);
    }

    @Test
    public void testExecute() {
        this.command.execute();
        final Transaction t = this.account.getHistory().getIterator().next();
        Assert.assertEquals(t.getAmount(), this.amount, Math.pow(10, -10));
        Assert.assertEquals(this.date, t.getDate());
        Assert.assertEquals(this.info, t.getDetailedInformation());
        Assert.assertEquals(this.subject, t.getSubject());
    }

    @Test
    public void testUndoFromExecute() {
        this.command.execute();
        this.command.undo();
        final Transaction t = this.account.getHistory().getIterator().next();
        Assert.assertEquals(t.getAmount(), this.amount, Math.pow(10, -10));
        Assert.assertEquals(this.date, t.getDate());
        Assert.assertEquals(this.info, t.getDetailedInformation());
        Assert.assertEquals(this.subject, t.getSubject());
    }

    @Test
    public void testUndoFromRedo() {
        this.command.redo();
        this.command.undo();
        final Iterator<Transaction> it = this.account.getHistory()
                .getIterator();
        Assert.assertFalse(it.hasNext());
    }

    @Test
    public void testRedo() {
        this.command.execute();
        this.command.undo();
        this.command.redo();
        final Transaction t = this.account.getHistory().getIterator().next();
        Assert.assertEquals(t.getAmount(), this.amount, Math.pow(10, -10));
        Assert.assertEquals(this.date, t.getDate());
        Assert.assertEquals(this.info, t.getDetailedInformation());
        Assert.assertEquals(this.subject, t.getSubject());
    }
}
