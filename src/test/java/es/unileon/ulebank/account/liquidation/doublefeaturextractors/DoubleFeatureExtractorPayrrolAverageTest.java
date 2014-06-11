package es.unileon.ulebank.account.liquidation.doublefeaturextractors;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.account.liquidation.AbstractFeatureExtractor;
import es.unileon.ulebank.account.liquidation.InvalidCondition;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.DirectDebitTransaction;
import es.unileon.ulebank.history.TransactionHandlerProvider;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class DoubleFeatureExtractorPayrrolAverageTest {

    private String subject;

    private Account account;

    private Office office;

    private Bank bank;

    private Client titular;

    private AbstractFeatureExtractor<Double> extractor;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException,
            InvalidCondition, TransactionException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.titular = new Person(74484986, 'S');
        this.account = new Account(this.office, this.bank,
                this.office.getNewAccountNumber(), this.titular);
        this.account.setMaxOverdraft(10000);
        this.subject = "subject";
        for (int i = 0; i < 10; i++) {
            this.account.doDirectDebit(this.getTransaction(this.subject,
                    (i % 2) == 0 ? -i : i, new Date(i)));
        }
        this.extractor = new DoubleFeatureExtractorPayrrolAverage();
        Assert.assertEquals(this.extractor.getFeature(), 0.0, Math.pow(10, -5));
        this.extractor.generateRandomFeature();
        this.extractor.updateFeature(this.account, new Date(2), new Date(8));
    }

    @Test
    public void testUpdateWrongArgs() {
        this.extractor.updateFeature(this.account, new Date(8), new Date(2));
        Assert.assertEquals(this.extractor.getFeature(), Double.NaN,
                Math.pow(10, -5));
    }

    @Test
    public void testGetFeatureName() {
        Assert.assertEquals(this.extractor.getFeatureName(),
                "Importe medio nominas");
    }

    @Test
    public void testGetFeature() {
        Assert.assertEquals(this.extractor.getFeature(), 5.0, Math.pow(10, -5));
    }

    public DirectDebitTransaction getTransaction(String subject, double amount,
            Date date) throws TransactionException {
        final DirectDebitTransaction dt = new DirectDebitTransaction(amount,
                date, subject,
                TransactionHandlerProvider.getTransactionHandler());
        dt.setEffectiveDate(date);
        return dt;
    }

}
