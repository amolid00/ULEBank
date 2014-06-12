package es.unileon.ulebank.command;

import java.io.IOException;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.command.exceptions.CommandException;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.exceptions.CommissionException;
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
    private final Handler cardHandler;
    /**
     * Limite de compra diario para la tarjeta
     */
    private final double buyLimitDiary;
    /**
     * Limite de compra mensual para la tarjeta
     */
    private final double buyLimitMonthly;
    /**
     * Limite de extraccion en cajero diario para la tarjeta
     */
    private final double cashLimitDiary;
    /**
     * Limite de extraccion en cajero mensual para la tarjeta
     */
    private final double cashLimitMonthly;
    /**
     * Comision de emision de la tarjeta
     */
    private final double commissionEmission;
    /**
     * Comision de mantenimiento de la tarjeta
     */
    private final double commissionMaintenance;
    /**
     * Comision de renovacion de la tarjeta
     */
    private final double commissionRenovate;
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
     * @param string
     * @param officeId
     * @param cardId
     * @param buyLimitDiary
     * @param buyLimitMonthly
     * @param cashLimitDiary
     * @param cashLimitMonthly
     * @param commissionEmission
     * @param commissionMaintenance
     * @param commissionRenovate
     * @throws MalformedHandlerException
     */
    public NewCardCommand(Office office, Client client, Account account,
            CardType cardType, String string, String officeId, String cardId,
            double buyLimitDiary, double buyLimitMonthly,
            double cashLimitDiary, double cashLimitMonthly,
            double commissionEmission, double commissionMaintenance,
            double commissionRenovate) throws CommandException {
        this.office = office;
        this.account = account;
        this.client = client;
        try {
            this.cardHandler = new CardHandler(cardId);
        } catch (MalformedHandlerException e) {
            throw new CommandException(e.getMessage());
        }
        this.cardType = cardType.toString();
        this.id = new CommandHandler(this.cardHandler);
        this.buyLimitDiary = buyLimitDiary;
        this.buyLimitMonthly = buyLimitMonthly;
        this.cashLimitDiary = cashLimitDiary;
        this.cashLimitMonthly = cashLimitMonthly;
        this.commissionEmission = commissionEmission;
        this.commissionMaintenance = commissionMaintenance;
        this.commissionRenovate = commissionRenovate;
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
                        this.account, this.buyLimitDiary, this.buyLimitMonthly,
                        this.cashLimitDiary, this.cashLimitMonthly,
                        this.commissionEmission, this.commissionMaintenance,
                        this.commissionRenovate);
            } else if ("DEBIT".equalsIgnoreCase(this.cardType)) {
                this.card = new DebitCard(this.cardHandler, this.client,
                        this.account, this.buyLimitDiary, this.buyLimitMonthly,
                        this.cashLimitDiary, this.cashLimitMonthly,
                        this.commissionEmission, this.commissionMaintenance,
                        this.commissionRenovate);
            } 
        } catch (InvalidFeeException e) {
            throw new CommandException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new CommandException(e.getMessage());
        } catch (CommissionException e) {
            throw new CommandException(e.getMessage());
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
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