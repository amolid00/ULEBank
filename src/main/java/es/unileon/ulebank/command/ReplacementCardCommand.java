package es.unileon.ulebank.command;

import java.io.IOException;

import org.apache.log4j.Logger;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.exceptions.ClientNotFoundException;
import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.handler.CommandHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;

/**
 * @author Israel
 * Comando para la sustitucion de la tarjeta
 */
public class ReplacementCardCommand implements Command {
	/**
	 * Logger de la clase
	 */
	private static final Logger LOG = Logger.getLogger(ModifyBuyLimitCommand.class.getName());
	/**
	 * Identificador del comando
	 */
	private Handler id;
	/**
	 * Tarjeta que vamos a sustituir
	 */
	private Card card;
	/**
	 * Identificador de la tarjeta a sustituir
	 */
	private Handler cardId;
	/**
	 * Cuenta a la que esta asociada la tarjeta
	 */
	private Account account;
	/**
	 * PIN antes de la sustitucion
	 */
	private String oldPin;
	/**
	 * PIN nuevo que es generado
	 */
	private String newPin;
	/**
	 * CVV antes de la sustitucion
	 */
	private String oldCvv;
	/**
	 * CVV nuevo que es generado
	 */
	private String newCvv;
	/**
	 * Anterior fecha de caducidad
	 */
	private String oldExpirationDate;
	/**
	 * Nueva fecha de caducidad
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
	 * @param cardId
	 * @param office
	 * @param dni
	 * @param accountHandler
	 * @throws ClientNotFoundException 
	 */
	public ReplacementCardCommand(Handler cardId, Office office, Handler dni, Handler accountHandler) throws ClientNotFoundException {
		this.id = new CommandHandler(cardId);
		this.cardId = cardId;
		this.account = office.searchClient(dni).searchAccount(accountHandler);
	}

	/**
	 * Realiza la sustitucion de la tarjeta
	 * @throws IOException 
	 */
	@Override
	public void execute() throws IOException {
		try{
			//Buscamos la tarjeta en la cuenta a la que esta asociada a traves del identificador
			this.card = account.searchCard(cardId);
			//Guardamos el PIN anterior
			this.oldPin = card.getPin();
			//Generamos el nuevo PIN y lo almacenamos
			this.newPin = card.generatePinCode();
			//Cambiamos el PIN por el nuevo
			this.card.setPin(newPin);
			//Guardamos el anterior CVV
			this.oldCvv = card.getCvv();
			//Generamos un CVV nuevo y lo guardamos
			this.newCvv = card.generateCVV();
			//Cambiamos el CVV por el nuevo
			this.card.setCvv(newCvv);
			//Guardamos la anterior fecha de caducidad
			this.oldExpirationDate = card.getExpirationDate();
			//Generamos la nueva fecha de caducidad y la almacenamos
			this.newExpirationDate = card.generateExpirationDate();
			//Cambiamos la fecha de caducidad por la nueva
			this.card.setExpirationDate(newExpirationDate);
			this.executed = true;
		}catch (IOException e) {
			LOG.info(e.getMessage());
		}
	}

	/**
	 * Restaura los valores antes de la sustitucion
	 * @throws IOException 
	 * @throws CommandException 
	 */
	@Override
	public void undo() throws IOException, CommandException {
		if (this.executed){
			try{
				//Restaura el CVV
				this.card.setCvv(oldCvv);
				//Restaura el codigo PIN
				this.card.setPin(oldPin);
				//Restaura la fecha de caducidad
				this.card.setExpirationDate(oldExpirationDate);
				this.undone = true;
			}catch (IOException e) {
				LOG.info(e.getMessage());
			}
		}else {
			LOG.info("Can't undo because command has not executed yet.");
			throw new CommandException("Can't undo because command has not executed yet.");
		}
	}

	/**
	 * Vuelve a modificar los valores de la sustitucion
	 * @throws IOException 
	 * @throws CommandException 
	 */
	@Override
	public void redo() throws IOException, CommandException {
		if (this.undone){
			try{
				//Vuelve a cambiar el CVV por el nuevo
				this.card.setCvv(newCvv);
				//Vuelve a cambiar el PIN por el nuevo
				this.card.setPin(newPin);
				//Vuelve a cambiar la fecha de caducidad por la nueva
				this.card.setExpirationDate(newExpirationDate);
			}catch (IOException e) {
				LOG.info(e.getMessage());
			}
		} else {
			LOG.info("Can't undo because command has not undoned yet.");
			throw new CommandException("Can't undo because command has not undoned yet.");
		}
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
