package es.unileon.ulebank.command;

import org.apache.log4j.Logger;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.ClientNotFoundException;
import es.unileon.ulebank.command.exceptions.CommandException;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Transfer;
import es.unileon.ulebank.payments.exceptions.TransferException;

/**
 * Transfer Command Class
 * 
 * @author Rober dCR
 * @date 13/05/2014
 * @brief Command with which can make a transfer between two accounts
 */
public class TransferCommand implements Command {
    /**
     * Class Logger
     */
    private static final Logger LOG = Logger
            .getLogger(ModifyBuyLimitCommand.class.getName());
    /**
     * Identifier of the command
     */
    private Handler id;
    /**
     * Account which makes the transfer
     */
    private Account accountSender;
    /**
     * Account which receives the amount
     */
    private Account accountReceiver;
    /**
     * Quantity of the transfer
     */
    private double amount;
    /**
     * Concept of the transfer
     */
    private String concept;
    /**
     * Prove if we can undo the command
     */
    private boolean undo = false;
    /**
     * Prove if we can redo the command
     */
    private boolean redo = false;

    /**
     * Class constructor
     * 
     * @param ofice
     * @param accountSender
     * @param dniSender
     * @param accountReceiver
     * @param dniReceiver
     * @param amount
     * @throws CommandException 
     */
    public TransferCommand(Office office, Handler accountSender,
            Handler dniSender, Handler accountReceiver, Handler dniReceiver,
            double amount, String concept) throws CommandException {
        try {
            this.id = new CommandHandler(accountSender);
            this.accountSender = office.searchClient(dniSender).searchAccount(
                    accountSender);
            this.accountReceiver = office.searchClient(dniReceiver)
                    .searchAccount(accountReceiver);
            this.amount = amount;
            this.concept = concept;
        } catch (ClientNotFoundException e) {
            LOG.info(e.getMessage());
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Metodo que realiza la transferencia
     */
    @Override
    public void execute() throws CommandException {
        try {
            final Transfer transfer = new Transfer(this.accountSender,
                    this.accountReceiver, this.amount);
            transfer.make(this.concept);
            this.undo = true;
        } catch (TransferException e) {
            LOG.info(e.getMessage());
            throw new CommandException(e.getMessage());
        } catch (TransactionException e) {
            LOG.info(e.getMessage());
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Metodo que deshace la transferencia
     * @throws CommandException 
     */
    @Override
    public void undo() throws CommandException {
        if (this.undo) {
            try {
                final Transfer transfer = new Transfer(this.accountReceiver,
                        this.accountSender, this.amount);
                transfer.make("Return transfer " + this.concept);
                this.redo = true;
                this.undo = false;
            } catch (TransferException e) {
                LOG.info(e.getMessage());
                throw new CommandException(e.getMessage());
            } catch (TransactionException e) {
                LOG.info(e.getMessage());
                throw new CommandException(e.getMessage());
            }
        }
    }

    /**
     * Metodo que rehace la transferencia
     * @throws CommandException 
     */
    @Override
    public void redo() throws CommandException {
        if (this.redo) {
            try {
                final Transfer transfer = new Transfer(this.accountSender,
                        this.accountReceiver, this.amount);
                transfer.make(this.concept);
                this.undo = true;
                this.redo = false;
            } catch (TransferException e) {
                LOG.info(e.getMessage());
                throw new CommandException(e.getMessage());
            } catch (TransactionException e) {
                LOG.info(e.getMessage());
                throw new CommandException(e.getMessage());
            }
        }

    }

    /**
     * Metodo que devuelve el identificador del comando
     * 
     * @return command id
     */
    @Override
    public Handler getID() {
        return this.id;
    }

}
