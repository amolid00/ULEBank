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
import es.unileon.ulebank.client.PersonHandler;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.office.OfficeHandler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CardType;
import es.unileon.ulebank.payments.handler.CardHandler;

public class NewCardCommandTest {
    private NewCardCommand test;
    private Handler bankHandler;
    private Handler dni;
    private String officeId;
    private String cardId;
    private Office office;
    private Handler accountHandler;
    private CardType cardTypeCredit;
    private CardType cardTypeDebit;
    private Bank bank;
    private Client client;
    private Account account;

    private final String accountNumber = "0000000000";

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.bankHandler = new BankHandler("1234");
        this.bank = new Bank(this.bankHandler);
        this.office = new Office(new OfficeHandler("1234"), this.bank);
        this.dni = new PersonHandler(71557005, 'A');
        this.client = new Person(71557005, 'A');
        this.office.addClient(this.client);
        this.officeId = "01";
        this.cardId = "123401123456789";
        this.account = new Account(this.office, this.bank, this.accountNumber,
                this.client);
        this.accountHandler = this.account.getID();
        this.client.add(this.account);
        this.cardTypeCredit = CardType.CREDIT;
        this.cardTypeDebit = CardType.DEBIT;
    }

    @Test
    public void testCommandNull() throws AccountNotFoundException {
        Assert.assertNull(this.test);
    }

    @Test
    public void testCommandNotNull() throws CommandException {
        this.test = new NewCardCommand(this.office, this.client, this.account,
                this.cardTypeCredit,
                this.officeId, this.cardId);
        Assert.assertNotNull(this.test);
    }

    @Test
    public void testCommandId() throws CommandException {
        this.test = new NewCardCommand(this.office, this.client, this.account,
                this.cardTypeCredit,
                this.officeId, this.cardId);
        Assert.assertTrue(this.test.getID() != null);
    }

    @Test
    public void testCreateCreditCard() throws Exception {
        this.test = new NewCardCommand(this.office, this.client, this.account,
                this.cardTypeCredit, 
                this.officeId, this.cardId);
        this.test.execute();
        final CommandHandler id = (CommandHandler) this.test.getID();
        final CardHandler cardHandler = (CardHandler) id.getId();
        final Card card = this.office.searchClient(this.dni)
                .searchAccount(this.accountHandler).searchCard(cardHandler);
        Assert.assertEquals(this.cardTypeCredit.toString(), card.getCardType());
    }

    @Test
    public void testCreateDebitCard() throws Exception {
        this.test = new NewCardCommand(this.office, this.client, this.account,
                this.cardTypeDebit, this.officeId,
                this.cardId);
        this.test.execute();
        final CommandHandler id = (CommandHandler) this.test.getID();
        final CardHandler cardHandler = (CardHandler) id.getId();
        final Card card = this.office.searchClient(this.dni)
                .searchAccount(this.accountHandler).searchCard(cardHandler);
        Assert.assertEquals(this.cardTypeDebit.toString(), card.getCardType());
    }

    public void testUndoNewCreditCardCommand() throws Exception {
        this.test = new NewCardCommand(this.office, this.client, this.account,
                this.cardTypeCredit,
                this.officeId, this.cardId);
        this.test.execute();
        Assert.assertEquals(1, this.office.searchClient(this.dni)
                .searchAccount(this.accountHandler).getCardAmount());
        this.test.undo();
        Assert.assertEquals(0, this.office.searchClient(this.dni)
                .searchAccount(this.accountHandler).getCardAmount());
    }

    public void testUndoNewDebitCardCommand() throws Exception {
        this.test = new NewCardCommand(this.office, this.client, this.account,
                this.cardTypeDebit, this.officeId,
                this.cardId);
        this.test.execute();
        final CommandHandler id = (CommandHandler) this.test.getID();
        final CardHandler cardHandler = (CardHandler) id.getId();
        Assert.assertEquals(1, this.office.searchClient(this.dni)
                .searchAccount(this.accountHandler).getCardAmount());
        this.test.undo();
        Assert.assertEquals(0, this.office.searchClient(this.dni)
                .searchAccount(this.accountHandler).getCardAmount());
        this.office.searchClient(this.dni).searchAccount(this.accountHandler)
        .searchCard(cardHandler);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRedoNewCreditCardCommand() throws Exception {
        this.test = new NewCardCommand(this.office, this.client, this.account,
                this.cardTypeCredit,
                this.officeId, this.cardId);
        this.test.execute();
        this.test.undo();
        this.test.redo();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRedoNewDebitCardCommand() throws Exception {
        this.test = new NewCardCommand(this.office, this.client, this.account,
                this.cardTypeDebit, this.officeId,
                this.cardId);
        this.test.execute();
        this.test.undo();
        this.test.redo();
    }
}
