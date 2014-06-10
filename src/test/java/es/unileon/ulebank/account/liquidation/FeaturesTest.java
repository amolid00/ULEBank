package es.unileon.ulebank.account.liquidation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

public class FeaturesTest {

    private List<String> featureName;
    private Account accountUpdated;
    private Date min;
    private Date max;
    private Office office;
    private Bank bank;
    private Client titular;
    private List<MockFeatureExtractor<String>> mocks;
    private Features<String> features;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.titular = new Person(74484986, 'S');
        this.accountUpdated = new Account(this.office, this.bank,
                this.office.getNewAccountNumber(), this.titular);
        this.min = new Date(1);
        this.max = new Date(3);
        this.featureName = new ArrayList<String>();
        this.mocks = new ArrayList<MockFeatureExtractor<String>>();
        this.featureName.add("mock");
        this.featureName.add("mock1");
        this.featureName.add("mock2");
        this.featureName.add("mock3");
        this.features = new Features<String>();
        for (final String s : this.featureName) {
            final MockFeatureExtractor<String> mock = new MockFeatureExtractor<String>(
                    s, s, s);
            this.features.addFeature(mock);
            this.mocks.add(mock);
        }
    }

    @Test
    public void testGetNames() {
        final String[] names = this.features.getFeaturesNames();
        for (final String s : names) {
            Assert.assertTrue(this.featureName.remove(s));
        }
        Assert.assertEquals(this.featureName.size(), 0);
    }

    @Test
    public void testUpdate() {
        for (final MockFeatureExtractor<String> mock : this.mocks) {
            Assert.assertEquals(mock.getAccountUpdated(), null);
            Assert.assertEquals(mock.getMax(), null);
            Assert.assertEquals(mock.getMin(), null);
        }
        this.features.updateFeatures(this.accountUpdated, this.min, this.max);
        for (final MockFeatureExtractor<String> mock : this.mocks) {
            Assert.assertEquals(mock.getAccountUpdated(), this.accountUpdated);
            Assert.assertEquals(mock.getMax(), this.max);
            Assert.assertEquals(mock.getMin(), this.min);
        }
    }

    @Test
    public void testGetFeatures() {
        this.features.updateFeatures(this.accountUpdated, this.min, this.max);
        final Map<String, String> map = this.features.getFeatures();
        for (final String s : this.featureName) {
            Assert.assertTrue(map.containsKey(s));
            Assert.assertTrue(map.containsValue(s));
        }
    }

    @Test
    public void testGetRandomFeatures() {
        this.features.updateFeatures(this.accountUpdated, this.min, this.max);
        final Map<String, String> map = this.features.generateRandomFeatures();
        for (final String s : this.featureName) {
            Assert.assertTrue(map.containsKey(s));
            Assert.assertTrue(map.containsValue(s));
        }
    }
}
