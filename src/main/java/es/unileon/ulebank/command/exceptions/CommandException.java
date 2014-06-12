package es.unileon.ulebank.command.exceptions;

/**
 * @author Israel Excepcion que se lanza cuando un comando no se ejecuta
 *         correctamente
 */
public class CommandException extends Exception {
    /**
     * Version
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la excepcion que transmite el mensaje recibido a su super
     * clase
     * 
     * @param message
     */
    public CommandException(String message) {
        super(message);
    }
}
