package es.unileon.ulebank.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CifControlTest {

    private CifControl cifControl;

    @Before
    public void setUp() throws Exception {

        this.cifControl = CifControl.instance();
    }

    @Test
    public void testIsCifValid() {
        Assert.assertTrue(this.cifControl.isCifValid('Q', 65, 82297, 'E'));
        Assert.assertFalse(this.cifControl.isCifValid('H', 23, 54333, 'X'));
    }

}
