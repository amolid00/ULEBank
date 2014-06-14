package es.unileon.ulebank.office;

import java.io.Serializable;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

/**
 * 
 * @author Patricia
 * 
 */
public class OfficeHandler implements Handler, Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id of the office
     */
    private String id;

    public OfficeHandler(int number) throws MalformedHandlerException {

        if (number >= 0) {

            if (Integer.toString(number).length() == 4) {
                this.id = Integer.toString(number);
            } else {
                if (Integer.toString(number).length() < 4) {
                    this.id = Integer.toString(number);
                    while (this.id.length() <= 4) {
                        this.id = 0 + this.id;
                    }
                } else {
                    throw new MalformedHandlerException(
                            "The idOffice is malformed");
                }
            }
        } else {
            throw new MalformedHandlerException(
                    "The idOffice has to be a positive number");
        }
    }

    public OfficeHandler(String numberOffice) throws MalformedHandlerException {
        try {
            Integer.parseInt(numberOffice);
        } catch (final NumberFormatException e) {
            throw new MalformedHandlerException(
                    "The idOffice has to be a number");
        }

        if (Integer.parseInt(numberOffice) >= 0) {
            if (numberOffice.length() == 4) {
                this.id = numberOffice;
            } else {
                if (numberOffice.length() < 4) {
                    while (numberOffice.length() <= 4) {
                        numberOffice = 0 + numberOffice;
                    }
                } else {
                    throw new MalformedHandlerException(
                            "The idOffice is malformed");
                }
            }
        } else {
            throw new MalformedHandlerException(
                    "The idOffice has to be a positive number");
        }
    }

    public OfficeHandler() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(Handler another) {
        return this.id.compareTo(another.toString());
    }

    @Override
    public String toString() {
        return this.id;
    }

}
