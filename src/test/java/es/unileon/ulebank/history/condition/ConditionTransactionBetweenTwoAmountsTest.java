/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.history.condition;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.ConditionTransactionBetweenTwoAmounts;
import es.unileon.ulebank.history.conditions.WrongArgsException;

/**
 *
 * @author runix
 */
public class ConditionTransactionBetweenTwoAmountsTest {

    @Test
    public void testBetweenOk() throws WrongArgsException, TransactionException {
        ConditionTransactionBetweenTwoAmounts condition = new ConditionTransactionBetweenTwoAmounts<Transaction>(
                100, 100);
        Assert.assertTrue(condition.test(this.getTransaction(100)));
        condition = new ConditionTransactionBetweenTwoAmounts<Transaction>(100,
                125);
        Assert.assertTrue(condition.test(this.getTransaction(100)));
        Assert.assertTrue(condition.test(this.getTransaction(125)));
        Assert.assertTrue(condition.test(this.getTransaction(115)));

    }

    @Test
    public void testBetweenNotOk() throws WrongArgsException,
            TransactionException {
        ConditionTransactionBetweenTwoAmounts condition = new ConditionTransactionBetweenTwoAmounts<Transaction>(
                100, 100);
        Assert.assertFalse(condition.test(this.getTransaction(101)));
        condition = new ConditionTransactionBetweenTwoAmounts<Transaction>(100,
                125);
        Assert.assertFalse(condition.test(this.getTransaction(99)));
        Assert.assertFalse(condition.test(this.getTransaction(126)));

    }

    public final Transaction getTransaction(double amount)
            throws TransactionException {
        final Transaction t = new GenericTransaction(amount, new Date(),
                "subject");
        return t;
    }
}
