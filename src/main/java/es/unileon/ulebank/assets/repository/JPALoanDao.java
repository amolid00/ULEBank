package es.unileon.ulebank.assets.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import es.unileon.ulebank.assets.Loan;

public class JPALoanDao implements LoanDao{
	private EntityManager em;

	/*
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

	@Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Loan> getLoanList() {
        return em.createQuery("select p from Loan p order by p.id").getResultList();
    }

    @Transactional(readOnly = false)
    public void saveLoan(Loan loan) {
        em.merge(loan);
    }
}
