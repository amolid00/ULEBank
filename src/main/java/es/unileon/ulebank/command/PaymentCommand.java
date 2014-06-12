package es.unileon.ulebank.command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.command.exceptions.CommandException;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CardType;
import es.unileon.ulebank.payments.Transfer;
import es.unileon.ulebank.payments.exceptions.PaymentException;
import es.unileon.ulebank.payments.exceptions.TransferException;

/**
 * Payment Command Class
 * 
 * @author Rober dCR
 * @date 12/05/2014
 * @brief Class that implements the command for make a payment
 */
public class PaymentCommand implements Command {
    /**
     * Logger Class
     */
    private static final Logger LOG = Logger.getLogger(PaymentCommand.class);
    /**
     * Concept when undo the command
     */
    private static final String UNDO_PROPERTY = "concept_undo_payment";
    /**
     * String for add in the concept when makes the undo method
     */
    private String undoConcept;
    /**
     * Command Identifier
     */
    private final Handler id;
    /**
     * Card Identifier
     */
    private final Handler cardId;
    /**
     * Card which makes the payment
     */
    private Card card;
    /**
     * Account which sends the payment
     */
    private final Account accountSender;
    /**
     * Account which receives the payment
     */
    private Account accountReceiver;
    /**
     * Amount of the payment
     */
    private final double amount;
    /**
     * Concept of the payment
     */
    private final String concept;

    /**
     * Class constructor
     * 
     * @param cardId
     * @param office
     * @param dni
     * @param accountHandler
     * @param dniReceiver
     * @param accountReceiver
     * @param amount
     * @param concept
     * @param type
     * @throws CommandException 
     */
    public PaymentCommand(Handler cardId, Office office, Handler dni,
            Handler accountHandler, double amount, String concept, CardType type) throws CommandException {
        this.id = new CommandHandler(cardId);
        this.cardId = cardId;
        this.accountSender = office.searchClient(dni).searchAccount(
                accountHandler);
        this.amount = amount;
        this.concept = concept;
    }

    /**
     * Method to execute payment
     * @throws CommandException 
     */
    @Override
    public void execute() throws CommandException {
        try {
            // Search the account for that card
            this.card = this.accountSender.searchCard(this.cardId);
            // Make the payment by the type of the card
            this.card.makeTransaction(this.amount, this.concept);
        } catch (TransactionException e) {
            PaymentCommand.LOG.info(e.getMessage());
            throw new CommandException(e.getMessage());
        } catch (PaymentException e) {
            PaymentCommand.LOG.info(e.getMessage());
            throw new CommandException(e.getMessage());
        }

    }

    /**
     * Method to undo payment
     */
    @Override
    public void undo() throws CommandException {
        try {
            // Make the transfer for revert the payment
            final Transfer revertPayment = new Transfer(this.accountReceiver,
                    this.accountSender, this.amount);
            this.setUndoConcept();
            revertPayment.make(this.undoConcept + this.cardId.toString());
        } catch (TransactionException e) {
            PaymentCommand.LOG.info(e.getMessage());
            throw new CommandException(e.getMessage());
        } catch (TransferException e) {
            PaymentCommand.LOG.info(e.getMessage());
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Method to redo payment
     */
    @Override
    public void redo() throws PaymentException, TransactionException {
        try {
            // Make the payment by the type of the card
            this.card.makeTransaction(this.amount, this.concept);
        } catch (TransactionException e) {
            PaymentCommand.LOG.info(e.getMessage());
            throw new TransactionException(e.getMessage());
        }

    }

    /**
     * Method that returns command ID
     * 
     * @return command id
     */
    @Override
    public Handler getID() {
        return this.id;
    }

    /**
     * Setter of undoConcept
     * 
     * @throws IOException
     */
    private void setUndoConcept() throws CommandException {
        try {
            final Properties commissionProperty = new Properties();
            commissionProperty.load(new FileInputStream(
                    "src/es/unileon/ulebank/properties/card.properties"));

            /* Obtain the paramentes in card.properties */
            this.undoConcept = commissionProperty
                    .getProperty(PaymentCommand.UNDO_PROPERTY);
        } catch (FileNotFoundException e) {
            throw new CommandException("File card.properties not found");
        } catch (IOException e2) {
            throw new CommandException(
                    "Fail in card.properties when try open or close file.");
        }
    }

}
