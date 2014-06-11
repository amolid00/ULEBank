package es.unileon.ulebank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.unileon.ulebank.command.Command;
import es.unileon.ulebank.command.ModifyBuyLimitCommand;
import es.unileon.ulebank.command.ModifyCashLimitCommand;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.repository.dao.CardsDao;

/**
 * @author Israel
 * Funciona como mediador entre el controlador y la clase que gestiona el almacenamiento
 */
@Component
public class SimpleCardManager implements CardManager {

	private static final long serialVersionUID = 1L;
	/**
	 * Atributo para realizar las operaciones de acceso a la base de datos
	 */
	@Autowired
	private CardsDao cardDao;
	/**
	 * Guarda la ultima tarjeta agnadida a la base de datos
	 */
	private Card lastCard;
	
	/**
	 * Obtiene la lista de tarjetas de la base de datos
	 */
	@Override
	public List<Card> getCards(Handler dni) {
		return cardDao.getCards(dni);
	}

	/**
	 * Guarda la tarjeta recibida en la base de datos
	 */
	public void saveNewCard(Card card) {
		this.lastCard = card;
		this.cardDao.persist(card);
	}
	
	/**
	 * Cambia el atributo de acceso al almacenamiento por el recibido
	 * @param cardDao
	 */
	public void setCardDao(CardsDao cardDao) {
		this.cardDao = cardDao;
	}

	/**
	 * Devuelve la ultima tarjeta almacenada en la memoria o base de datos
	 * @return
	 */
	public Card getLastCard() {
		return lastCard;
	}

	@Override
	public void changeBuyLimits(double diary, double monthly, Handler handler)
			throws Exception {
		Card card = this.cardDao.findById(handler);
		Command buyLimitsDiary = new ModifyBuyLimitCommand(card.getId(), card, diary, "diary");
		Command buyLimitsMonthly = new ModifyBuyLimitCommand(card.getId(), card, monthly, "monthly");
		buyLimitsMonthly.execute();
		buyLimitsDiary.execute();
		this.cardDao.merge(card);
	}

	@Override
	public void changeCashLimits(double diary, double monthly, Handler handler)
			throws Exception {
		Card card = this.cardDao.findById(handler);
		Command cashLimitsDiary = new ModifyCashLimitCommand(card.getId(), card, diary, "diary");
		Command cashLimitsMonthly = new ModifyCashLimitCommand(card.getId(), card, monthly, "monthly");
		cashLimitsMonthly.execute();
		cashLimitsDiary.execute();
		this.cardDao.merge(card);
	}

	public void setCard(Card card) {
		this.lastCard = card;
	}

}
