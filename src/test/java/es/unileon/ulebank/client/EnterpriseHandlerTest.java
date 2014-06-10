/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

/**
 *
 * @author Oigrex
 */
public class EnterpriseHandlerTest {

    private EnterpriseHandler enterpriseHandler;
    private String string;

    @Before
    public void setUp() throws MalformedHandlerException {
        this.enterpriseHandler = new EnterpriseHandler('A', 5881850, '1');
    }

    /**
     * Test of compareTo method, of class EnterpriseHandler.
     */
    @Test
    public void testCompareTo() {
        this.string = "A58818501";
        final Handler handler = new GenericHandler(this.string);
        Assert.assertEquals(this.enterpriseHandler.compareTo(handler), 0);
    }

    /**
     * Test of toString method, of class EnterpriseHandler.
     */
    @Test
    public void testToString() {
        Assert.assertEquals(this.enterpriseHandler.toString(), "A58818501");
    }

    @Test
    public void testHandlerCorrecto() throws MalformedHandlerException {
        new Enterprise('A', 5881850, '1');
    }

    @Test(expected = MalformedHandlerException.class)
    public void testNumeroEnLugarLetraCIF() throws MalformedHandlerException {
        new Enterprise('1', 5881850, '1');
    }

    @Test(expected = MalformedHandlerException.class)
    public void testDigitoControlErroneo() throws MalformedHandlerException {
        new Enterprise('A', 5881850, '2');
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLongitudErroneaMenorDigitoControlErroneo()
            throws MalformedHandlerException {
        new Enterprise('A', 588185, '2');
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLongitudErroneaMayorDigitoControlCorrecto()
            throws MalformedHandlerException {
        new Enterprise('A', 58818500, '1');
    }

    @Test(expected = MalformedHandlerException.class)
    public void testLongitudErroneaMayorDigitoControlErroneo()
            throws MalformedHandlerException {
        new Enterprise('A', 58818508, '2');
    }
}
