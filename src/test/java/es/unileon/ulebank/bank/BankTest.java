/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.bank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

/**
 *
 * @author Revellado
 */
public class BankTest {

    private Bank bank;
    private Office office;
    private Office office2;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.office2 = new Office(new GenericHandler("1235"), this.bank);
        this.bank.addOffice(this.office);
        this.bank.addOffice(this.office2);
    }

    /**
     * Test of getID method, of class Bank.
     */
    @Test
    public void testGetID() {

        final Handler expResult = new GenericHandler("1234");
        Assert.assertNotNull(this.bank.getID());
        Assert.assertEquals(this.bank.getID().compareTo(expResult), 0);
    }

    /**
     * Test of addOffice method, of class Bank.
     * 
     * @throws WrongArgsException
     */
    @Test
    public void testAddOffice() throws WrongArgsException {

        Assert.assertTrue(this.bank.addOffice(new Office(new GenericHandler(
                "2234"), this.bank)));
    }

    /**
     * Test of addOffice method, of class Bank.
     */
    @Test
    public void testAddOfficeNullOffice() {

        Assert.assertFalse(this.bank.addOffice(null));
    }

    /**
     * Test of addOffice method, of class Bank.
     */
    @Test
    public void testAddOfficeSameOffice() {

        this.bank.addOffice(this.office);

        Assert.assertFalse(this.bank.addOffice(this.office));
    }

    /**
     * Test of removeOffice method, of class Bank.
     */
    @Test
    public void testRemoveOffice() {

        this.bank.addOffice(this.office);
        Assert.assertTrue(this.bank.removeOffice(this.office.getIdOffice()));
    }

    /**
     * Test of removeOffice method, of class Bank.
     */
    @Test
    public void testRemoveOfficeNullOffice() {

        Assert.assertFalse(this.bank.removeOffice(null));
    }

    /**
     * Test of removeOffice method, of class Bank.
     */
    @Test
    public void testRemoveOfficeNotBelongsTheBank() {

        Assert.assertFalse(this.bank.removeOffice(new GenericHandler("6666")));
    }

    @Test
    public void testSearchOffice() {
        Assert.assertEquals(this.office2,
                this.bank.searchOffice(this.office2.getIdOffice()));
        Assert.assertEquals(this.office,
                this.bank.searchOffice(this.office.getIdOffice()));
        Assert.assertEquals(null,
                this.bank.searchOffice(new GenericHandler("9292")));
    }

}
