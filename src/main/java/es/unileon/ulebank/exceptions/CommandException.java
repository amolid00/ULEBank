package es.unileon.ulebank.exceptions;

/**
 * Generic exception that extends Exception and all exceptions depends that
 * @author Israel
 */
public class CommandException extends Exception {

    private static final long serialVersionUID = 1L;
    
    /**
     * Creates new Generic Ulebank Exception
     * @param msg
     */
    public CommandException(String msg) {
        super(msg);
    }
}
