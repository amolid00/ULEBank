package es.unileon.ulebank.exceptions;

/**
 * PaymentException Class
 * @author Rober dCR
 * @date 07/05/2014
 * @brief  Exception that is thrown if the payment conditions are not accepted
 */
public class PaymentException extends Exception{

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor
	 * @param message
	 */
	public PaymentException(String message){
		super(message);
	}
}
