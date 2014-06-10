package es.unileon.ulebank.client;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.handler.MalformedHandlerException;

public class PersonTest {

    private Person dni;
    private Person foreign;

    @Before
    public void setUp() throws MalformedHandlerException {
        this.dni = new Person(30143115, 'M');
        this.foreign = new Person('X', 6078827, 'L');
    }

    @Test(expected = MalformedHandlerException.class)
    public void createPersonWrongDNI() throws MalformedHandlerException {
        new Person(30143115, 'A');
    }

    @Test(expected = MalformedHandlerException.class)
    public void createPersonWrongNIE() throws MalformedHandlerException {
        new Person('U', 6078827, 'L');
    }

    @Test
    public void testGetAndSetAddress() {
        final Address address = new Address("", 2, 2, '2', "LE", "LE", 24007);
        this.dni.setAddress(address);
        this.foreign.setAddress(address);
        Assert.assertEquals(this.dni.getAddress(), address);
        Assert.assertEquals(this.foreign.getAddress(), address);
    }

    @Test
    public void testSetAndGetBirthDate() {
        final Date date = new Date();
        this.dni.setBirthDate(date);
        Assert.assertEquals(this.dni.getBirthDate(), date);
    }

    @Test
    public void testSetAndGetProfession() {
        final String profession = "nini";
        this.dni.setProfession(profession);
        Assert.assertEquals(this.dni.getProfession(), profession);
    }

    @Test
    public void testSetAndGetCivilState() {
        final String profession = "informatico";
        this.dni.setCivilState(profession);
        Assert.assertEquals(this.dni.getCivilState(), profession);
    }

    @Test
    public void testSetAndGetPhoneNumber() {
        int number1, number2;
        number1 = number2 = 0;
        this.dni.replacePhoneNumber(0, number1);
        this.dni.replacePhoneNumber(1, number2);
        this.dni.replacePhoneNumber(-1, number1);
        this.dni.replacePhoneNumber(3, number2);
        Assert.assertEquals(this.dni.getPhoneNumber(0), number1);
        Assert.assertEquals(this.dni.getPhoneNumber(1), number2);

        Assert.assertEquals(this.dni.getPhoneNumber(-1), 0);
        Assert.assertEquals(this.dni.getPhoneNumber(4), 0);
    }
}
