package es.unileon.ulebank.office;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

/**
 * 
 * @author Patricia
 * 
 */
public class OfficeHandler implements Handler {

    private String numberOffice;

    public OfficeHandler(int number) throws MalformedHandlerException {

        if (number >= 0) {

            if (Integer.toString(number).length() == 4) {
                this.numberOffice = Integer.toString(number);
            } else {
                if (Integer.toString(number).length() < 4) {
                    this.numberOffice = Integer.toString(number);
                    while (this.numberOffice.length() <= 4) {
                        this.numberOffice = 0 + this.numberOffice;
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
                this.numberOffice = numberOffice;
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

    public int getIdOffice() {
        return Integer.parseInt(this.numberOffice);
    }

    @Override
    public int compareTo(Handler another) {
        return this.numberOffice.compareTo(another.toString());
    }

    @Override
    public String toString() {
        return this.numberOffice;
    }

}
