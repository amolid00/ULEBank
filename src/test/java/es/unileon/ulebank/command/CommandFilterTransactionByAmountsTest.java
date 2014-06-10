/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.DetailedInformation;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.history.iterator.IteratorBetweenTwoAmounts;

/**
 *
 * @author runix
 */
public class CommandFilterTransactionByAmountsTest {
    private Iterator<Transaction> iteratorFromList;
    private Iterator<Transaction> iteratorFromIterator;
    private List<Transaction> elements;
    private List<Transaction> correctElements;
    private CommandFilterTransactionByAmounts command;
    private CommandFilterTransactionByAmounts commandFromIterator;
    private double min;
    private double max;

    @Before
    public void setup() throws WrongArgsException, TransactionException {
        this.min = 100;
        this.max = 2000;
        this.elements = new ArrayList<Transaction>();
        this.correctElements = new ArrayList<Transaction>();
        for (int i = 1; i < 10; i++) {
            this.elements.add(this.getTransaction(this.max + (10 * i)));
            this.elements.add(this.getTransaction(i));
        }
        double actual = this.min;
        while (actual < this.max) {
            final Transaction t = this.getTransaction(actual);
            this.correctElements.add(t);
            this.elements.add(t);
            actual += 100;
        }

        this.command = new CommandFilterTransactionByAmounts(this.min,
                this.max, this.elements, new GenericHandler(""));
        this.commandFromIterator = new CommandFilterTransactionByAmounts(
                this.min, this.max, this.elements.iterator(),
                new GenericHandler(""));
        this.command.execute();
        this.commandFromIterator.execute();

        this.iteratorFromIterator = this.commandFromIterator.getList()
                .iterator();
        this.iteratorFromList = this.command.getList().iterator();
    }

    @Test
    public void testNoElements() throws WrongArgsException {
        this.elements.clear();
        this.command = new CommandFilterTransactionByAmounts(this.min,
                this.max, this.elements, new GenericHandler(""));
        this.commandFromIterator = new CommandFilterTransactionByAmounts(
                this.min, this.max, this.elements.iterator(),
                new GenericHandler(""));
        this.command.execute();
        this.commandFromIterator.execute();

        this.iteratorFromIterator = this.commandFromIterator.getList()
                .iterator();
        this.iteratorFromList = this.command.getList().iterator();
        Assert.assertFalse(this.iteratorFromIterator.hasNext());
        Assert.assertFalse(this.iteratorFromList.hasNext());
    }

    @Test
    public void testNoValidElements() throws WrongArgsException {
        this.elements.removeAll(this.correctElements);
        this.command = new CommandFilterTransactionByAmounts(this.min,
                this.max, this.elements, new GenericHandler(""));
        this.commandFromIterator = new CommandFilterTransactionByAmounts(
                this.min, this.max, this.elements.iterator(),
                new GenericHandler(""));
        this.command.execute();
        this.commandFromIterator.execute();

        this.iteratorFromIterator = this.commandFromIterator.getList()
                .iterator();
        this.iteratorFromList = this.command.getList().iterator();
        Assert.assertFalse(this.iteratorFromIterator.hasNext());
        Assert.assertFalse(this.iteratorFromList.hasNext());
    }

    @Test(expected = WrongArgsException.class)
    public void testWrongArgumentsFromList() throws WrongArgsException {
        new IteratorBetweenTwoAmounts<Transaction>(this.elements, 100, 0);
    }

    @Test(expected = WrongArgsException.class)
    public void testWrongArgumentsFromIterator() throws WrongArgsException {
        new IteratorBetweenTwoAmounts<Transaction>(this.elements.iterator(),
                100, 0);
    }

    @Test
    public void testWithElements() {
        final Iterator<Transaction> it = this.correctElements.iterator();
        while (this.iteratorFromIterator.hasNext()
                && this.iteratorFromList.hasNext() && it.hasNext()) {
            Transaction nextFromIterator, nextFromList, correct;
            nextFromIterator = this.iteratorFromIterator.next();
            nextFromList = this.iteratorFromList.next();
            correct = it.next();
            Assert.assertEquals(nextFromIterator, correct);
            Assert.assertEquals(nextFromList, correct);
        }
        Assert.assertFalse(it.hasNext());
        Assert.assertFalse(this.iteratorFromIterator.hasNext());
        Assert.assertFalse(this.iteratorFromList.hasNext());
    }

    public Transaction getTransaction(double amount)
            throws TransactionException {
        return new GenericTransaction(amount, new Date(), "subject",
                new DetailedInformation());
    }
}
