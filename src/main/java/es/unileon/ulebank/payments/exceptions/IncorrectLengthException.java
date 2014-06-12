package es.unileon.ulebank.payments.exceptions;


public class IncorrectLengthException extends PaymentException {
    
    private static final long serialVersionUID = 1L;

    public IncorrectLengthException(String msg) {
        super(msg);
    }
}
