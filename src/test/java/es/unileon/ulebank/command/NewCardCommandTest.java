package es.unileon.ulebank.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.security.auth.login.AccountNotFoundException;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.account.AccountHandler;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.client.PersonHandler;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.CommandHandler;
import es.unileon.ulebank.handler.DNIHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.handler.OfficeHandler;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CardType;

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
	private double buyLimitDiary;
	private double buyLimitMonthly;
	private double cashLimitDiary;
	private double cashLimitMonthly;
	private float commissionEmission;
	private float commissionMaintenance;
	private float commissionRenovate;
	private Bank bank;
	private Client client;
	private Account account;

    private String accountNumber = "0000000000";
	
	@Before
	public void setUp() throws MalformedHandlerException, WrongArgsException {
		this.bankHandler = new BankHandler("1234");
        this.bank = new Bank(bankHandler);
		this.office = new Office(new OfficeHandler("1234"), this.bank);
		this.dni = new PersonHandler(71557005, 'A');
		this.client = new Person(71557005, 'A');
		this.office.addClient(client);
		this.officeId = "01";
		this.cardId ="123401123456789";
		this.account = new Account(office, bank, accountNumber, client);
		this.accountHandler = account.getID();
		client.add(account);
		this.cardTypeCredit = CardType.CREDIT;
		this.cardTypeDebit = CardType.DEBIT;
		this.buyLimitDiary = 400.0;
		this.buyLimitMonthly = 1000.0;
		this.cashLimitDiary = 400.0;
		this.cashLimitMonthly = 1000.0;
		this.commissionEmission = 0;
		this.commissionMaintenance = 0;
		this.commissionRenovate = 0;
	}
	
	@Test
	public void testCommandNull() throws AccountNotFoundException {
		assertNull(test);
	}
	
	@Test
	public void testCommandNotNull() throws MalformedHandlerException {
		test = new NewCardCommand(office, client, account, cardTypeCredit, bankHandler.toString(), officeId, cardId, buyLimitDiary,	buyLimitMonthly, cashLimitDiary, cashLimitMonthly, commissionEmission, commissionMaintenance, commissionRenovate);
		assertNotNull(test);
	}
	
	@Test
	public void testCommandId() throws MalformedHandlerException {
		this.test = new NewCardCommand(office, client, account, cardTypeCredit, bankHandler.toString(), officeId, cardId, buyLimitDiary, 
				buyLimitMonthly, cashLimitDiary, cashLimitMonthly, commissionEmission, commissionMaintenance, commissionRenovate);
		assertTrue(test.getID() != null);
	}
	
	@Test
	public void testCreateCreditCard() throws Exception {
		this.test = new NewCardCommand(office, client, account, cardTypeCredit, bankHandler.toString(), officeId, cardId,
				buyLimitDiary, buyLimitMonthly, cashLimitDiary, cashLimitMonthly, commissionEmission, 
				commissionMaintenance, commissionRenovate);
		this.test.execute();
		CommandHandler id = (CommandHandler) test.getID();
		CardHandler cardHandler = (CardHandler) id.getId();
		Card card = office.searchClient((PersonHandler) dni).searchAccount((AccountHandler) accountHandler).searchCard(cardHandler);
		assertEquals(cardTypeCredit.toString(), card.getCardType());
		assertEquals(buyLimitDiary, card.getBuyLimitDiary(), 0.0001);
		assertEquals(buyLimitMonthly, card.getBuyLimitMonthly(), 0.0001);
		assertEquals(cashLimitDiary, card.getCashLimitDiary(), 0.0001);
		assertEquals(cashLimitMonthly, card.getCashLimitMonthly(), 0.0001);
		assertEquals(commissionEmission, card.getCommissionEmission(0), 0.0001);
		assertEquals(commissionMaintenance, card.getCommissionMaintenance(0), 0.0001);
		assertEquals(commissionRenovate, card.getCommissionRenovate(0), 0.0001);
	}
	
	@Test
	public void testCreateDebitCard() throws Exception {
		this.test = new NewCardCommand(office, client, account, cardTypeDebit, bankHandler.toString(), officeId, cardId,
				buyLimitDiary, buyLimitMonthly, cashLimitDiary, cashLimitMonthly, commissionEmission, 
				commissionMaintenance, commissionRenovate);
		this.test.execute();
		CommandHandler id = (CommandHandler) test.getID();
		CardHandler cardHandler = (CardHandler) id.getId();
		Card card = office.searchClient((PersonHandler) dni).searchAccount((AccountHandler) accountHandler).searchCard(cardHandler);
		assertEquals(cardTypeDebit.toString(), card.getCardType());
		assertEquals(buyLimitDiary, card.getBuyLimitDiary(), 0.0001);
		assertEquals(buyLimitMonthly, card.getBuyLimitMonthly(), 0.0001);
		assertEquals(cashLimitDiary, card.getCashLimitDiary(), 0.0001);
		assertEquals(cashLimitMonthly, card.getCashLimitMonthly(), 0.0001);
		assertEquals(commissionEmission, card.getCommissionEmission(0), 0.0001);
		assertEquals(commissionMaintenance, card.getCommissionMaintenance(0), 0.0001);
		assertEquals(commissionRenovate, card.getCommissionRenovate(0), 0.0001);
	}
	
	public void testUndoNewCreditCardCommand() throws Exception {
		this.test = new NewCardCommand(office, client, account, cardTypeCredit, bankHandler.toString(), officeId, cardId,
				buyLimitDiary, buyLimitMonthly, cashLimitDiary, cashLimitMonthly, commissionEmission, 
				commissionMaintenance, commissionRenovate);
		this.test.execute();
		assertEquals(1, office.searchClient((PersonHandler) dni).searchAccount((AccountHandler) accountHandler).getCardAmount());
		this.test.undo();
		assertEquals(0, office.searchClient((PersonHandler) dni).searchAccount((AccountHandler) accountHandler).getCardAmount());
	}
	
	public void testUndoNewDebitCardCommand() throws Exception {
		this.test = new NewCardCommand(office, client, account, cardTypeDebit, bankHandler.toString(), officeId, cardId,
				buyLimitDiary, buyLimitMonthly, cashLimitDiary, cashLimitMonthly, commissionEmission, 
				commissionMaintenance, commissionRenovate);
		this.test.execute();
		CommandHandler id = (CommandHandler) test.getID();
		CardHandler cardHandler = (CardHandler) id.getId();
		assertEquals(1, office.searchClient((DNIHandler) dni).searchAccount((AccountHandler) accountHandler).getCardAmount());
		this.test.undo();
		assertEquals(0, office.searchClient((DNIHandler) dni).searchAccount((AccountHandler) accountHandler).getCardAmount());
		office.searchClient((DNIHandler) dni).searchAccount((AccountHandler) accountHandler).searchCard(cardHandler);
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testRedoNewCreditCardCommand() throws Exception {
		this.test = new NewCardCommand(office, client, account, cardTypeCredit, bankHandler.toString(), officeId, cardId,
				buyLimitDiary, buyLimitMonthly, cashLimitDiary, cashLimitMonthly, commissionEmission, 
				commissionMaintenance, commissionRenovate);
		this.test.execute();
		this.test.undo();
		this.test.redo();
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testRedoNewDebitCardCommand() throws Exception {
		this.test = new NewCardCommand(office, client, account, cardTypeDebit, bankHandler.toString(), officeId, cardId,
				buyLimitDiary, buyLimitMonthly, cashLimitDiary, cashLimitMonthly, commissionEmission, 
				commissionMaintenance, commissionRenovate);
		this.test.execute();
		this.test.undo();
		this.test.redo();
	}
}
