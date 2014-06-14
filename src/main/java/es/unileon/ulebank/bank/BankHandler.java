/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.bank;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Id;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

/**
 *
 * @author runix
 */
public class BankHandler implements Handler, Serializable {

    /**
     * The number of digits
     */
    private static final int BANK_NUMBER_DIGITS = 4;
    /**
     * Bank's number
     */
    private String id;
    
    public BankHandler(){
        
    }

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
            this.id = number;
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
        return this.id;
    }

    @Override
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 64)
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
