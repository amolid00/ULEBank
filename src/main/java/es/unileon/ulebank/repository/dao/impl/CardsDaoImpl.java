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

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.repository.dao.impl.CardsDao#persist(es.unileon.ulebank
     * .repository.domain.Cards)
     */
    @Override
    @Transactional(readOnly = false)
    public void persist(Card transientInstance) {
        CardsDaoImpl.log.debug("persisting Cards instance");
        try {
            this.entityManager.persist(transientInstance);
            CardsDaoImpl.log.debug("persist successful");
        } catch (final RuntimeException re) {
            CardsDaoImpl.log.error("persist failed", re);
            throw re;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.repository.dao.impl.CardsDao#remove(es.unileon.ulebank
     * .repository.domain.Cards)
     */
    @Override
    @Transactional(readOnly = false)
    public void remove(Card persistentInstance) {
        CardsDaoImpl.log.debug("removing Cards instance");
        try {
            persistentInstance = this.findById(persistentInstance.getId());
            this.entityManager.remove(persistentInstance);
            CardsDaoImpl.log.debug("remove successful");
        } catch (final RuntimeException re) {
            CardsDaoImpl.log.error("remove failed", re);
            throw re;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.repository.dao.impl.CardsDao#merge(es.unileon.ulebank
     * .repository.domain.Cards)
     */
    @Override
    @Transactional(readOnly = false)
    public Card merge(Card detachedInstance) {
        CardsDaoImpl.log.debug("merging Cards instance");
        try {
            final Card result = this.entityManager.merge(detachedInstance);
            CardsDaoImpl.log.debug("merge successful");
            return result;
        } catch (final RuntimeException re) {
            CardsDaoImpl.log.error("merge failed", re);
            throw re;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.repository.dao.impl.CardsDao#findById(es.unileon.ulebank
     * .repository.domain.CardsId)
     */
    @Override
    @Transactional
    public Card findById(Handler handler) {
        CardsDaoImpl.log.debug("getting Cards instance with id: "
                + handler.toString());
        try {
            final Card instance = this.entityManager.find(Card.class,
                    handler.toString());
            CardsDaoImpl.log.debug("get successful");
            return instance;
        } catch (final RuntimeException re) {
            CardsDaoImpl.log.error("get failed", re);
            throw re;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<Card> getCards(Handler dni) {
        final String clientId = dni.toString();
        final List<Card> result = new ArrayList<Card>();
        final Query query = this.entityManager
                .createQuery("select c from DebitCard c where clientId=:clientId order by c.id");
        query.setParameter("clientId", clientId);
        result.addAll(query.getResultList());
        this.entityManager.close();
        return result;
    }
}
