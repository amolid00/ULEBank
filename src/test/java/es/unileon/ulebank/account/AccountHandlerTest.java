/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.account;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

/**
 *
 * @author runix
 */
public class AccountHandlerTest {

    private AccountHandler accountHandler1;
    private Bank bank;
    private Office office;
    private String dc;
    private String accountNumber;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.accountNumber = "1234567891";
        this.accountHandler1 = new AccountHandler(this.office.getIdOffice(),
                this.bank.getID(), this.accountNumber);
        this.dc = "10";
    }

    @Test
    public void testHandlerFromAnother() throws MalformedHandlerException {
        final Handler another = new GenericHandler(this.bank.getID().toString()
                + AccountHandler.SEPARATOR
                + this.office.getIdOffice().toString()
                + AccountHandler.SEPARATOR + this.dc + AccountHandler.SEPARATOR
                + this.accountNumber);
        final AccountHandler parsed = new AccountHandler(another);
        Assert.assertTrue(this.bank.getID().compareTo(parsed.getBankHandler()) == 0);
        Assert.assertTrue(this.office.getIdOffice().compareTo(
                parsed.getOfficeHandler()) == 0);
        Assert.assertTrue(this.accountNumber.equals(parsed.getNumber()));
        Assert.assertTrue(this.dc.equals(this.dc));
    }

    @Test
    public void testHandlerFromAnotherWithSymbols()
            throws MalformedHandlerException {
        final Handler another = new GenericHandler(this.bank.getID().toString()
                + AccountHandler.SEPARATOR
                + this.office.getIdOffice().toString()
                + AccountHandler.SEPARATOR + this.dc + AccountHandler.SEPARATOR
                + this.accountNumber);
        final AccountHandler parsed = new AccountHandler(another);
        Assert.assertTrue(this.bank.getID().compareTo(parsed.getBankHandler()) == 0);
        Assert.assertTrue(this.office.getIdOffice().compareTo(
                parsed.getOfficeHandler()) == 0);
        Assert.assertTrue(this.accountNumber.equals(parsed.getNumber()));
        Assert.assertTrue(this.dc.equals(this.dc));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreBankHandlerLength() throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "0000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreBankHandlerLengthWithCharacterIn1Position()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("a0000"), new GenericHandler(
                "0000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessBankHandlerLength() throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00"),
                new GenericHandler("0000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessBankHandlerLengthWithCharacterIn1Position()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("a00"),
                new GenericHandler("0000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLength() throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "000000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLengthWithCharacterIn1Position()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "a0000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLength() throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler("00"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLengthWithCharacterIn1Position()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler("a00"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLength()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "0000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn1Position()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "0000"), "a0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLength()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "0000"), "000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn1Position()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "0000"), "a00000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("0000"), "00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("000"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("0a0"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"),
                new GenericHandler("000"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "000"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "0a0"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler("0a0"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"), new GenericHandler(
                "00000"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerMoreBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"), new GenericHandler(
                "0a000"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerWithLetterMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"), new GenericHandler(
                "00000"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler("000"), "00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler("0a0"), "00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler("000"), "00a000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler("000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler("0a0"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler("000"), "000000a00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "00000"), "00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "0a000"), "00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "00000"), "00a000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"),
                new GenericHandler("0000"), "00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessOfficeHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("0000"), "00a000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("0000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"),
                new GenericHandler("0000"), "000000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessOfficeHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("0000"), "000000a00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "0000"), "00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000"), new GenericHandler(
                "0000"), "00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreOfficeHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "0000"), "00a000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("000"), "000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("000"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "000"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "00000"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "00000"), "000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessOfficeHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("000"), "00a000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessOfficeHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("000"), "0000a000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "000"), "0000a000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "00000"), "00000a00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreOfficeHandlerMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "00000"), "00a000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"),
                new GenericHandler("000"), "000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"),
                new GenericHandler("000"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a00"), new GenericHandler(
                "000"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerWithLetterMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000"), new GenericHandler(
                "00000"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerWithLetterMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a00"), new GenericHandler(
                "00000"), "000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("0a0"), "000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("0a0"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "0a0"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerMoreBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "0a000"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerMoreBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "0a000"), "000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessOfficeHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"),
                new GenericHandler("000"), "0000a0");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessOfficeHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"),
                new GenericHandler("000"), "000a0000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerWithLetterLessBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000"), new GenericHandler(
                "000"), "000a0000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerWithLetterMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a00"), new GenericHandler(
                "00000"), "0a000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreOfficeHandlerWithLetterMoreBankHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000"), new GenericHandler(
                "00000"), "000a00000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerWithLetterLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"),
                new GenericHandler("0a0"), "000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerWithLetterLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0"),
                new GenericHandler("a00"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerWithLetterLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000"), new GenericHandler(
                "0a0"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerWithLetterMoreBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000"), new GenericHandler(
                "0a000"), "00000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerWithLetterMoreBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a00"), new GenericHandler(
                "a0000"), "000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessOfficeHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("0a0"), "000a00");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessOfficeHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000"),
                new GenericHandler("0a0"), "000a0000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerLessBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "a00"), "00a00000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerMoreBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "a0000"), "000a0000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreOfficeHandlerMoreBankHandlerWithLetter()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000"), new GenericHandler(
                "00a00"), "00a000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullBank() throws MalformedHandlerException {
        new AccountHandler(null, new GenericHandler("0000"), "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullOffice() throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), null, "0000000000");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullAccountNumber() throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), new GenericHandler(
                "0000"), null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullAccountNumberNullBank()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"), null, null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullAccountNumberNullOffice()
            throws MalformedHandlerException {
        new AccountHandler(null, new GenericHandler("0000"), null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullAccountNumberNullOfficeNullBank()
            throws MalformedHandlerException {
        new AccountHandler(null, null, null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullHandlerBankContent() throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler(null), "1234567891");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullHandlerOfficeContent() throws MalformedHandlerException {
        new AccountHandler(new GenericHandler(null),
                new GenericHandler("0000"), "1234567891");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullHandlerBankContentNullAccountNumber()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000"),
                new GenericHandler(null), null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNullHandlerOfficeContentNullAccountNumber()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler(null),
                new GenericHandler("0000"), null);
    }

    /*
     * 
     * Create handler from another
     */
    @Test(expected = MalformedHandlerException.class)
    public void testMoreBankHandlerLengthParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreBankHandlerLengthWithCharacterIn1PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("a0000-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreBankHandlerLengthWithCharacterIn2PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreBankHandlerLengthWithCharacterIn3PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a00-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreBankHandlerLengthWithCharacterIn4PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000a0-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreBankHandlerLengthWithCharacterIn5PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000a-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessBankHandlerLengthParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessBankHandlerLengthWithCharacterIn1PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("a00-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessBankHandlerLengthWithCharacterIn2PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessBankHandlerLengthWithCharacterIn3PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLengthParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-000000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLengthWithCharacterIn1PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-a0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLengthWithCharacterIn2PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0a000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLengthWithCharacterIn3PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-00a00-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLengthWithCharacterIn4PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-000a0-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLengthWithCharacterIn5PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000a-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLengthParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-00-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLengthWithCharacterIn1PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-a00-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLengthWithCharacterIn2PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0a0-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLengthWithCharacterIn3PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-00a-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn1PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-a0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn2PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-0a000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn3PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-00a00000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn4PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-000a0000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn5PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-0000a000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn6PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-00000a00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn7PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-000000a0000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn8PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-0000000a000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn9PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-00000000a00"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn10PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-000000000a0"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLengthWithCharacterIn11PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-0000000000a"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn1PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-a00000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn2PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-0a0000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn3PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-00a000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn4PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-000a00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn5PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-0000a0000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn6PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-00000a000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn7PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-000000a00"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn8PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-0000000a0"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLengthWithCharacterIn9PositionParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0000-00-00000000a"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0000-00-00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-000-00-0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0a0-00-0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-000-00-0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-000-00-0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-0a0-00-0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreOfficeHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0a0-00-0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-00000-00-0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerMoreBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0a000-00-0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessOfficeHandlerWithLetterMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-00000-00-0000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-000-00-00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0a0-00-00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-000-00-00a000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0a0-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-000-00-000000a00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-00000-00-00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-0a000-00-00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0000-00000-00-00a000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-0000-00-00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessOfficeHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0000-00-00a000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-0000-00-000000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessOfficeHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0000-00-000000a00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-0000-00-00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000-0000-00-00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreOfficeHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-0000-00-00a000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-000-00-000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-000-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-000-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-00000-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-00000-00-000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessOfficeHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-000-00-00a000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessOfficeHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-000-00-0000a000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-000-00-0000a000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-00000-00-00000a00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreOfficeHandlerMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-00000-00-00a000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-000-00-000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-000-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a00-000-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerWithLetterMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000-00000-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerWithLetterMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a00-00000-00-000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0a0-00-000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0a0-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-0a0-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerMoreBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-0a000-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerMoreBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-0a000-00-000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessOfficeHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-000-00-0000a0"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessOfficeHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-000-00-000a0000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerWithLetterLessBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000-000-00-000a0000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerWithLetterMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a00-00000-00-0a000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreOfficeHandlerWithLetterMoreBankHandlerParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000-00000-00-000a00000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerLessOfficeHandlerWithLetterLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-0a0-00-000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerLessOfficeHandlerWithLetterLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a0-a00-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerWithLetterLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000-0a0-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerMoreOfficeHandlerWithLetterMoreBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("0a000-0a000-00-00000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerMoreOfficeHandlerWithLetterMoreBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00a00-a0000-00-000000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterLessOfficeHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0a0-00-000a00"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterLessOfficeHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("000-0a0-00-000a0000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerLessBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-a00-00-00a00000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testMoreAccountnumberHandlerWithLetterMoreOfficeHandlerMoreBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-a0000-00-000a0000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLessAccountnumberHandlerWithLetterMoreOfficeHandlerMoreBankHandlerWithLetterParsingHandler()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler("00000-00a00-00-00a000000"));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testParsingHandlerBadDCAllDigits()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler(this.bank.getID().toString()
                + AccountHandler.SEPARATOR
                + this.office.getIdOffice().toString() + "99"
                + this.accountNumber));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testParsingHandlerBadDCFirstDigit()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler(this.bank.getID().toString()
                + AccountHandler.SEPARATOR
                + this.office.getIdOffice().toString() + "11"
                + this.accountNumber));
    }

    @Test(expected = MalformedHandlerException.class)
    public void testParsingHandlerBadDCLastDigit()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler(this.bank.getID().toString()
                + AccountHandler.SEPARATOR
                + this.office.getIdOffice().toString()
                + AccountHandler.SEPARATOR + "21" + AccountHandler.SEPARATOR
                + this.accountNumber));

    }

    @Test(expected = MalformedHandlerException.class)
    public void testParsingNullHandler() throws MalformedHandlerException {
        new AccountHandler(null);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testParsingGenericWithNullString()
            throws MalformedHandlerException {
        new AccountHandler(new GenericHandler(null));
    }

    // Creating OK
    @Test
    public void testParsingHandlerOk() throws MalformedHandlerException {
        final AccountHandler ac = new AccountHandler(new GenericHandler(
                this.bank.getID().toString() + AccountHandler.SEPARATOR
                        + this.office.getIdOffice().toString()
                        + AccountHandler.SEPARATOR + this.dc
                        + AccountHandler.SEPARATOR + this.accountNumber));
        Assert.assertTrue(ac.getBankHandler().toString()
                .equals(this.bank.getID().toString()));
        Assert.assertTrue(ac.getOfficeHandler().toString()
                .equals(this.office.getIdOffice().toString()));
        Assert.assertTrue(ac.getDC().toString().equals(this.dc.toString()));
        Assert.assertTrue(ac.getNumber().toString()
                .equals(this.accountNumber.toString()));
    }

    @Test
    public void testCreateOK() throws MalformedHandlerException {
        final AccountHandler ac = new AccountHandler(this.office.getIdOffice(),
                this.bank.getID(), this.accountNumber);
        Assert.assertTrue(ac.getBankHandler().toString()
                .equals(this.bank.getID().toString()));
        Assert.assertTrue(ac.getOfficeHandler().toString()
                .equals(this.office.getIdOffice().toString()));
        Assert.assertTrue(ac.getDC().toString().equals(this.dc.toString()));
        Assert.assertTrue(ac.getNumber().toString()
                .equals(this.accountNumber.toString()));
    }

    // Test compareTo
    @Test
    public void testCompareToOK() throws MalformedHandlerException {
        final Handler handler = new AccountHandler(this.office.getIdOffice(),
                this.bank.getID(), this.accountNumber);
        Assert.assertEquals(handler.compareTo(this.accountHandler1), 0);
        final Handler handlerFromRaw = new AccountHandler(new GenericHandler(
                this.office.getIdOffice().toString() + AccountHandler.SEPARATOR
                        + this.bank.getID().toString()
                        + AccountHandler.SEPARATOR + this.dc
                        + AccountHandler.SEPARATOR + this.accountNumber));
        Assert.assertEquals(this.accountHandler1.compareTo(handlerFromRaw), 0);

        final Handler generic = new GenericHandler(handler.toString());
        Assert.assertEquals(this.accountHandler1.compareTo(generic), 0);
    }

    @Test
    public void testCompareToNotOK() throws MalformedHandlerException {
        Handler handler = new AccountHandler(new GenericHandler("1999"),
                this.bank.getID(), this.accountNumber);
        Assert.assertNotSame(handler.compareTo(this.accountHandler1), 0);
        handler = new AccountHandler(this.office.getIdOffice(),
                new GenericHandler("1999"), this.accountNumber);
        Assert.assertNotSame(handler.compareTo(this.accountHandler1), 0);
        handler = new AccountHandler(this.office.getIdOffice(),
                this.bank.getID(), "1234567899");
        Assert.assertNotSame(handler.compareTo(this.accountHandler1), 0);
        final Handler handlerFromRaw = new AccountHandler(new GenericHandler(
                new GenericHandler("9999") + AccountHandler.SEPARATOR
                        + this.bank.getID().toString()
                        + AccountHandler.SEPARATOR + "90"
                        + AccountHandler.SEPARATOR + this.accountNumber));
        Assert.assertNotSame(this.accountHandler1.compareTo(handlerFromRaw), 0);
        final Handler generic = new GenericHandler(handler.toString() + ".");
        Assert.assertNotSame(this.accountHandler1.compareTo(generic), 0);
    }

    @Test
    public void testTostring() {
        final String exp = this.bank.getID().toString()
                + AccountHandler.SEPARATOR
                + this.office.getIdOffice().toString()
                + AccountHandler.SEPARATOR + this.dc + AccountHandler.SEPARATOR
                + this.accountNumber;
        Assert.assertTrue(exp.equals(this.accountHandler1.toString()));
    }
}
