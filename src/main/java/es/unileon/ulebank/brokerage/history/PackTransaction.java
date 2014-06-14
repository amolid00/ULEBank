/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.brokerage.history;

import es.unileon.ulebank.brokerage.pack.Pack;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.users.Employee;
import es.unileon.ulebank.history.Transaction;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author roobre
 */
@Entity
@Table(name = "TRANSACTIONS", catalog = "ULEBANK_FINAL")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "PackTransaction")
public class PackTransaction extends Transaction {

    private Pack pack;
    private Employee operator;

    public PackTransaction(double amount, Date date, String subject, Pack pack,
            Employee operator) throws TransactionException {
        super(amount, date, subject);

        this.pack = pack;
        this.operator = operator;
    }

    public Pack getPack() {
        return this.pack;
    }

    public Employee getOperator() {
        return operator;
    }

    public void setOperator(Employee operator) {
        this.operator = operator;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    @Override
    public String toString() {
        return super.toString() + ", pack=" + this.pack + ", operator="
                + this.operator;
    }

}
