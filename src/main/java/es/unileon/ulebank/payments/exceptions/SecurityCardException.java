package es.unileon.ulebank.payments;

/**
 * SecurityCardException Class
 * 
 * @author Rober dCR
 * @date 26/03/2014
 * @brief Exception that is thrown if the security conditions are not accepted
 */
public class SecurityCardException extends Exception {

    /**
     * Version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Class constructor
     * 
     * @param message
     */
    public SecurityCardException(String message) {
        super(message);
    }

}
