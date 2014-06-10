package es.unileon.ulebank.command;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

/**
 *
 * @author Paula
 */
public class CreateAccountCommand implements Command {

    private final Office office;
    private final Bank bank;
    private final Handler commandID;
    private Account account;
    private final Client titular;
    private static final int STATE_EXECUTED = 4;
    private static final int STATE_REDO = 1;
    private static final int STATE_UNDO = 2;
    private static final int STATE_NORMAL = 0;
    private int state;

    /**
     *
     * @param office
     * @param bank
     * @param effectiveDate
     * @param commandId
     * @param titular
     */
    public CreateAccountCommand(Office office, Bank bank, Date effectiveDate,
            Handler commandId, Client titular) {
        this.bank = bank;
        this.office = office;
        this.titular = titular;
        this.commandID = commandId;
        this.state = CreateAccountCommand.STATE_NORMAL;
    }

    /**
     *
     */
    @Override
    public void execute() {
        try {
            if ((this.account == null)
                    && (this.state == CreateAccountCommand.STATE_NORMAL)) {
                this.account = new Account(this.office, this.bank,
                        this.office.getNewAccountNumber(), this.titular);
                this.office.addAccount(this.account);
                this.state = CreateAccountCommand.STATE_EXECUTED;
            }
        } catch (final MalformedHandlerException ex) {
            Logger.getLogger(CreateAccountCommand.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (final WrongArgsException ex) {
            Logger.getLogger(CreateAccountCommand.class.getName()).log(
                    Level.SEVERE, null, ex);
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
        if ((this.state & (CreateAccountCommand.STATE_EXECUTED | CreateAccountCommand.STATE_REDO)) != 0) {
            this.office.deleteAccount(this.account.getID());
            this.state = CreateAccountCommand.STATE_UNDO;
        }
    }

    /**
     *
     */
    @Override
    public void redo() {
        if (this.state == CreateAccountCommand.STATE_UNDO) {
            this.office.addAccount(this.account);
            this.state = CreateAccountCommand.STATE_REDO;
        }
    }

}
