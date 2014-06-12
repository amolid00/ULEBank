package es.unileon.ulebank.assets.command;

//import com.sun.org.apache.xpath.internal.operations.Variable;

import es.unileon.ulebank.assets.VariableLoan;
import es.unileon.ulebank.command.Command;
import es.unileon.ulebank.handler.Handler;

public class UpdateInterestCommand implements Command{
	private Handler id;
	
	private VariableLoan loan;
	
	public UpdateInterestCommand(VariableLoan loan,Handler genericHandler){
		this.id=genericHandler;
		this.loan=loan;
		
	}
	
	@Override
	public void execute() {
		this.loan.update();
		this.loan.recalcInterestRate();
		
	}

	@Override
	public void undo() {
		
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Handler getID() {
		// TODO Auto-generated method stub
		return id;
	}

	

}
