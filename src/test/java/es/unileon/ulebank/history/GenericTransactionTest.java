package es.unileon.ulebank.history;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.DetailedInformation;
import es.unileon.ulebank.exceptions.TransactionException;

public class GenericTransactionTest {

    private String subject;
    private double amount;
    private Date date;
    private static final double EPSILON = Math.pow(10, -10);
    private Transaction generic;
    private Transaction genericWithExtraInfo;
    private Date effectiveDate;
    private String extraInfo;

    @Before
    public void setUp() throws Exception {
        this.extraInfo = "extraInfo";
        this.date = new Date();
        this.effectiveDate = new Date();
        this.amount = 10;
        this.subject = "subject";
        this.generic = new GenericTransaction(this.amount, this.date,
                this.subject);
        this.genericWithExtraInfo = new GenericTransaction(this.amount,
                this.date, this.subject,
                new DetailedInformation(this.extraInfo));
        this.generic.setEffectiveDate(this.effectiveDate);
        this.genericWithExtraInfo.setEffectiveDate(this.effectiveDate);
    }

    @Test(expected = TransactionException.class)
    public void testNullSubject() throws TransactionException {
        new GenericTransaction(this.amount, this.date, null,
                new DetailedInformation(this.extraInfo));
    }

    @Test(expected = TransactionException.class)
    public void testNullDate() throws TransactionException {
        new GenericTransaction(this.amount, null, this.subject,
                new DetailedInformation(this.extraInfo));
    }

    @Test(expected = TransactionException.class)
    public void testNullSubjectNullDate() throws TransactionException {
        new GenericTransaction(this.amount, null, null,
                new DetailedInformation(this.extraInfo));
    }

    @Test(expected = TransactionException.class)
    public void testNullSubjectNullDetailedInformation()
            throws TransactionException {
        new GenericTransaction(this.amount, this.date, this.subject, null);
    }

    @Test(expected = TransactionException.class)
    public void testEmptySubject() throws TransactionException {
        new GenericTransaction(this.amount, this.date, "",
                new DetailedInformation(this.extraInfo));
    }

    @Test
    public void testGetAmount() {
        Assert.assertEquals(this.amount, this.generic.getAmount(),
                GenericTransactionTest.EPSILON);
        Assert.assertEquals(this.amount, this.genericWithExtraInfo.getAmount(),
                GenericTransactionTest.EPSILON);
    }

    @Test
    public void testGetSubject() {
        Assert.assertEquals(this.subject.equals(this.generic.getSubject()),
                true);
        Assert.assertEquals(
                this.subject.equals(this.genericWithExtraInfo.getSubject()),
                true);
    }

    @Test
    public void testGetDate() {
        Assert.assertEquals(this.date.getTime(), this.generic.getDate()
                .getTime(), GenericTransactionTest.EPSILON);
        Assert.assertEquals(this.date.getTime(), this.genericWithExtraInfo
                .getDate().getTime(), GenericTransactionTest.EPSILON);
    }

    @Test
    public void testGetAndSetEffectiveDate() {
        Assert.assertEquals(this.effectiveDate.getTime(), this.generic
                .getEffectiveDate().getTime(), GenericTransactionTest.EPSILON);
        Assert.assertEquals(this.effectiveDate.getTime(),
                this.genericWithExtraInfo.getEffectiveDate().getTime(),
                GenericTransactionTest.EPSILON);
        final Date newDate = new Date();
        this.generic.setEffectiveDate(newDate);
        this.genericWithExtraInfo.setEffectiveDate(newDate);
        Assert.assertEquals(newDate.getTime(), this.generic.getEffectiveDate()
                .getTime(), GenericTransactionTest.EPSILON);
        Assert.assertEquals(newDate.getTime(), this.genericWithExtraInfo
                .getEffectiveDate().getTime(), GenericTransactionTest.EPSILON);
    }

    @Test
    public void testGetDetailedInformation() {
        Assert.assertEquals(
                "".equals(this.generic.getDetailedInformation().toString()),
                true);
        Assert.assertEquals(this.extraInfo.equals(this.genericWithExtraInfo
                .getDetailedInformation().toString()), true);
    }

    @Test
    public void testToString() {

        final String exp = "Transaction id=" + this.generic.getId()
                + ", amount=" + this.generic.getAmount() + ", date="
                + this.generic.getDate() + ", effectiveDate="
                + this.generic.getEffectiveDate() + ", subject=" + this.subject;
        Assert.assertEquals(exp, this.generic.toString());
    }
}
