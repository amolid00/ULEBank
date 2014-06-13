package es.unileon.ulebank.assets.service;

import java.util.List;

import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.strategy.loan.ScheduledPayment;
import es.unileon.ulebank.handler.Handler;

public interface Manager {

	public void setAmortization(double quantity, Handler loanId);

	public void setAmortization(double quantity);

	public void setNumberOfFees(int numFees, Handler loanId);

	public void setNumberOfFees(int numFees);

	public List<ScheduledPayment> getPayments();

	public List<Loan> getLoans();

	void saveLoan(Loan loan);
}
