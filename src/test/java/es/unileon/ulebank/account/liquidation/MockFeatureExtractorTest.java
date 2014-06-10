package es.unileon.ulebank.account.liquidation;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class MockFeatureExtractorTest {

    private String feature;
    private String randomFeature;
    private String featureName;
    private Account accountUpdated;
    private Date min;
    private Date max;
    private Office office;
    private Bank bank;
    private Client titular;
    private MockFeatureExtractor<String> mock;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.feature = "nothing";
        this.randomFeature = "nothingrandom";
        this.featureName = "interestingFeature";
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.titular = new Person(74484986, 'S');
        this.accountUpdated = new Account(this.office, this.bank,
                this.office.getNewAccountNumber(), this.titular);
        this.min = new Date(1);
        this.max = new Date(3);
        this.mock = new MockFeatureExtractor<String>(this.feature,
                this.randomFeature, this.featureName);
        this.mock.updateFeature(this.accountUpdated, this.min, this.max);
    }

    @Test
    public void testGetFeature() {
        Assert.assertEquals(this.feature, this.mock.getFeature());
    }

    @Test
    public void testGetRandomFeature() {
        Assert.assertEquals(this.randomFeature,
                this.mock.generateRandomFeature());
    }

    @Test
    public void testGetUpdateAccount() {
        Assert.assertEquals(this.accountUpdated, this.mock.getAccountUpdated());
    }

    @Test
    public void testGetMinDateUpdate() {
        Assert.assertEquals(this.min, this.mock.getMin());
    }

    @Test
    public void testGetMaxDateUpdate() {
        Assert.assertEquals(this.max, this.mock.getMax());
    }

    @Test
    public void testGetFeatureName() {
        Assert.assertEquals(this.featureName, this.mock.getFeatureName());
    }

}
