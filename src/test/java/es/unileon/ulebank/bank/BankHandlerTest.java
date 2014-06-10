package es.unileon.ulebank.bank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

public class BankHandlerTest {

    private String number;
    private Handler bankHandler;

    @Before
    public void setUp() throws Exception {
        this.number = "1234";
        this.bankHandler = new BankHandler(this.number);
    }

    @Test
    public void testToString() {
        Assert.assertEquals(this.bankHandler.toString().compareTo(this.number),
                0);
    }

    @Test
    public void testCompareTo() {
        Handler generic = new GenericHandler(this.number);
        Assert.assertEquals(this.bankHandler.compareTo(generic), 0);
        generic = new GenericHandler("4321");
        Assert.assertNotSame(this.bankHandler.compareTo(generic), 0);
    }

    @Test(expected = MalformedHandlerException.class)
    public void testConstructorWithExtraNumbers()
            throws MalformedHandlerException {
        new BankHandler("12345");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testConstructorWithLessNumbers()
            throws MalformedHandlerException {
        new BankHandler("125");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testConstructorWithExtraNumbersAndLetter()
            throws MalformedHandlerException {
        new BankHandler("a2345");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testConstructorWithLessNumbersAndLetter()
            throws MalformedHandlerException {
        new BankHandler("1a5");
    }

    @Test(expected = MalformedHandlerException.class)
    public void testConstructorWithLetter() throws MalformedHandlerException {
        new BankHandler("1a25");
    }
}
