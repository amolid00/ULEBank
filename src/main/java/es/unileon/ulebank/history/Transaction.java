/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.history;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.TransactionHandlerProvider;

/**
 *
 * @author roobre
 */
@Entity
@Table(name = "TRANSACTIONS", catalog = "ULEBANK_FINAL")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction implements Serializable {

    /**
     * the serial id
     */
    private static final long serialVersionUID = 1L;
    private Handler id;
    private double amount;
    private Date date;
    private Date effectiveDate;
    private String subject;
    private String extraInformation;

    /**
     *
     * @param amount
     * @param date
     * @param subject
     */
    public Transaction(double amount, Date date, String subject)
            throws TransactionException {
        this(amount, date, subject, "");
    }

    public Transaction() {

    }

    /**
     *
     * @param amount
     * @param date
     * @param subject
     * @param info
     */
    public Transaction(double amount, Date date, String subject, String info)
            throws TransactionException {
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
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    public Handler getId() {
        return this.id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }

    public void setId(Handler id) {
        this.id = id;
    }

    /**
     * @return the amount
     */
    @Column(name = "amount", nullable = false, precision = 22, scale = 0)
    public double getAmount() {
        return this.amount;
    }

    /**
     * @return the date
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false, length = 19)
    public Date getDate() {
        return this.date;
    }

    /**
     * @return the effectiveDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effective_date", nullable = false, length = 19)
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    /**
     * @return the subject
     */
    @Column(name = "subject", nullable = false, length = 64)
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

    @Column(name = "extra_information", nullable = false, length = 64)
    public String getExtraInformation() {
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