/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.bank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

/**
 *
 * @author runix
 */
public class BankHandler implements Handler {

    /**
     * The number of digits
     */
    private static final int BANK_NUMBER_DIGITS = 4;
    /**
     * Bank's number
     */
    private final String number;

    /**
     * Create a new Bank handler
     *
     * @param number
     *            ( The number )
     * @throws MalformedHandlerException
     *             (If the bank isn't correct )
     */
    public BankHandler(String number) throws MalformedHandlerException {
        final Pattern numberPattern = Pattern.compile("^[0-9]*$");
        final Matcher matcher = numberPattern.matcher(number);
        if (matcher.find()
                && (number.length() == BankHandler.BANK_NUMBER_DIGITS)) {
            this.number = number;
        } else {
            final String error = "Error, the number hasn't "
                    + BankHandler.BANK_NUMBER_DIGITS
                    + " digits or has letters \n";
            throw new MalformedHandlerException(error);
        }
    }

    @Override
    public int compareTo(Handler another) {
        return this.toString().compareTo(another.toString());
    }

    /**
     *
     * @return ( Return the number)
     */
    @Override
    public String toString() {
        return this.number;
    }
}
