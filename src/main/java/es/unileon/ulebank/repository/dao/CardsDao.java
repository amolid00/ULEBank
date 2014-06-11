package es.unileon.ulebank.repository.dao;

import java.util.List;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.Card;


public interface CardsDao {

	public void persist(Card transientInstance);

	public void remove(Card persistentInstance);

	public Card merge(Card detachedInstance);

	public Card findById(Handler handler);
	
	public List<Card> getCards(Handler dni);
}