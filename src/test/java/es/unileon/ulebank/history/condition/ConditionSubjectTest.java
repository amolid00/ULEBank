/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.history.condition;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.ConditionSubject;

/**
 *
 * @author runix
 */
public class ConditionSubjectTest {

    @Test
    public void testSubjectOkOneWord() throws TransactionException {
        final ConditionSubject condition = new ConditionSubject(true, "prueba");
        Assert.assertTrue(condition.test(this
                .getTransaction("kdahsdhasdasdpruebaasdajhsdhasd")));
        Assert.assertTrue(condition.test(this
                .getTransaction("pruebakdahsdhasdasdasdajhsdhasd")));
        Assert.assertTrue(condition.test(this
                .getTransaction("kdahsdhasdasdasdajhsdhasdprueba")));
    }

    @Test
    public void testSubjectNotOkOneWord() throws TransactionException {
        final ConditionSubject condition = new ConditionSubject(true, "prueb2");
        Assert.assertFalse(condition.test(this
                .getTransaction("kdahsdhasdasdpruebaasdajhsdhasd")));
        Assert.assertFalse(condition.test(this
                .getTransaction("pruebakdahsdhasdasdasdajhsdhasd")));
        Assert.assertFalse(condition.test(this
                .getTransaction("kdahsdhasdasdasdajhsdhasdprueba")));
    }

    @Test
    public void testSubjectOkExclusiveOneWord() throws TransactionException {
        final ConditionSubject condition = new ConditionSubject(false,
                "prueba2");
        Assert.assertTrue(condition.test(this
                .getTransaction("kdahsdhasdasdpruebaasdajhsdhasd")));
        Assert.assertTrue(condition.test(this
                .getTransaction("pruebakdahsdhasdasdasdajhsdhasd")));
        Assert.assertTrue(condition.test(this
                .getTransaction("kdahsdhasdasdasdajhsdhasdprueba")));
    }

    @Test
    public void testSubjectNotOkExclusiveOneWord() throws TransactionException {
        final ConditionSubject condition = new ConditionSubject(false, "prueba");
        Assert.assertFalse(condition.test(this
                .getTransaction("kdahsdhasdasdpruebaasdajhsdhasd")));
        Assert.assertFalse(condition.test(this
                .getTransaction("pruebakdahsdhasdasdasdajhsdhasd")));
        Assert.assertFalse(condition.test(this
                .getTransaction("kdahsdhasdasdasdajhsdhasdprueba")));
    }

    public final Transaction getTransaction(String subject)
            throws TransactionException {
        final Transaction t = new GenericTransaction(0, new Date(), subject);
        return t;
    }
}
