package es.unileon.ulebank.exceptions;

/**
 * TransferException Class
 * @author Rober dCR
 * @date 28/04/2014
 * @brief  Exception that is thrown if the transfer conditions are not accepted
 */
public class TransferException extends Exception{

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor
	 * @param message
	 */
	public TransferException(String message){
		super(message);
	}
}
