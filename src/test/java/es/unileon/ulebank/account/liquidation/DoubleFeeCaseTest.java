package es.unileon.ulebank.account.liquidation;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.account.liquidation.doublefeaturextractors.DoubleFeatureExtractorDirectDebitMaxAmount;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.DirectDebitTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.TransactionHandlerProvider;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class DoubleFeeCaseTest {

    private Features<Double> features;

    private String amountFormula;

    private String subject;

    private Account account;

    private Office office;

    private Bank bank;

    private Client titular;

    private DoubleFeeCase feeCase;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException,
            InvalidCondition, TransactionException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.titular = new Person(74484986, 'S');
        this.account = new Account(this.office, this.bank,
                this.office.getNewAccountNumber(), this.titular);
        this.features = new Features<Double>();
        this.features
                .addFeature(new DoubleFeatureExtractorDirectDebitMaxAmount());
        this.amountFormula = "2*3";
        this.subject = "subject";
        this.feeCase = new DoubleFeeCase(this.features, this.amountFormula,
                this.subject, this.account);
        for (int i = 0; i < 10; i++) {
            this.account.doDirectDebit(this.getTransaction(this.subject, i,
                    new Date()));
        }
    }

    @Test
    public void testTrigger() throws InvalidCondition {
        Assert.assertTrue(this.feeCase.triggerCase());
        this.feeCase.addConditionEquation("1", '<', "2");
        Assert.assertTrue(this.feeCase.triggerCase());
        this.feeCase.addConditionEquation("3", '>', "2");
        Assert.assertTrue(this.feeCase.triggerCase());
    }

    @Test(expected = TransactionException.class)
    public void testCalculateAmountFail() throws TransactionException {
        final DoubleFeeCase cas = new DoubleFeeCase(this.features, "aaa",
                this.subject, this.account);
        cas.calculateAmount();
    }

    @Test
    public void testGetFeatureExtractor() throws InvalidCondition {
        Assert.assertEquals(this.feeCase.getFeatures(), this.features);
    }

    @Test
    public void testTriggerWithVariable() throws InvalidCondition {
        this.feeCase
                .addConditionEquation("pago domiciliado mas alto", '>', "1");
        this.feeCase
                .addConditionEquation("1", '>', "pago domiciliado mas alto");
    }

    @Test(expected = InvalidCondition.class)
    public void testTriggerErrorComparator() throws InvalidCondition {
        this.feeCase.addConditionEquation("1", '!', "1");
    }

    @Test(expected = InvalidCondition.class)
    public void testTriggerErrorLeftOperand() throws InvalidCondition {
        this.feeCase.addConditionEquation("a", '<', "1");
    }

    @Test(expected = InvalidCondition.class)
    public void testTriggerErrorRightOperand() throws InvalidCondition {
        this.feeCase.addConditionEquation("1", '>', "a");
    }

    @Test
    public void testCalculateAmount() throws TransactionException {
        final Transaction t = this.feeCase.calculateAmount();
        Assert.assertNotNull(t);
        Assert.assertEquals(t.getAmount(), 6, Math.pow(10, -6));
        Assert.assertEquals(this.subject, t.getSubject());
    }

    @Test
    public void testCalculateAmountNotTrigger() throws TransactionException,
            InvalidCondition {
        this.feeCase.addConditionEquation("1", '>', "2");
        Assert.assertFalse(this.feeCase.triggerCase());
        final Transaction t = this.feeCase.calculateAmount();
        Assert.assertNull(t);
    }

    public DirectDebitTransaction getTransaction(String subject, double amount,
            Date date) throws TransactionException {
        final DirectDebitTransaction dt = new DirectDebitTransaction(amount,
                date, subject,
                TransactionHandlerProvider.getTransactionHandler());
        return dt;
    }

}
