package es.unileon.ulebank.assets.repository;

import java.util.List;

import es.unileon.ulebank.assets.Loan;

public interface LoanDao {
	public List<Loan> getLoanList();
	public void saveLoan(Loan loan);
}
