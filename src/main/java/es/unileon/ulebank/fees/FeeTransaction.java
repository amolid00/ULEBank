/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unileon.ulebank.fees;

import java.util.Date;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.history.Transaction;

/**
 *
 * @author roobre
 */
public class FeeTransaction extends Transaction {

    private final Transaction related;
    private static final String subjectAmmend = "Com. ";

    public FeeTransaction(double amount, Date date, Transaction related)
            throws TransactionException {
        super(amount, date, FeeTransaction.subjectAmmend + related.getSubject());

        this.related = related;
    }

    @Override
    public String toString() {
        return super.toString() + ", related=" + this.related;
    }

}
