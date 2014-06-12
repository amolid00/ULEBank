package es.unileon.ulebank.googlefinances;

public class ElementNotFoundException extends Exception {
    public ElementNotFoundException(String element) {
        super("The value \"" + element + "\" can't be recovered.");
    }
}
