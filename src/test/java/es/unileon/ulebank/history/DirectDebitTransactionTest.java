package es.unileon.ulebank.history;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;

public class DirectDebitTransactionTest {

    private String subject;
    private double amount;
    private Date date;
    private static final double EPSILON = Math.pow(10, -10);
    private DirectDebitTransaction transaction;
    private Date effectiveDate;
    private Handler directDebitId;

    @Before
    public void setUp() throws Exception {
        this.date = new Date();
        this.effectiveDate = new Date();
        this.amount = 10;
        this.subject = "subject";
        this.directDebitId = new GenericHandler("1234");
        this.transaction = new DirectDebitTransaction(this.amount, this.date,
                this.subject, this.directDebitId);
        this.transaction.setEffectiveDate(this.effectiveDate);
    }

    @Test(expected = TransactionException.class)
    public void testNullSubject() throws TransactionException {
        new DirectDebitTransaction(this.amount, this.date, null,
                this.directDebitId);
    }

    @Test(expected = TransactionException.class)
    public void testNullDate() throws TransactionException {
        new DirectDebitTransaction(this.amount, null, this.subject,
                this.directDebitId);
    }

    @Test(expected = TransactionException.class)
    public void testNullSubjectNullDate() throws TransactionException {
        new DirectDebitTransaction(this.amount, null, null, this.directDebitId);
    }

    @Test(expected = TransactionException.class)
    public void testNullSubjectNullDetailedInformation()
            throws TransactionException {
        new DirectDebitTransaction(this.amount, this.date, null,
                this.directDebitId);
    }

    @Test(expected = TransactionException.class)
    public void testEmptySubject() throws TransactionException {
        new DirectDebitTransaction(this.amount, this.date, "",
                this.directDebitId);
    }

    @Test
    public void testGetAmount() {
        Assert.assertEquals(this.amount, this.transaction.getAmount(),
                DirectDebitTransactionTest.EPSILON);
    }

    @Test
    public void testGetSubject() {
        Assert.assertEquals(this.subject.equals(this.transaction.getSubject()),
                true);
    }

    @Test
    public void testGetDate() {
        Assert.assertEquals(this.date.getTime(), this.transaction.getDate()
                .getTime(), DirectDebitTransactionTest.EPSILON);
    }

    @Test
    public void testGetAndSetEffectiveDate() {
        Assert.assertEquals(this.effectiveDate.getTime(), this.transaction
                .getEffectiveDate().getTime(),
                DirectDebitTransactionTest.EPSILON);

        final Date newDate = new Date();
        this.transaction.setEffectiveDate(newDate);
        Assert.assertEquals(newDate.getTime(), this.transaction
                .getEffectiveDate().getTime(),
                DirectDebitTransactionTest.EPSILON);
    }

    @Test
    public void testGetDetailedInformation() {
        Assert.assertEquals(
                "".equals(this.transaction.getDetailedInformation().toString()),
                true);
    }

    @Test
    public void testGetDirectDebitId() {
        Assert.assertEquals(this.transaction.getDirectDebitId(),
                this.directDebitId);
    }

    @Test
    public void testToString() {

        final String exp = "Transaction id=" + this.transaction.getId()
                + ", amount=" + this.transaction.getAmount() + ", date="
                + this.transaction.getDate() + ", effectiveDate="
                + this.transaction.getEffectiveDate() + ", subject="
                + this.subject;
        Assert.assertEquals(exp, this.transaction.toString());
    }
}