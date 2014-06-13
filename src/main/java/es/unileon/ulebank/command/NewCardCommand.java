package es.unileon.ulebank.command;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CardType;
import es.unileon.ulebank.payments.CreditCard;
import es.unileon.ulebank.payments.DebitCard;
import es.unileon.ulebank.payments.handler.CardHandler;

/**
 * @author Israel Comando para la creacion de las tarjetas
 */
public class NewCardCommand implements Command {
    /**
     * Logger de la clase
     */
    private static final Logger LOG = Logger
            .getLogger(NewCardCommand.class.getName());
    /**
     * Tarjeta que se va a crear
     */
    private Card card;
    /**
     * Identificador del comando
     */
    private final Handler id;
    /**
     * Cuenta a la que se ha de asociar la tarjeta
     */
    private final Account account;
    /**
     * Oficina en la que esta la cuenta a la que se va a asociar la tarjeta
     */
    private final Office office;
    /**
     * Tipo de tarjeta a crear
     */
    private final String cardType;
    /**
     * Identificador de la tarjeta
     */
    private Handler cardHandler;
    /**
     * Duegno de la tarjeta
     */
    private final Client client;

    /**
     * Constructor del comando que recibe los datos necesarios para crear una tarjeta
     * @param office
     * @param client
     * @param account
     * @param cardType
     * @param officeId
     * @param cardId
     * @throws CommandException
     */
    public NewCardCommand(Office office, Client client, Account account,
            CardType cardType, String officeId, String cardId) throws CommandException {
        this.office = office;
        this.account = account;
        this.client = client;
        try {
            this.cardHandler = new CardHandler(cardId);
        } catch (MalformedHandlerException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
        this.cardType = cardType.toString();
        this.id = new CommandHandler(this.cardHandler);
    }

    /**
     * Realiza la creacion de la tarjeta con todos los parametros necesarios
     * @throws CommandException 
     * 
     * @throws InvalidFeeException
     * @throws ClientNotFoundException
     * @throws IOException
     * @throws NumberFormatException
     */
    @Override
    public void execute() throws CommandException {

        try {
            if ("CREDIT".equalsIgnoreCase(this.cardType)) {
                this.card = new CreditCard(this.cardHandler, this.client,
                        this.account);
            } else if ("DEBIT".equalsIgnoreCase(this.cardType)) {
                this.card = new DebitCard(this.cardHandler, this.client,
                        this.account);
            } 
        } catch (NumberFormatException e) {
        	LOG.log(Level.SEVERE, e.getMessage());
            throw new NumberFormatException(e.getMessage());
        }

        if (this.card != null) {
            this.account.addCard(this.card);
        }
    }

    /**
     * Deshace la creacion de la tarjeta
     * @throws CommandException 
     * 
     * @throws ClientNotFoundException
     */
    @Override
    public void undo() throws CommandException {
        CancelCardCommand cancel;
        cancel = new CancelCardCommand(
                this.cardHandler, this.office, this.client.getId(),
                this.account.getID());
        cancel.execute();
    }

    /**
     * Operacion no soportada, no se puede deshacer la cancelacion
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