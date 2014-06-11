/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.history.iterator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.DetailedInformation;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.TransactionHandlerProvider;

/**
 *
 * @author runix
 */
public class IteratorSubjectTest {

    private Iterator<Transaction> iteratorFromList;
    private Iterator<Transaction> iteratorFromIterator;
    private Iterator<Transaction> iteratorFromListNegate;
    private Iterator<Transaction> iteratorFromIteratorNegate;
    private List<Transaction> elements;
    private List<Transaction> correctElements;
    private List<Transaction> invalidElements;
    private String word;

    @Before
    public void setup() throws TransactionException {
        this.elements = new ArrayList<Transaction>();
        this.correctElements = new ArrayList<Transaction>();
        this.invalidElements = new ArrayList<Transaction>();
        this.word = "this is a test";
        for (int i = 0; i < 10; i++) {
            this.invalidElements.add(this.getTransaction("handler->"
                    + TransactionHandlerProvider.getTransactionHandler()
                            .toString()));
        }

        for (int i = 10; i < 10; i++) {
            this.correctElements.add(this
                    .getTransaction(TransactionHandlerProvider
                            .getTransactionHandler().toString()
                            + this.word
                            + "ajsdas"));
        }
        this.elements.addAll(this.invalidElements);
        this.elements.addAll(this.correctElements);
        this.iteratorFromList = new IteratorSubject<Transaction>(this.elements,
                this.word, true);
        this.iteratorFromListNegate = new IteratorSubject<Transaction>(
                this.elements, this.word, false);

        this.iteratorFromIterator = new IteratorSubject<Transaction>(
                this.elements.iterator(), this.word, true);
        this.iteratorFromIteratorNegate = new IteratorSubject<Transaction>(
                this.elements.iterator(), this.word, false);
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
        Assert.assertEquals(this.iteratorFromIterator.next(), null);
        Assert.assertEquals(this.iteratorFromList.next(), null);
    }

    @Test
    public void testNoValidElements() {
        this.elements.removeAll(this.correctElements);
        this.iteratorFromIterator = new IteratorSubject<Transaction>(
                this.elements.iterator(), this.word, true);
        this.iteratorFromList = new IteratorSubject<Transaction>(this.elements,
                this.word, true);
        Assert.assertFalse(this.iteratorFromIterator.hasNext());
        Assert.assertFalse(this.iteratorFromList.hasNext());
        Assert.assertEquals(this.iteratorFromIterator.next(), null);
        Assert.assertEquals(this.iteratorFromList.next(), null);

        this.elements.clear();
        this.elements.addAll(this.correctElements);
        this.iteratorFromIteratorNegate = new IteratorSubject<Transaction>(
                this.elements.iterator(), this.word, false);
        this.iteratorFromListNegate = new IteratorSubject<Transaction>(
                this.elements, this.word, false);
        Assert.assertFalse(this.iteratorFromIteratorNegate.hasNext());
        Assert.assertFalse(this.iteratorFromListNegate.hasNext());
        Assert.assertEquals(this.iteratorFromIteratorNegate.next(), null);
        Assert.assertEquals(this.iteratorFromListNegate.next(), null);
    }

    @Test
    public void testNoElements() {
        this.elements.clear();
        this.iteratorFromIteratorNegate = new IteratorSubject<Transaction>(
                this.elements.iterator(), this.word, false);
        this.iteratorFromListNegate = new IteratorSubject<Transaction>(
                this.elements, this.word, false);
        this.iteratorFromIterator = new IteratorSubject<Transaction>(
                this.elements.iterator(), this.word, true);
        this.iteratorFromList = new IteratorSubject<Transaction>(this.elements,
                this.word, true);
        Assert.assertFalse(this.iteratorFromIterator.hasNext());
        Assert.assertFalse(this.iteratorFromList.hasNext());
        Assert.assertEquals(this.iteratorFromIterator.next(), null);
        Assert.assertEquals(this.iteratorFromList.next(), null);
        Assert.assertFalse(this.iteratorFromIteratorNegate.hasNext());
        Assert.assertFalse(this.iteratorFromListNegate.hasNext());
        Assert.assertEquals(this.iteratorFromIteratorNegate.next(), null);
        Assert.assertEquals(this.iteratorFromListNegate.next(), null);
    }

    public Transaction getTransaction(String subject)
            throws TransactionException {
        return new GenericTransaction(0, new Date(), subject,
                new DetailedInformation());
    }
}
