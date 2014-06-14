package es.unileon.ulebank.assets.handler;

import java.io.Serializable;

import es.unileon.ulebank.assets.handler.exceptions.LINCMalformedException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

public class FinancialProductHandler implements Handler, Serializable {
	
	/**
	 * Loan identification number.
	 */
    

	private String id;
	
	/**
	 * Empty constructor to persistence
	 */
	public FinancialProductHandler(){
	    
	}
	
	public FinancialProductHandler(String type, String countryCode) throws MalformedHandlerException{
		try {
			this.id = new LoanIdentificationNumberCode(type, countryCode).toString();
		} catch (LINCMalformedException e) {
			throw new MalformedHandlerException(e.getMessage());
		}
	}
	
	/**
	 * Handler which is passed an instance of the class LoanIdentificationNumberCode responsible 
	 * for generating the identification number.
	 * @param linc Instance of LoanIdentificationNumberCode
	 * @throws MalformedHandlerException 
	 */
	public FinancialProductHandler(LoanIdentificationNumberCode linc) throws MalformedHandlerException {
		this(linc.getType(), linc.getCountryCode());
	}
	
	
	public int compareTo(Handler anotherHandler) {
		return this.id.compareTo(anotherHandler.toString());
	}
	
	@Override
	public String toString() {
		return this.id;
	}

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;  
    }
}
