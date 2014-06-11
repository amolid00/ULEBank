/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.history.condition;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.ConditionTransactionBetweenTwoDates;

/**
 *
 * @author runix
 */
public class ConditionalTransactionBetweenTwoDays {

    private static final long DAY_TIMESTAMP = 1000 * 24 * 60 * 60;

    private ConditionTransactionBetweenTwoDates<Transaction> conditionBetween;

    private Date min;
    private Date max;

    @Before
    public void setup() throws Exception {
        this.min = new Date(
                (ConditionalTransactionBetweenTwoDays.DAY_TIMESTAMP * 10)
                        - (ConditionalTransactionBetweenTwoDays.DAY_TIMESTAMP / 2));
        this.max = new Date(
                (ConditionalTransactionBetweenTwoDays.DAY_TIMESTAMP * 20)
                        + (ConditionalTransactionBetweenTwoDays.DAY_TIMESTAMP / 2));
        this.conditionBetween = new ConditionTransactionBetweenTwoDates<Transaction>(
                this.min, this.max);
    }

    @Test
    public void testConditionBetweenOK() throws TransactionException {
        Assert.assertTrue(this.conditionBetween.test(this
                .getTransaction(new Date(this.min.getTime()
                        + (this.max.getTime() - this.min.getTime())))));
        Assert.assertTrue(this.conditionBetween.test(this
                .getTransaction(new Date(this.min.getTime()))));
        Assert.assertTrue(this.conditionBetween.test(this
                .getTransaction(new Date(this.max.getTime()))));

    }

    @Test
    public void testConditionBetweenNoOK() throws TransactionException {
        Assert.assertFalse(this.conditionBetween.test(this
                .getTransaction(new Date(this.min.getTime() - 1))));
        Assert.assertFalse(this.conditionBetween.test(this
                .getTransaction(new Date(this.max.getTime() + 1))));

    }

    public final Transaction getTransaction(Date date)
            throws TransactionException {
        final Transaction t = new GenericTransaction(0, date, "subject");
        t.setEffectiveDate(date);
        return t;
    }
}
