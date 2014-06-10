/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.handler.MalformedHandlerException;

/**
 *
 * @author Oigrex
 */
public class EnterpriseTest {

    private Enterprise enterprise, enterprise1;
    private Address address, address1;
    private Person persona;
    private PersonHandler personHandler;

    @Before
    public void setUp() throws MalformedHandlerException {
        this.enterprise = new Enterprise('A', 5881850, '1');
        this.address = new Address("Gran V�a", 11, 2, 'B', "Madrid", "Madrid",
                28013);
        this.address1 = new Address("Calle Spooner", 10, 1, 'A', "Quahog",
                "Rhode Island", 12345);
        this.enterprise1 = new Enterprise('A', 5881850, '1',
                "Universidad de Le�n", this.address);
        this.persona = new Person(84431140, 'A');
        this.personHandler = new PersonHandler(84431140, 'A');
    }

    /**
     * Test of addAuthorizedPerson method, of class Enterprise.
     */
    @Test
    public void testAddAuthorizedPerson() {
        this.enterprise.addAuthorizedPerson(this.persona);
        Assert.assertTrue(this.enterprise
                .existsAuthorizedPerson(this.personHandler));
        this.enterprise.addAuthorizedPerson(this.persona);
    }

    /**
     * Test of removeAuthorizedPerson method, of class Enterprise.
     */
    @Test
    public void testRemoveAuthorizedPerson() {
        this.enterprise.addAuthorizedPerson(this.persona);
        Assert.assertTrue(this.enterprise
                .existsAuthorizedPerson(this.personHandler));
        this.enterprise.removeAuthorizedPerson(this.personHandler);
        Assert.assertFalse(this.enterprise
                .existsAuthorizedPerson(this.personHandler));
        this.enterprise.removeAuthorizedPerson(this.personHandler);
    }

    /**
     * Test of existsAuthorizedPerson method, of class Enterprise.
     */
    @Test
    public void testExistsAuthorizedPerson() {
        Assert.assertFalse(this.enterprise
                .existsAuthorizedPerson(this.personHandler));
        this.enterprise.addAuthorizedPerson(this.persona);
        Assert.assertTrue(this.enterprise
                .existsAuthorizedPerson(this.personHandler));
        this.enterprise.addAuthorizedPerson(this.persona);
    }

    /**
     * Test of getEnterpriseName method, of class Enterprise.
     */
    @Test
    public void testGetEnterpriseName() {
        Assert.assertNotNull(this.enterprise1.getEnterpriseName());
        Assert.assertEquals(this.enterprise1.getEnterpriseName(),
                "Universidad de Le�n");
    }

    /**
     * Test of setEnterpriseName method, of class Enterprise.
     */
    @Test
    public void testSetEnterpriseName() {
        Assert.assertEquals(this.enterprise1.getEnterpriseName(),
                "Universidad de Le�n");
        this.enterprise1.setEnterpriseName("Oigrex Company");
        Assert.assertEquals(this.enterprise1.getEnterpriseName(),
                "Oigrex Company");
    }

    /**
     * Test of getAddress method, of class Enterprise.
     */
    @Test
    public void testGetAddress() {
        Assert.assertNotNull(this.enterprise1.getAddress());
        Assert.assertEquals(this.enterprise1.getAddress(), this.address);
    }

    /**
     * Test of setAddress method, of class Enterprise.
     */
    @Test
    public void testSetAddress() {
        Assert.assertEquals(this.enterprise1.getAddress(), this.address);
        this.enterprise1.setAddress(this.address1);
        Assert.assertEquals(this.enterprise1.getAddress(), this.address1);
    }

}
