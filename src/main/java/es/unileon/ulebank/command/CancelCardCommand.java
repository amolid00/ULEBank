package es.unileon.ulebank.command;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.command.exceptions.CommandException;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.exceptions.CardNotFoundException;

/**
 * @author Israel Comando para realizar la cancelacion de la tarjeta
 */
public class CancelCardCommand implements Command {
    /**
     * Identificador del comando
     */
    private final Handler id;
    /**
     * Identificador de la tarjeta a cancelar
     */
    private final Handler cardId;
    /**
     * Cuenta a la que esta asociada la tarjeta que se va a cancelar
     */
    private final Account account;

    /**
     * Constructor de la clase
     * 
     * @param cardId
     * @param office
     * @param dni
     * @param account
     * @throws ClientNotFoundException
     */
    public CancelCardCommand(Handler cardId, Office office, Handler dni,
            Handler account) {
        this.id = new CommandHandler(cardId);
        this.cardId = cardId;
        this.account = office.searchClient(dni).searchAccount(account);
    }

    /**
     * Realiza la cancelacion de la tarjeta
     * @throws CommandException 
     */
    @Override
    public void execute() throws CommandException {
        // Se borra la tarjeta de la lista de tarjetas de la cuenta
        try {
            this.account.removeCard(this.cardId);
        } catch (CardNotFoundException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Operacion no soportada, no se puede deshacer una cancelacion
     */
    @Override
    public void undo() {
        throw new UnsupportedOperationException();
    }

    /**
     * Operacion no soportada, como no se puede deshacer una cancelacion tampoco
     * se puede rehacer
     */
    @Override
    public void redo() {
        throw new UnsupportedOperationException();
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
