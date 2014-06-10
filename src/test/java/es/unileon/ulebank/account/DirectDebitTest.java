package es.unileon.ulebank.account;

import org.junit.Assert;
import org.junit.Test;

import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;

public class DirectDebitTest {

    @Test
    public void testCreateDirectDebit() {
        final Handler g = new GenericHandler("test");
        final DirectDebit debit = new DirectDebit(g);
        Assert.assertEquals(debit.getDirectDebitID(), g);
    }

}
