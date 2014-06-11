package es.unileon.ulebank.exceptions;

/**
 * Commission Exception Class
 * @author Rober dCR
 * @date 29/04/2014
 * @brief Exception that is thrown if the commission conditions are not accepted
 */
public class CommissionException extends Exception {

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor
	 * @param message
	 */
	public CommissionException(String message) {
		super(message);
	}
}
