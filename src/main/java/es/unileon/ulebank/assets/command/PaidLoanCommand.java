package es.unileon.ulebank.assets.command;

import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.exceptions.LoanException;
import es.unileon.ulebank.command.Command;
import es.unileon.ulebank.handler.Handler;

public class PaidLoanCommand implements Command{
	Handler id;
	Loan loan;
	
	
	public PaidLoanCommand(Handler id, Loan loan) {
		this.id = id;
		this.loan = loan;
	}

	@Override
	public Handler getID() {
		
		return this.id;
	}

	@Override
	public void execute() {
		try {
			this.loan.paid();
		} catch (LoanException e) {
		}
		
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

}
