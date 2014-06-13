package es.unileon.ulebank.assets.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.exceptions.LoanException;
import es.unileon.ulebank.assets.repository.LoanDao;
import es.unileon.ulebank.assets.strategy.loan.ScheduledPayment;
import es.unileon.ulebank.handler.Handler;

@Component
public class SimpleManagerLoan implements Manager {

	@Autowired
	private LoanDao loanDao;

	/**
	 * Realiza la amortizacion de todos los prestamos
	 */
	@Override
	public void setAmortization(double quantity) {
		List<Loan> loans = this.loanDao.getLoanList();
		if (loans != null) {
			for (Loan loan : loans) {
				try {
					loan.amortize(quantity);
					this.loanDao.saveLoan(loan);
				} catch (LoanException e) {

				}
			}
		}

	}

	@Override
	public List<ScheduledPayment> getPayments() {
		List<Loan> loans = this.loanDao.getLoanList();
		ArrayList<ScheduledPayment> payments = new ArrayList<ScheduledPayment>();

		if (loans != null) {
			if (loans.size() > 0) {
				for (Loan loan : loans) {
					payments.addAll(loan.getPayments());
				}
			}
		}

		return payments;
	}

	@Override
	public List<Loan> getLoans() {
		return this.loanDao.getLoanList();
	}

	@Override
	public void setAmortization(double quantity, Handler loanId) {
		List<Loan> loans = this.loanDao.getLoanList();

		boolean found = false;

		// buscamos el loan concreto
		Iterator<Loan> iterator = loans.iterator();
		while (!found && iterator.hasNext()) {
			Loan foundLoan = iterator.next();
			// si lo encontrammos realizamos la amortizacion
			if (foundLoan.getId().compareTo(loanId) == 0) {
				found = true;
				try {
					foundLoan.amortize(quantity);
					this.loanDao.saveLoan(foundLoan);
				} catch (LoanException e) {
				}

			}
		}
	}

	/**
	 * Cambia las cuotas de un prestamo concreto
	 */
	@Override
	public void setNumberOfFees(int numFees, Handler loanId) {
		List<Loan> loans = loanDao.getLoanList();
		boolean found = false;

		Iterator<Loan> iterator = loans.iterator();
		while (!found && iterator.hasNext()) {
			Loan foundLoan = iterator.next();

			if (foundLoan.getId().compareTo(loanId) == 0) {
				found = true;
				foundLoan.setAmortizationTime(numFees);
				loanDao.saveLoan(foundLoan);

			}
		}
	}

	/**
	 * Cambia las cuotas de todos los prestamos
	 */
	@Override
	public void setNumberOfFees(int numFees) {
		List<Loan> loans = loanDao.getLoanList();
		if (loans != null) {
			for (Loan loan : loans) {
				loan.setAmortizationTime(numFees);
				loanDao.saveLoan(loan);
			}
		}
	}
	
	@Override
	public void saveLoan(Loan loan) {
		this.loanDao.saveLoan(loan);
	}
	
	public LoanDao getLoanDao() {
		return loanDao;
	}

	public void setLoanDao(LoanDao loanDao) {
		this.loanDao = loanDao;
	}

}
