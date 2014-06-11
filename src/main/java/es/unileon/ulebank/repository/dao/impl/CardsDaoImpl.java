package es.unileon.ulebank.repository.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.repository.dao.CardsDao;

/**
 * Home object for domain model class Cards.
 * 
 * @see es.unileon.ulebank.repository.domain.Cards
 */
@Stateless
@Repository(value = "cardsDao")
public class CardsDaoImpl implements CardsDao {

	private static final Log log = LogFactory.getLog(CardsDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see es.unileon.ulebank.repository.dao.impl.CardsDao#persist(es.unileon.ulebank.repository.domain.Cards)
	 */
	@Override
	@Transactional(readOnly = false)
	public void persist(Card transientInstance) {
		log.debug("persisting Cards instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see es.unileon.ulebank.repository.dao.impl.CardsDao#remove(es.unileon.ulebank.repository.domain.Cards)
	 */
	@Override
	@Transactional(readOnly = false)
	public void remove(Card persistentInstance) {
		log.debug("removing Cards instance");
		try {
			persistentInstance = findById(persistentInstance.getId());
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see es.unileon.ulebank.repository.dao.impl.CardsDao#merge(es.unileon.ulebank.repository.domain.Cards)
	 */
	@Override
	@Transactional(readOnly = false)
	public Card merge(Card detachedInstance) {
		log.debug("merging Cards instance");
		try {
			Card result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see es.unileon.ulebank.repository.dao.impl.CardsDao#findById(es.unileon.ulebank.repository.domain.CardsId)
	 */
	@Override
	@Transactional
	public Card findById(Handler handler) {
		log.debug("getting Cards instance with id: " + handler.toString());
		try {
			Card instance = entityManager.find(Card.class, handler.toString());
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional (readOnly = true)
	public List<Card> getCards(Handler dni) {
		String clientId = dni.toString();
		List<Card> result = new ArrayList<Card>();
		Query query = entityManager.createQuery("select c from DebitCard c where clientId=:clientId order by c.id");
		query.setParameter("clientId", clientId);
		result.addAll(query.getResultList());
		entityManager.close();
		return result;
	}
}
