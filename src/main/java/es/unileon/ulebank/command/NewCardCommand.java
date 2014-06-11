package es.unileon.ulebank.command;

import java.io.IOException;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.ClientNotFoundException;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.handler.CardHandler;
import es.unileon.ulebank.handler.CommandHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.CardType;
import es.unileon.ulebank.payments.CreditCard;
import es.unileon.ulebank.payments.DebitCard;
import es.unileon.ulebank.repository.CardBean;

/**
 * @author Israel
 * Comando para la creacion de las tarjetas
 */
public class NewCardCommand implements Command {
	/**
	 * Tarjeta que se va a crear
	 */
	private Card card;
	/**
	 * Identificador del comando
	 */
	private Handler id;
	/**
	 * Cuenta a la que se ha de asociar la tarjeta
	 */
	private Account account;
	/**
	 * Oficina en la que esta la cuenta a la que se va a asociar la tarjeta
	 */
	private Office office;
	/**
	 * Tipo de tarjeta a crear
	 */
	private String cardType;
	/**
	 * Identificador de la tarjeta
	 */
	private Handler cardHandler;
	/**
	 * Limite de compra diario para la tarjeta
	 */
	private double buyLimitDiary;
	/**
	 * Limite de compra mensual para la tarjeta
	 */
	private double buyLimitMonthly;
	/**
	 * Limite de extraccion en cajero diario para la tarjeta
	 */
	private double cashLimitDiary;
	/**
	 * Limite de extraccion en cajero mensual para la tarjeta
	 */
	private double cashLimitMonthly;
	/**
	 * Comision de emision de la tarjeta
	 */
	private double commissionEmission;
	/**
	 * Comision de mantenimiento de la tarjeta
	 */
	private double commissionMaintenance;
	/**
	 * Comision de renovacion de la tarjeta
	 */
	private double commissionRenovate;
	/**
	 * Duegno de la tarjeta
	 */
	private Client client;
	/**
	 * Manejador de las operaciones de la tarjeta
	 */
//	private CardManager manager;
	/**
	 * Constructor de la clase
	 * @param office
	 * @param dni
	 * @param accountHandler
	 * @param type
	 * @param buyLimitDiary
	 * @param buyLimitMonthly
	 * @param cashLimitDiary
	 * @param cashLimitMonthly
	 * @param commissionEmission
	 * @param commissionMaintenance
	 * @param commissionRenovate
	 * @param limitDebit
	 * @throws MalformedHandlerException 
	 * @throws WrongArgsException 
	 */
	public NewCardCommand(Office office, Client client, Account account, CardBean bean) throws MalformedHandlerException, WrongArgsException {
		this.office = office;
	    this.account = account;
		this.client = client;
		this.cardHandler = new CardHandler(bean.getCardNumber());
		this.id = new CommandHandler(this.cardHandler);
		this.cardType = bean.getCardType();
		this.buyLimitDiary = bean.getBuyLimitDiary();
		this.buyLimitMonthly = bean.getBuyLimitMonthly();
		this.cashLimitDiary = bean.getCashLimitDiary();
		this.cashLimitMonthly = bean.getCashLimitMonthly();
		this.commissionEmission = bean.getCommissionEmission();
		this.commissionMaintenance = bean.getCommissionMaintenance();
		this.commissionRenovate = bean.getCommissionRenovate();
	}

	public NewCardCommand(Office office, Client client, Account account,
            CardType cardType, String string, String officeId,
            String cardId, double buyLimitDiary, double buyLimitMonthly,
            double cashLimitDiary, double cashLimitMonthly,
            double commissionEmission, double commissionMaintenance,
            double commissionRenovate) throws MalformedHandlerException {
	    this.office = office;
        this.account = account;
        this.client = client;
        this.cardHandler = new CardHandler(cardId);
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
	 * @throws InvalidFeeException 
	 * @throws ClientNotFoundException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public void execute()
			throws CommissionException, NumberFormatException, IOException, InvalidFeeException {
		if ("CREDIT".equalsIgnoreCase(this.cardType)) {
			this.card = new CreditCard(this.cardHandler, this.client, this.account, 
					this.buyLimitDiary, this.buyLimitMonthly, this.cashLimitDiary, 
					this.cashLimitMonthly, this.commissionEmission, this.commissionMaintenance, 
					this.commissionRenovate);
		} else if ("DEBIT".equalsIgnoreCase(this.cardType)) {
			this.card = new DebitCard(this.cardHandler, this.client, this.account, 
					this.buyLimitDiary, this.buyLimitMonthly, this.cashLimitDiary, 
					this.cashLimitMonthly, this.commissionEmission, this.commissionMaintenance, 
					this.commissionRenovate);
		} else if ("REVOLVING".equalsIgnoreCase(this.cardType)) {
			this.cardType.equalsIgnoreCase("REVOLVING");
		}

		if (this.card != null) {
		    System.out.println("");
		    this.account.addCard(card);
//			this.manager.saveNewCard(card);
		}
	}

	/**
	 * Deshace la creacion de la tarjeta
	 * @throws ClientNotFoundException 
	 */
	@Override
	public void undo() throws ClientNotFoundException {
		CancelCardCommand cancel = new CancelCardCommand(cardHandler, office, client.getId(), account.getID());
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
	 * @return command id
	 */
	@Override
	public Handler getID() {
		return this.id;
	}
}