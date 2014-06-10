/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Oigrex
 */
public class AddressTest {

    Address address;

    @Before
    public void setUp() {
        this.address = new Address("Calle de Peter", 314159, 10, 'A', "Le�n",
                "Le�n", 24001);
    }

    /**
     * Test of getStreet method, of class Address.
     */
    @Test
    public void testGetStreet() {
        Assert.assertNotNull(this.address.getStreet());
        Assert.assertEquals(this.address.getStreet(), "Calle de Peter");
    }

    /**
     * Test of setStreet method, of class Address.
     */
    @Test
    public void testSetStreet() {
        Assert.assertEquals(this.address.getStreet(), "Calle de Peter");
        this.address.setStreet("Calle de Juan");
        Assert.assertEquals(this.address.getStreet(), "Calle de Juan");
    }

    /**
     * Test of getBlockNumber method, of class Address.
     */
    @Test
    public void testGetBlockNumber() {
        Assert.assertNotNull(this.address.getBlockNumber());
        Assert.assertEquals(this.address.getBlockNumber(), 314159);
    }

    /**
     * Test of setBlockNumber method, of class Address.
     */
    @Test
    public void testSetBlockNumber() {
        Assert.assertEquals(this.address.getBlockNumber(), 314159);
        this.address.setBlockNumber(4);
        Assert.assertEquals(this.address.getBlockNumber(), 4);
    }

    /**
     * Test of getFloor method, of class Address.
     */
    @Test
    public void testGetFloor() {
        Assert.assertNotNull(this.address.getFloor());
        Assert.assertEquals(this.address.getFloor(), 10);
    }

    /**
     * Test of setFloor method, of class Address.
     */
    @Test
    public void testSetFloor() {
        Assert.assertEquals(this.address.getFloor(), 10);
        this.address.setFloor(11);
        Assert.assertEquals(this.address.getFloor(), 11);
    }

    /**
     * Test of getDoor method, of class Address.
     */
    @Test
    public void testGetDoor() {
        Assert.assertNotNull(this.address.getDoor());
        Assert.assertEquals(this.address.getDoor(), 'A');
    }

    /**
     * Test of setDoor method, of class Address.
     */
    @Test
    public void testSetDoor() {
        Assert.assertEquals(this.address.getDoor(), 'A');
        this.address.setDoor('B');
        Assert.assertEquals(this.address.getDoor(), 'B');
    }

    /**
     * Test of getLocality method, of class Address.
     */
    @Test
    public void testGetLocality() {
        Assert.assertNotNull(this.address.getLocality());
        Assert.assertEquals(this.address.getLocality(), "Le�n");
    }

    /**
     * Test of setLocality method, of class Address.
     */
    @Test
    public void testSetLocality() {
        Assert.assertEquals(this.address.getLocality(), "Le�n");
        this.address.setLocality("Madrid");
        Assert.assertEquals(this.address.getLocality(), "Madrid");
    }

    /**
     * Test of getProvince method, of class Address.
     */
    @Test
    public void testGetProvince() {
        Assert.assertNotNull(this.address.getProvince());
        Assert.assertEquals(this.address.getProvince(), "Le�n");
    }

    /**
     * Test of setProvince method, of class Address.
     */
    @Test
    public void testSetProvince() {
        Assert.assertEquals(this.address.getProvince(), "Le�n");
        this.address.setProvince("Madrid");
        Assert.assertEquals(this.address.getProvince(), "Madrid");
    }

    /**
     * Test of getZipCode method, of class Address.
     */
    @Test
    public void testGetZipCode() {
        Assert.assertNotNull(this.address.getZipCode());
        Assert.assertEquals(this.address.getZipCode(), 24001);
    }

    /**
     * Test of setZipCode method, of class Address.
     */
    @Test
    public void testSetZipCode() {
        Assert.assertEquals(this.address.getZipCode(), 24001);
        this.address.setZipCode(24002);
        Assert.assertEquals(this.address.getZipCode(), 24002);
    }

}
