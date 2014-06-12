package es.unileon.ulebank.payments.exceptions;

/**
 * Exception thrown when Card is not found
 * 
 * @author Rober dCR
 *
 */
public class CardNotFoundException extends Exception {

    /**
     * Version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase
     * 
     * @param message
     */
    public CardNotFoundException(String message) {
        super(message);
    }
}
