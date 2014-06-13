package es.unileon.ulebank.assets.service;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FeeChanges {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Valor minimo de 3 cuotas y maximo de 20 años 
	 * (No hay ningun valor fijado para esto)
	 */
	@Min(3)
	@Max(240)
	private int numberOfFees;

	public void setNumberOfFees(int i) {
		numberOfFees = i;
		logger.info("Number of fees set to " + i);
	}

	public int getNumberOfFees() {
		return numberOfFees;
	}

}