/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.history;

import java.util.Date;

import es.unileon.ulebank.account.DetailedInformation;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;

/**
 *
 * @author roobre
 */
public abstract class Transaction {

    private final Handler id;
    private final double amount;
    private final Date date;
    private Date effectiveDate;
    private final String subject;
    private final DetailedInformation extraInformation;

    /**
     *
     * @param amount
     * @param date
     * @param subject
     */
    public Transaction(double amount, Date date, String subject)
            throws TransactionException {
        this(amount, date, subject, new DetailedInformation(""));
    }

    /**
     *
     * @param amount
     * @param date
     * @param subject
     * @param info
     */
    public Transaction(double amount, Date date, String subject,
            DetailedInformation info) throws TransactionException {
        this.id = TransactionHandlerProvider.getTransactionHandler();
        final StringBuilder err = new StringBuilder();

        if (subject == null) {
            err.append("The subject cannot be null \n");
        } else {
            if (subject.length() == 0) {
                err.append("Transaction length cannot be 0 \n");
            }
        }

        if (info == null) {
            err.append("DetailedInformation cannot be null\n");
        }
        if (date == null) {
            err.append("The date cannot be null \n");
        }

        if (info == null) {
            err.append("Extra info cannot be null \n");
        }

        if (err.length() > 0) {
            throw new TransactionException(err.toString());
        }
        this.amount = amount;
        this.date = date;
        this.subject = subject;
        this.extraInformation = info;
        this.extraInformation.doFinal();
    }

    /**
     * @return the id
     */
    public Handler getId() {
        return this.id;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     *
     * @param effectiveDate
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public DetailedInformation getDetailedInformation() {
        return this.extraInformation;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Transaction " + "id=" + this.id + ", amount=" + this.amount
                + ", date=" + this.date + ", effectiveDate="
                + this.effectiveDate + ", subject=" + this.subject;
    }
}
