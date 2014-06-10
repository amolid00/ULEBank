package es.unileon.ulebank.account;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.handler.GenericHandler;

public class AccountDirectDebitsTest {

    private List<DirectDebit> debits;
    private AccountDirectDebits accountDebits;

    @Before
    public void setup() {
        this.debits = new ArrayList<DirectDebit>();
        this.accountDebits = new AccountDirectDebits();
        for (int i = 0; i < 10; i++) {
            final DirectDebit dd = new DirectDebit(new GenericHandler(
                    "debit-> " + i));
            Assert.assertTrue(this.debits.add(dd));
            Assert.assertTrue(this.accountDebits.addDirectDebit(dd));
        }
    }

    @Test
    public void addDuplicated() {
        for (int i = 0; i < this.debits.size(); i++) {
            Assert.assertFalse(this.accountDebits.addDirectDebit(this.debits
                    .get(i)));
        }
    }

    @Test
    public void remove() {
        Assert.assertFalse(this.accountDebits
                .removeDirectDebit(new GenericHandler("nofound")));
        for (int i = 0; i < this.debits.size(); i++) {
            Assert.assertTrue(this.accountDebits.removeDirectDebit(this.debits
                    .get(i).getDirectDebitID()));
        }
        Assert.assertFalse(this.accountDebits
                .removeDirectDebit(new GenericHandler("nofound")));

    }

}
