/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.account;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.client.PersonHandler;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.DirectDebitTransaction;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.History;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class AccountTest {

    private static final double EPSILON = 0.00001;

    private Account account;
    private Office office;
    private Bank bank;
    private Client titular1;
    private Client titular2;
    private Client authorized1;
    private Client authorized2;

    private static int NIF1 = 98765432;
    private static int NIF2 = 87654321;
    private static int NIF3 = 76543210;
    private static int NIF4 = 12345678;

    private static char NIF_LETTER1 = 'M';
    private static char NIF_LETTER2 = 'X';
    private static char NIF_LETTER3 = 'S';
    private static char NIF_LETTER4 = 'Z';

    private final String accountNumber = "0000000000";

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.titular1 = new Person(AccountTest.NIF1, AccountTest.NIF_LETTER1);
        this.account = new Account(this.office, this.bank, this.accountNumber,
                this.titular1);
        this.titular2 = new Person(AccountTest.NIF2, AccountTest.NIF_LETTER2);
        this.authorized1 = new Person(AccountTest.NIF3, AccountTest.NIF_LETTER3);
        this.authorized2 = new Person(AccountTest.NIF4, AccountTest.NIF_LETTER4);
        Assert.assertTrue(this.account.addTitular(this.titular2));
        Assert.assertTrue(this.account.addAuthorized(this.authorized1));
        Assert.assertTrue(this.account.addAuthorized(this.authorized2));

    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberLengthNullClient()
            throws MalformedHandlerException, WrongArgsException {
        this.account = new Account(this.office, this.bank, "000000000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberLengthNullClient()
            throws MalformedHandlerException, WrongArgsException {
        this.account = new Account(this.office, this.bank, "000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectAccountnumberLengthInterspersedLettersNullClient()
            throws MalformedHandlerException, WrongArgsException {
        this.account = new Account(this.office, this.bank, "00aa00aa00", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn1CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "a000000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn2CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0a00000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn3CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00a0000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn4CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000a000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn5CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000a00000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn6CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000a0000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn7CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000a000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn8CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000000a00", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn9CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000000a0", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn10CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000000a", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn1CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "a0000000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn2CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0a000000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn3CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00a00000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn4CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000a0000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn5CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000a000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn6CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000a00000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn7CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000a0000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn8CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000000a000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn9CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000000a00", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn10CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000000a0", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn11CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000000000a", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn2CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "a00000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn3CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0a0000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn4CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00a000000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn5CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000a00000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn6CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000a0000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn7CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000a000", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn8CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000a00", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn9CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000000a0", null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn10CharacterNullClient()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000000a", null);
    }

    @Test(expected = WrongArgsException.class)
    public void testNullClient() throws MalformedHandlerException,
            WrongArgsException {
        new Account(this.office, this.bank, this.accountNumber, null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberLength() throws MalformedHandlerException,
            WrongArgsException {
        this.account = new Account(this.office, this.bank, "000000000000",
                this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberLength() throws MalformedHandlerException,
            WrongArgsException {
        this.account = new Account(this.office, this.bank, "000000",
                this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectAccountnumberLengthInterspersedLetters()
            throws MalformedHandlerException, WrongArgsException {
        this.account = new Account(this.office, this.bank, "00aa00aa00",
                this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn1Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "a000000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn2Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0a00000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn3Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00a0000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn4Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000a000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn5Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000a00000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn6Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000a0000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn7Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000a000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn8Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000000a00", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn9Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000000a0", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testCorrectLengthWithLetterIn10Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000000a", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn1Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "a0000000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn2Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0a000000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn3Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00a00000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn4Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000a0000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn5Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000a000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn6Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000a00000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn7Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000a0000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn8Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000000a000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn9Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000000a00", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn10Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000000a0", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreLengthWithLetterIn11Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000000000a", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn2Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "a00000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn3Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0a0000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn4Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00a000000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn5Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000a00000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn6Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000a0000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn7Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000a000", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn8Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "000000a00", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn9Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "0000000a0", this.titular1);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessLengthWithLetterIn10Character()
            throws MalformedHandlerException, WrongArgsException {
        new Account(this.office, this.bank, "00000000a", this.titular1);
    }

    /**
     * Test of getBalance method, of class Account.
     */
    @Test
    public void testGetBalance() {
        final double expResult = 0.0d;
        final double result = this.account.getBalance();
        Assert.assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testGetTitulars() {
        final List<Client> clients = this.account.getTitulars();
        Assert.assertEquals(clients.size(), 2);
        Assert.assertTrue(clients.contains(this.titular1));
        Assert.assertTrue(clients.contains(this.titular2));
    }

    @Test
    public void testGetAuthorizeds() {
        final List<Client> clients = this.account.getAuthorizeds();
        Assert.assertEquals(clients.size(), 2);
        Assert.assertTrue(clients.contains(this.authorized1));
        Assert.assertTrue(clients.contains(this.authorized2));
    }

    @Test
    public void testGetID() throws MalformedHandlerException,
            WrongArgsException {
        Handler accountNumber = new AccountHandler(this.office.getIdOffice(),
                this.bank.getID(), this.accountNumber);
        Assert.assertTrue(accountNumber.compareTo(this.account.getID()) == 0);
        accountNumber = new AccountHandler(this.office.getIdOffice(),
                this.bank.getID(), "1299567899");
        Assert.assertFalse(accountNumber.compareTo(this.account.getID()) == 0);
        accountNumber = new AccountHandler(this.office.getIdOffice(),
                new GenericHandler("9999"), this.accountNumber);
        Assert.assertFalse(accountNumber.compareTo(this.account.getID()) == 0);
        accountNumber = new AccountHandler(new GenericHandler("9999"),
                this.office.getIdOffice(), this.accountNumber);
        Assert.assertFalse(accountNumber.compareTo(this.account.getID()) == 0);
    }

    @Test
    public void testDoTransaction() throws TransactionException {
        final double amount = 10.0;
        final GenericTransaction t2 = new GenericTransaction(amount,
                new Date(), "Salary");
        t2.setEffectiveDate(new Date(System.currentTimeMillis()));
        this.account.doTransaction(t2);
        Assert.assertEquals(amount, this.account.getBalance(),
                AccountTest.EPSILON);

        final GenericTransaction t = new GenericTransaction(-amount,
                new Date(), "Salary");
        t.setEffectiveDate(new Date(System.currentTimeMillis()));
        this.account.doTransaction(t);
        Assert.assertEquals(0.0, this.account.getBalance(), AccountTest.EPSILON);

        final GenericTransaction t3 = new GenericTransaction(amount,
                new Date(), "Salary");
        t.setEffectiveDate(new Date(System.currentTimeMillis()));
        this.account.doTransaction(t3);
        Assert.assertEquals(amount, this.account.getBalance(),
                AccountTest.EPSILON);

        final GenericTransaction t4 = new GenericTransaction(amount,
                new Date(), "Salary");
        t.setEffectiveDate(new Date(System.currentTimeMillis()));
        this.account.doTransaction(t4);
        Assert.assertEquals(2 * amount, this.account.getBalance(),
                AccountTest.EPSILON);
    }

    @Test
    public void testGetterAndSetterMaxOverdraft() {
        Assert.assertEquals(this.account.getMaxOverdraft(), 0.0,
                AccountTest.EPSILON);
        double maxOverdraft = 1;
        Assert.assertTrue(this.account.setMaxOverdraft(maxOverdraft));
        Assert.assertEquals(this.account.getMaxOverdraft(), maxOverdraft,
                AccountTest.EPSILON);
        Assert.assertFalse(this.account.setMaxOverdraft(-maxOverdraft));
        Assert.assertEquals(this.account.getMaxOverdraft(), maxOverdraft,
                AccountTest.EPSILON);
        maxOverdraft = 10000.0d;
        Assert.assertTrue(this.account.setMaxOverdraft(maxOverdraft));
        Assert.assertEquals(this.account.getMaxOverdraft(), maxOverdraft,
                AccountTest.EPSILON);
    }

    @Test
    public void testGetterAndSetterLiquidationFrecuency() {
        final int frecuency = 1;
        Assert.assertEquals(this.account.getLiquidationFrecuency(),
                Account.DEFAULT_LIQUIDATION_FREQUENCY);
        Assert.assertTrue(this.account.setLiquidationFrecuency(frecuency));
        Assert.assertEquals(this.account.getLiquidationFrecuency(), frecuency);
        Assert.assertFalse(this.account.setLiquidationFrecuency(0));
        Assert.assertEquals(this.account.getLiquidationFrecuency(), frecuency);
        Assert.assertFalse(this.account.setLiquidationFrecuency(-1));
        Assert.assertEquals(this.account.getLiquidationFrecuency(), frecuency);

    }

    @Test(expected = TransactionException.class)
    public void testDoTransactionNotEnoughOverdraftZero()
            throws TransactionException {
        final double amount = -10.0;
        final GenericTransaction t2 = new GenericTransaction(amount,
                new Date(), "Salary");
        t2.setEffectiveDate(new Date(System.currentTimeMillis()));
        this.account.doTransaction(t2);
    }

    @Test(expected = TransactionException.class)
    public void testDoTransactionRepeatedTransaction()
            throws TransactionException {
        final double amount = 10.0;
        final GenericTransaction t2 = new GenericTransaction(amount,
                new Date(), "Salary");
        t2.setEffectiveDate(new Date(System.currentTimeMillis()));
        this.account.doTransaction(t2);
        this.account.doTransaction(t2);
    }

    @Test(expected = TransactionException.class)
    public void testDoTransactionNotEnoughOverdraft()
            throws TransactionException {
        final double amount = -10.0;
        this.account.setMaxOverdraft(-1.0d * (amount / 2));
        final GenericTransaction t2 = new GenericTransaction(amount,
                new Date(), "Salary");
        t2.setEffectiveDate(new Date(System.currentTimeMillis()));
        this.account.doTransaction(t2);
    }

    @Test
    public void testAccountHistory() throws TransactionException {
        final double amount = 10.0;
        final GenericTransaction t2 = new GenericTransaction(amount,
                new Date(), "Salary");
        t2.setEffectiveDate(new Date(System.currentTimeMillis()));
        this.account.doTransaction(t2);

        final GenericTransaction t = new GenericTransaction(-amount,
                new Date(), "Salary");
        t.setEffectiveDate(new Date(System.currentTimeMillis()));
        this.account.doTransaction(t);

        final History history = this.account.getHistory();
        ;
        final Iterator<Transaction> it = history.getIterator();
        this.compareEntryAndTransactionsWithAsserts(t2, it.next());
        this.compareEntryAndTransactionsWithAsserts(t, it.next());
    }

    @Test
    public void testDirectDebitHistory() throws TransactionException {
        final double amount = 10;
        final DirectDebitTransaction ddt = new DirectDebitTransaction(amount,
                new Date(), "nothing", new GenericHandler("1234"));
        ddt.setEffectiveDate(new Date());
        this.account.doDirectDebit(ddt);
        Assert.assertEquals(this.account.getBalance(), amount,
                AccountTest.EPSILON);
        final DirectDebitTransaction ddt2 = new DirectDebitTransaction(amount,
                new Date(), "nothing", new GenericHandler("1224"));
        ddt2.setEffectiveDate(new Date());
        this.account.doDirectDebit(ddt2);
        Assert.assertEquals(this.account.getBalance(), 2 * amount,
                AccountTest.EPSILON);
        final DirectDebitTransaction ddt3 = new DirectDebitTransaction(amount,
                new Date(), "nothing", new GenericHandler("1334"));
        ddt3.setEffectiveDate(new Date());
        this.account.doDirectDebit(ddt3);
        Assert.assertEquals(this.account.getBalance(), 3 * amount,
                AccountTest.EPSILON);
        final Iterator<DirectDebitTransaction> it = this.account
                .getDirectDebitHistory().getIterator();
        this.compareEntryAndDirectDebitTransactionWithAsserts(ddt, it.next());
        this.compareEntryAndDirectDebitTransactionWithAsserts(ddt2, it.next());
        this.compareEntryAndDirectDebitTransactionWithAsserts(ddt3, it.next());

    }

    public void compareEntryAndDirectDebitTransactionWithAsserts(
            DirectDebitTransaction t, DirectDebitTransaction entry) {
        Assert.assertTrue(t.getSubject().equals(entry.getSubject()));
        Assert.assertTrue(t.getId().compareTo(entry.getId()) == 0);
        Assert.assertEquals(t.getAmount(), entry.getAmount(),
                AccountTest.EPSILON);
        Assert.assertTrue(t.getDate().compareTo(entry.getDate()) == 0);
        Assert.assertTrue(t.getEffectiveDate().compareTo(
                entry.getEffectiveDate()) == 0);
        Assert.assertTrue(t.getDirectDebitId().compareTo(
                entry.getDirectDebitId()) == 0);
    }

    public void compareEntryAndTransactionsWithAsserts(Transaction t,
            Transaction entry) {
        Assert.assertTrue(t.getSubject().equals(entry.getSubject()));
        Assert.assertTrue(t.getId().compareTo(entry.getId()) == 0);
        Assert.assertEquals(t.getAmount(), entry.getAmount(),
                AccountTest.EPSILON);
        Assert.assertTrue(t.getDate().compareTo(entry.getDate()) == 0);
        Assert.assertTrue(t.getEffectiveDate().compareTo(
                entry.getEffectiveDate()) == 0);
    }

    /**
     * Test of getLiquidationFrecuency method, of class Account.
     */
    @Test
    public void testGetAndSetLiquidationFrecuency() {
        Assert.assertEquals(this.account.getLiquidationFrecuency(),
                Account.DEFAULT_LIQUIDATION_FREQUENCY);
        final int frecuency = 1;
        Assert.assertTrue(this.account.setLiquidationFrecuency(frecuency));
        Assert.assertEquals(this.account.getLiquidationFrecuency(), frecuency);
        Assert.assertFalse(this.account.setLiquidationFrecuency(-frecuency));
        Assert.assertEquals(this.account.getLiquidationFrecuency(), frecuency);

    }

    /**
     * Test of setMaxOverdraft method, of class Account.
     */
    @Test
    public void testSetAndGetMaxOverdraft() {
        Assert.assertEquals(this.account.getMaxOverdraft(), 0.0,
                AccountTest.EPSILON);
        final double valid = 100;
        Assert.assertTrue(this.account.setMaxOverdraft(valid));
        Assert.assertEquals(this.account.getMaxOverdraft(), valid,
                AccountTest.EPSILON);
        Assert.assertFalse(this.account.setMaxOverdraft(-valid));
        Assert.assertEquals(this.account.getMaxOverdraft(), valid,
                AccountTest.EPSILON);
    }

    /**
     * Test of addTitular method, of class Account.
     * 
     * @throws MalformedHandlerException
     */
    @Test
    public void testAddTitular() throws MalformedHandlerException {
        final Client c = new Person(89051755, 'X');
        Assert.assertTrue(this.account.addTitular(c));
        Assert.assertFalse(this.account.addTitular(c));
    }

    /**
     * Test of deleteTitular method, of class Account.
     * 
     * @throws MalformedHandlerException
     */
    @Test
    public void testDeleteTitular() throws MalformedHandlerException {
        Assert.assertEquals(this.account.getTitulars().size(), 2);
        Assert.assertFalse(this.account
                .deleteTitular(new GenericHandler("!234")));
        Assert.assertTrue(this.account.deleteTitular(new PersonHandler(
                AccountTest.NIF1, AccountTest.NIF_LETTER1)));
        Assert.assertEquals(this.account.getTitulars().size(), 1);
        Assert.assertFalse(this.account.deleteTitular(new PersonHandler(
                AccountTest.NIF2, AccountTest.NIF_LETTER2)));
        Assert.assertEquals(this.account.getTitulars().size(), 1);
        Assert.assertFalse(this.account
                .deleteTitular(new GenericHandler("!234")));
        Assert.assertFalse(this.account
                .deleteTitular(new GenericHandler("!234")));
    }

    /**
     * Test of addAuthorized method, of class Account.
     * 
     * @throws MalformedHandlerException
     */
    @Test
    public void testAddAuthorized() throws MalformedHandlerException {
        final Client c = new Person(89051755, 'X');
        Assert.assertTrue(this.account.addAuthorized(c));
        Assert.assertFalse(this.account.addAuthorized(c));
    }

    /**
     * Test of deleteAuthorized method, of class Account.
     * 
     * @throws MalformedHandlerException
     */
    @Test
    public void testDeleteAuthorized() throws MalformedHandlerException {
        Assert.assertEquals(this.account.getAuthorizeds().size(), 2);
        Assert.assertFalse(this.account.deleteAuthorized(new GenericHandler(
                "!234")));
        Assert.assertTrue(this.account.deleteAuthorized(new PersonHandler(
                AccountTest.NIF3, AccountTest.NIF_LETTER3)));
        Assert.assertEquals(this.account.getAuthorizeds().size(), 1);
        Assert.assertTrue(this.account.deleteAuthorized(new PersonHandler(
                AccountTest.NIF4, AccountTest.NIF_LETTER4)));
        Assert.assertEquals(this.account.getAuthorizeds().size(), 0);
        Assert.assertFalse(this.account.deleteAuthorized(new GenericHandler(
                "!234")));
    }

    /**
     * Test of doDirectDebit method, of class Account.
     */
    @Test
    public void testDoDirectDebit() throws Exception {
        Assert.fail("The test case is a prototype.");
    }

    /**
     * Test of doLiquidation method, of class Account.
     */
    @Test
    public void testDoLiquidation() {
        Assert.fail("The test case is a prototype.");
    }
}
