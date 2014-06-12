package es.unileon.ulebank.command;

import java.io.IOException;

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
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.office.OfficeHandler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.DebitCard;
import es.unileon.ulebank.payments.exceptions.PaymentException;
import es.unileon.ulebank.payments.handler.CardHandler;

public class PaymentCommandTests {
	
	private Handler handler1;
    private Office office;
    private Handler dni;
    private Handler accountHandler;
    private Client client;
    private Account account;
    private Card card1;
    private PaymentCommand test;
    private Bank bank;

    private final String accountNumber = "0000000000";

    @Before
    public void setUp() throws NumberFormatException, CommissionException,
            IOException, InvalidFeeException, MalformedHandlerException,
            WrongArgsException, PaymentException {
        final Handler bankHandler = new BankHandler("1234");
        this.handler1 = new CardHandler(bankHandler, "01", "123456789");
        this.bank = new Bank(bankHandler);
        this.office = new Office(new OfficeHandler("1234"), this.bank);
        this.dni = new PersonHandler(71557005, 'A');
        this.client = new Person(71557005, 'A');
        this.office.addClient(this.client);
        this.account = new Account(this.office, this.bank, this.accountNumber,
                this.client);
        this.accountHandler = this.account.getID();
        this.client.add(this.account);
        this.card1 = new DebitCard(this.handler1, this.client, this.account);
        this.account.addCard(this.card1);
    }

    @Test
    public void testCommandNotNull() throws CommandException {
        this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                this.accountHandler, 20.00, "Concept");
        Assert.assertNotNull(this.test);
    }

    @Test
    public void testCommandNull() {
        Assert.assertNull(this.test);
    }
    
    @Test
    public void testCommandId() throws CommandException {
        this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                this.accountHandler, 20.00, "Concept");
        final CommandHandler handler = (CommandHandler) this.test.getID();
        Assert.assertTrue(handler.getId().compareTo(this.card1.getId()) == 0);
    }
    
    @Test
	public void testExecuteCommandOk() throws CommandException {
		this.account.setBalance(100.00);
		this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                this.accountHandler, 20.00, "Concept");
		this.test.execute();
		Assert.assertEquals(80.00, this.account.getBalance(),0.0);
	}

	@Test (expected = CommandException.class)
	public void testExecuteCommandWithoutBalance() throws CommandException {
		this.account.setBalance(0.00);
		this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                this.accountHandler, 20.00, "Concept");
		this.test.execute();
	}
	
	@Test (expected = CommandException.class)
	public void testExecuteCommandWithoutConcept() throws CommandException {
		this.account.setBalance(100.00);
		this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                this.accountHandler, 20.00, "");
		this.test.execute();
	}
	
	@Test (expected = CommandException.class)
	public void testExecuteCommandWithNeutralAmount() throws CommandException {
		this.account.setBalance(100.00);
		this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                this.accountHandler, 0.00, "Concept");
		this.test.execute();
	}
	
    @Test
	public void testUndoCommandOk() throws CommandException, PaymentException {
		this.account.setBalance(100.00);
		this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                this.accountHandler, 20.00, "Concept");
		this.test.execute();
		Assert.assertEquals(80.00, this.account.getBalance(),0.0);
		this.test.undo();
		Assert.assertEquals(100.00, this.account.getBalance(),0.0);
	}
    
    @Test (expected = CommandException.class)
	public void testUndoCommandFail() throws CommandException, PaymentException {
		this.account.setBalance(100.00);
		this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                this.accountHandler, 20.00, "Concept");
		this.test.undo();
	}
    
    @Test
   	public void testRedoCommandOk() throws CommandException, PaymentException, TransactionException {
   		this.account.setBalance(100.00);
   		this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                   this.accountHandler, 20.00, "Concept");
   		this.test.execute();
   		Assert.assertEquals(80.00, this.account.getBalance(),0.0);
   		this.test.undo();
   		Assert.assertEquals(100.00, this.account.getBalance(),0.0);
   		this.test.redo();
   		Assert.assertEquals(80.00, this.account.getBalance(),0.0);
   	}
    
    @Test (expected = CommandException.class)
   	public void testRedoCommandFail() throws CommandException, PaymentException, TransactionException {
   		this.account.setBalance(100.00);
   		this.test = new PaymentCommand(this.handler1, this.office, this.dni,
                   this.accountHandler, 20.00, "Concept");
   		this.test.execute();
   		Assert.assertEquals(80.00, this.account.getBalance(),0.0);
   		this.test.redo();
   	}
}
