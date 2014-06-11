package es.unileon.ulebank.fees;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.CommissionException;

/**
 * @class StrategyCommissionDebitMaintenance
 * @author Rober dCR
 * @date 19/03/2014
 * @brief Class that obtain Commision of Maintenance in Debit Cards
 */
public class DebitMaintenanceFee implements FeeStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Card owner
	 */
	private Client owner;
	
	/**
	 * Commission establish by the employee
	 */
	private double quantity;
	
	/**
	 * Maximum age to differentiate commissions
	 */
	private int maximum_age;
	
	/**
	 * Quantity of the default commission
	 */
	private float default_commission;
	
	/**
	 * String for obtain the maximum age
	 */
	private static final String AGE = "debit_age";
	
	/**
	 * String for obtain the default commission
	 */
	private static final String COMMISSION = "debit_maintenance";
	
	/**
	 * Class constructor
	 * @param owner
	 * @param quantity
	 * @throws CommissionException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
    public DebitMaintenanceFee(Client owner, double quantity) throws CommissionException, NumberFormatException, IOException {
    	this.owner = owner;
		this.setDefaultCommission();
		this.setMaximumAge();
		
		if (quantity >= 0) {
			this.quantity = quantity;
		} else {
			throw new CommissionException("Commission can't been negative.");
		}
    }

    /**
	 * Setter of maximum_age
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private void setMaximumAge() throws NumberFormatException, IOException{

		try {
			Properties ageProperty = new Properties();
			ageProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
			
			/**Obtenemos los parametros definidos en el archivo*/
			this.maximum_age = Integer.parseInt(ageProperty.getProperty(DebitMaintenanceFee.AGE));
			
		} catch(FileNotFoundException e){
			throw new FileNotFoundException("The file card.properties is not found.");
		}catch (IOException e2) {
			throw new IOException("Fail to try open or close file card.properties");
		}
	}

	/**
	 * Setter of default_commission
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	private void setDefaultCommission() throws NumberFormatException, IOException{
		
		try {
			Properties commissionProperty = new Properties();
			commissionProperty.load(new FileInputStream("src/es/unileon/ulebank/properties/card.properties"));
			
			/**Obtenemos los parametros definidos en el archivo*/
			this.default_commission = Float.parseFloat(commissionProperty.getProperty(DebitMaintenanceFee.COMMISSION));
		} catch(FileNotFoundException e) {
			throw new FileNotFoundException("The file card.properties is not found.");
		} catch (IOException e2) {
			throw new IOException("Fail to try open or close file card.properties");
		}
		
	}

    @Override
    public double getFee(double value) {
        if (((Person) this.owner).getAge() > this.maximum_age) {
            return this.default_commission;
        } else {
            return quantity;
        }
    }

}
