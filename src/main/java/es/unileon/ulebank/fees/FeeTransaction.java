/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unileon.ulebank.fees;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.history.Transaction;

/**
 *
 * @author roobre
 */
@Entity
@Table(name = "TRANSACTIONS", catalog = "ULEBANK_FINAL")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "FeeTransaction")
public class FeeTransaction extends Transaction {

    private Transaction related;
    private static final String subjectAmmend = "Com. ";

    public FeeTransaction(double amount, Date date, Transaction related)
            throws TransactionException {
        super(amount, date, FeeTransaction.subjectAmmend + related.getSubject());
        this.related = related;
    }

    public FeeTransaction() {
    }

    public void setRelated(Transaction related) {
        this.related = related;
    }

    @Override
    public String toString() {
        return super.toString() + ", related=" + this.related;
    }

}
