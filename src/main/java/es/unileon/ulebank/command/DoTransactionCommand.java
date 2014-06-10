/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.command;

import java.util.Date;

import org.apache.log4j.Logger;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.account.DetailedInformation;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.office.Office;

/**
 *
 * @author Paula
 */
public class DoTransactionCommand implements Command {

    private final Handler commandID;
    private final double amount;
    private final Date date;
    private final String subject;
    private final DetailedInformation extraInformation;
    private Transaction transaction;
    private final Office office;
    private final Handler destine;

    /**
     *
     * @param amount
     * @param date
     * @param subject
     * @param info
     * @param commandId
     */
    public DoTransactionCommand(Office office, double amount, Handler destine,
            Date date, String subject, DetailedInformation info,
            Handler commandId) {
        this.amount = amount;
        this.date = date;
        this.subject = subject;
        this.extraInformation = info;
        this.commandID = commandId;
        this.destine = destine;
        this.office = office;
    }

    /**
     *
     */
    @Override
    public void execute() {
        try {
            this.transaction = new GenericTransaction(this.amount, this.date,
                    this.subject, this.extraInformation);
            final Account ac = this.office.searchAccount(this.destine);
            ac.doTransaction(this.transaction);
        } catch (final TransactionException ex) {
            Logger.getLogger(DoTransactionCommand.class.getName()).error(
                    ex.toString());
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Handler getID() {
        return this.commandID;
    }

    /**
     *
     */
    @Override
    public void undo() {
        // it makes no sense
    }

    /**
     *
     */
    @Override
    public void redo() {
        // it makes no sense
    }

}
