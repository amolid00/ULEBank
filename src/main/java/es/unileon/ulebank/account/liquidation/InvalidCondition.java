package es.unileon.ulebank.account.liquidation;

/**
 * Invalid condition
 * 
 * @author runix
 *
 */
public class InvalidCondition extends Exception {

    /**
     * Create a new Exception
     * 
     * @param msg
     */
    public InvalidCondition(String msg) {
        super(msg);
    }
}
