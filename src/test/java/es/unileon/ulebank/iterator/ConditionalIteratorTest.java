package es.unileon.ulebank.iterator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.TransactionHandlerProvider;
import es.unileon.ulebank.history.conditions.ConditionSubject;
import es.unileon.ulebank.history.conditions.ConditionTransactionBetweenTwoDates;
import es.unileon.ulebank.history.conditions.WrongArgsException;

public class ConditionalIteratorTest {

    private List<Transaction> valid;
    private List<Transaction> inValid;
    private ConditionalIterator<Transaction> it;
    private ConditionalIterator<Transaction> itNoConditions;
    private String wordValid;
    private Date min;
    private Date max;

    @Before
    public void setUp() throws TransactionException, WrongArgsException {
        this.valid = new ArrayList<Transaction>();
        this.inValid = new ArrayList<Transaction>();
        this.min = new Date(System.currentTimeMillis());
        this.max = new Date(System.currentTimeMillis() + 100000);
        this.wordValid = "test";
        for (int i = 0; i < 10; i++) {
            this.valid.add(this.getTransaction(this.wordValid
                    + TransactionHandlerProvider.getTransactionHandler(),
                    new Date(this.min.getTime() + i)));
        }

        for (int i = 0; i < 10; i++) {
            this.inValid.add(this.getTransaction("adasd"
                    + TransactionHandlerProvider.getTransactionHandler(),
                    new Date(this.max.getTime() + i)));
        }
        final List<Condition<Transaction>> conditions = new ArrayList<Condition<Transaction>>();
        conditions.add(new ConditionTransactionBetweenTwoDates<Transaction>(
                this.min, this.max));
        conditions.add(new ConditionSubject<Transaction>(true, this.wordValid));
        final List<Transaction> all = new ArrayList<Transaction>();
        all.addAll(this.valid);
        all.addAll(this.inValid);
        this.it = new ConditionalIterator<Transaction>(conditions,
                all.iterator());
        this.itNoConditions = new ConditionalIterator<Transaction>(this.valid);
    }

    @Test
    public void testEquals() {
        while (this.it.hasNext() && this.itNoConditions.hasNext()) {
            Assert.assertTrue(this.valid.contains(this.it.next()));
            Assert.assertTrue(this.valid.contains(this.itNoConditions.next()));
        }

        Assert.assertFalse(this.it.hasNext());
        Assert.assertFalse(this.itNoConditions.hasNext());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        this.it.remove();
    }

    private Transaction getTransaction(String subject, Date date)
            throws TransactionException {
        final Transaction t = new GenericTransaction(10, date, subject);
        t.setEffectiveDate(date);
        return t;
    }

}
