package es.unileon.ulebank.client;

/**
 * @author Israel Garcia Centeno Excepcion que se lanza cuando el cliente no se
 *         encuentra registrado en la sucursal
 */
public class ClientNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la clase
     * 
     * @param message
     */
    public ClientNotFoundException(String message) {
        super(message);
    }
}
