/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.history;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.exceptions.TransactionException;

/**
 *
 * @author Revellado
 */
public class HistoryTest {

    private History<Transaction> accountHistory;

    @Before
    public void setUp() {

        this.accountHistory = new History<Transaction>();
    }

    /**
     * Test of add method, of class AccountHistory.
     * 
     * @throws TransactionException
     */
    @Test
    public void testAddGenericTransaction() throws TransactionException {
        final Transaction transaction = new GenericTransaction(10.5d,
                new Date(), "Imposicion");
        Assert.assertTrue(this.accountHistory.add(transaction));
        Assert.assertFalse(this.accountHistory.add(transaction));
    }

    /**
     * Test of getTransactions method, of class AccountHistory.
     */
    @Test
    public void testGetTransactions() throws ParseException,
            TransactionException {

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        final Date date1 = sdf.parse("01/01/2014");
        final Date date2 = sdf.parse("01/02/2014");
        final Date date3 = sdf.parse("01/03/2014");

        final Transaction transaction1 = new GenericTransaction(10.5d, date1,
                "Imposicion");
        final Transaction transaction2 = new GenericTransaction(10.5d, date2,
                "Imposicion");
        final Transaction transaction3 = new GenericTransaction(10.5d, date3,
                "Imposicion");
        final List<Transaction> added = new ArrayList<Transaction>();
        added.add(transaction1);
        added.add(transaction2);
        added.add(transaction3);

        Assert.assertTrue(this.accountHistory.add(transaction1));
        Assert.assertFalse(this.accountHistory.add(transaction1));
        Assert.assertTrue(this.accountHistory.add(transaction2));
        Assert.assertFalse(this.accountHistory.add(transaction2));
        Assert.assertTrue(this.accountHistory.add(transaction3));
        Assert.assertFalse(this.accountHistory.add(transaction3));

        final Iterator<Transaction> itHistory = this.accountHistory
                .getIterator();
        final Iterator<Transaction> it = added.iterator();
        while (it.hasNext() && itHistory.hasNext()) {
            Assert.assertEquals(it.next(), itHistory.next());
        }
        Assert.assertFalse(it.hasNext());
        Assert.assertFalse(itHistory.hasNext());
    }

    @Test
    public void testDelete() throws ParseException, TransactionException {

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        final Date date1 = sdf.parse("01/01/2014");
        final Date date2 = sdf.parse("01/02/2014");
        final Date date3 = sdf.parse("01/03/2014");

        final Transaction transaction1 = new GenericTransaction(10.5d, date1,
                "Imposicion");
        final Transaction transaction2 = new GenericTransaction(10.5d, date2,
                "Imposicion");
        final Transaction transaction3 = new GenericTransaction(10.5d, date3,
                "Imposicion");
        final List<Transaction> added = new ArrayList<Transaction>();
        added.add(transaction1);
        added.add(transaction3);

        Assert.assertTrue(this.accountHistory.add(transaction1));
        Assert.assertTrue(this.accountHistory.add(transaction2));
        Assert.assertTrue(this.accountHistory.add(transaction3));
        Assert.assertTrue(this.accountHistory.remove(transaction2.getId()));
        Assert.assertFalse(this.accountHistory.remove(transaction2.getId()));
        final Iterator<Transaction> itHistory = this.accountHistory
                .getIterator();
        final Iterator<Transaction> it = added.iterator();
        while (it.hasNext() && itHistory.hasNext()) {
            Assert.assertEquals(it.next(), itHistory.next());
        }
        Assert.assertFalse(it.hasNext());
        Assert.assertFalse(itHistory.hasNext());
    }

}
