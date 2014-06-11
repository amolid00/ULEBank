package es.unileon.ulebank.account.liquidation;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.account.liquidation.doublefeaturextractors.DoubleFeatureExtractorDirectDebitMaxAmount;
import es.unileon.ulebank.account.liquidation.doublefeaturextractors.DoubleFeatureExtractorDirectDebitsAverage;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.DirectDebitTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.TransactionHandlerProvider;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class GenericLiquidationFeeTest {

    private Features<Double> features;

    private String subject;

    private Account account;

    private Office office;

    private Bank bank;

    private Client titular;

    private DoubleFeeCase feeCase;

    private DoubleFeeCase feeCase2;

    private DoubleFeeCase feeCase3;

    private String amountFormula;

    private String amountFormula2;

    private String amountFormula3;

    private GenericLiquidationFee<Double> liquidationFee;

    private Handler id;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException,
            InvalidCondition, TransactionException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.titular = new Person(74484986, 'S');
        this.account = new Account(this.office, this.bank,
                this.office.getNewAccountNumber(), this.titular);
        this.features = new Features<Double>();
        this.amountFormula = "2*3";
        this.amountFormula2 = "3*3+1";
        this.amountFormula3 = "3 * 3 * 3 + 1";
        this.subject = "subject";
        Assert.assertTrue(this.features
                .addFeature(new DoubleFeatureExtractorDirectDebitMaxAmount()));
        Assert.assertTrue(this.features
                .addFeature(new DoubleFeatureExtractorDirectDebitsAverage()));
        this.feeCase = new DoubleFeeCase(this.features, this.amountFormula,
                this.subject, this.account);
        this.feeCase
                .addConditionEquation("pago domiciliado mas alto", '>', "4");

        this.feeCase2 = new DoubleFeeCase(this.features, this.amountFormula2,
                this.subject, this.account);
        this.feeCase2.addConditionEquation("pago domiciliado mas alto", '<',
                "4");

        this.feeCase3 = new DoubleFeeCase(this.features, this.amountFormula3,
                this.subject, this.account);
        this.feeCase3.addConditionEquation("importe medio pagos domiciliados",
                '<', "2");
        for (int i = 0; i < 10; i++) {
            this.account.doDirectDebit(this.getTransaction(this.subject, i,
                    new Date(i)));
        }
        this.id = new GenericHandler("basicLiquidationFee");
        this.liquidationFee = new GenericLiquidationFee<Double>(this.account,
                this.id, this.features);
        Assert.assertTrue(this.liquidationFee.addFeeCase(this.feeCase));
        Assert.assertTrue(this.liquidationFee.addFeeCase(this.feeCase2));
        Assert.assertTrue(this.liquidationFee.addFeeCase(this.feeCase3));
        Assert.assertFalse(this.liquidationFee.addFeeCase(new DoubleFeeCase(
                new Features<Double>(), this.amountFormula, this.subject,
                this.account)));
    }

    @Test
    public void testGetId() {
        Assert.assertEquals(this.id.compareTo(this.liquidationFee.getId()), 0);
    }

    @Test
    public void testCalculateFee() throws TransactionException {
        final Transaction t = this.liquidationFee.calculateFee(new Date(0),
                new Date(10));
        Assert.assertEquals(t.getAmount(), 10.0, Math.pow(10, -5));
        Assert.assertEquals(t.getSubject(), this.subject);
    }

    @Test
    public void testCalculateFeeNotFound() throws TransactionException,
            InvalidCondition {
        this.feeCase = new DoubleFeeCase(this.features, this.amountFormula,
                this.subject, this.account);
        this.feeCase.addConditionEquation("1", '>', "4");
        Assert.assertEquals(this.id.compareTo(this.liquidationFee.getId()), 0);
        this.liquidationFee = new GenericLiquidationFee<Double>(this.account,
                this.id, this.features);
        Assert.assertTrue(this.liquidationFee.addFeeCase(this.feeCase));
        Assert.assertNull(this.liquidationFee.calculateFee(new Date(0),
                new Date(10)));
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
