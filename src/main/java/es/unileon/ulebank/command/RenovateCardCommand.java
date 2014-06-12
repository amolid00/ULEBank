package es.unileon.ulebank.command;

import java.io.IOException;

import org.apache.log4j.Logger;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.exceptions.PaymentException;

/**
 * @author Israel Comando para la renovacion de la tarjeta
 */
public class RenovateCardCommand implements Command {
    /**
     * Logger de la clase
     */
    private static final Logger LOG = Logger
            .getLogger(ModifyBuyLimitCommand.class.getName());
    /**
     * Identificador del comando
     */
    private final Handler id;
    /**
     * Identificador de la tarjeta a renovar
     */
    private final Handler cardId;
    /**
     * Cuenta a la que esta asociada la tarjeta que se va a renovar
     */
    private final Account account;
    /**
     * Tarjeta que se va a renovar
     */
    private Card card;
    /**
     * Antiguo CVV antes de realizar la renovacion
     */
    private String oldCvv;
    /**
     * Nuevo CVV que se va a asignar
     */
    private String newCvv;
    /**
     * Antigua fecha de caducidad de la tarjeta
     */
    private String oldExpirationDate;
    /**
     * Nueva fecha de caducidad de la tarjeta
     */
    private String newExpirationDate;
    /**
     * Variable para saber si el comando ha sido ejecutado o no
     */
    private boolean executed = false;
    /**
     * Variable para saber si el comando ha sido deshecho o no
     */
    private boolean undone = false;

    /**
     * Constructor de la clase
     * 
     * @param cardId
     * @param office
     * @param dni
     * @param accountHandler
     * @throws CommandException 
     * @throws ClientNotFoundException
     */
    public RenovateCardCommand(Handler cardId, Office office, Handler dni,
            Handler accountHandler) {
        this.id = new CommandHandler(cardId);
        this.cardId = cardId;
        this.account = office.searchClient(dni).searchAccount(accountHandler);
    }

    /**
     * Realiza la renovacion de la tarjeta
     * 
     * @throws IOException
     * @throws CommandException 
     */
    @Override
    public void execute() throws CommandException {
            // Buscamos la tarjeta en la cuenta con el identificador de la misma
            this.card = this.account.searchCard(this.cardId);
            // Guardamos el CVV para poder deshacer la operacion
            this.oldCvv = this.card.getCvv();
            // Guardamos la fecha de caducidad para poder deshacer la operacion
            this.oldExpirationDate = this.card.getExpirationDate();
            // Generamos la nueva fecha de caducidad
            this.newExpirationDate = this.card.generateExpirationDate();
            // Cambiamos la fecha de caducidad por la nueva
            this.card.setExpirationDate(this.newExpirationDate);
            // Generamos el nuevo CVV y lo guardamos
            this.newCvv = this.card.generateCVV();
            // Cambiamos el CVV por el nuevo que se genera
            this.card.setCvv(this.newCvv);
            this.executed = true;
    }

    /**
     * Deshace la renovacion de la tarjeta
     * 
     * @throws IOException
     * @throws CommandException
     */
    @Override
    public void undo() throws CommandException {
        if (this.executed) {
            // Restaura el antiguo CVV
            this.card.setCvv(this.oldCvv);
            // Restaura la antigua fecha de caducidad
            this.card.setExpirationDate(this.oldExpirationDate);
            this.undone = true;

        } else {
            LOG.info("Can't undo because command has not executed yet.");
            throw new PaymentException(
                    "Can't undo because command has not executed yet.");
        }

    }

    /**
     * Rehace la renovacion de la tarjeta despues de haber deshecho la operacion
     * 
     * @throws IOException
     * @throws CommandException
     */
    @Override
    public void redo() throws CommandException {
        if (this.undone) {
            // Vuelve a cambiar el CVV por el que se habia generado
            this.card.setCvv(this.newCvv);
            // Vuelve a cambiar la fecha de caducidad por la nueva
            this.card.setExpirationDate(this.newExpirationDate);
        } else {
            LOG.info("Can't undo because command has not undoned yet.");
            throw new PaymentException(
                    "Can't undo because command has not undoned yet.");
        }
    }

    /**
     * Devuelve el identificador del comando
     * 
     * @return command id
     */
    @Override
    public Handler getID() {
        return this.id;
    }
}
