package es.unileon.ulebank.payments.handler;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import es.unileon.ulebank.handler.Handler;

/**
 * Class of Transfer Handler
 * 
 * @author Rober dCR
 * @date 9/04/2014
 * @brief Identifier of Transfer
 */
public class TransferHandler implements Handler, Serializable {

    /**
     * Serial Version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identifier of the handler
     */
    private String id;
    /**
     * Date of the transfer
     */
    private String date;
    /**
     * Calendar for obtain the date of the transfer
     */
    private Calendar calendar;

    /**
     * Class constructor
     * 
     * @param sender
     * @param receiver
     */
    public TransferHandler(String sender, String receiver) {
        this.calendar = new GregorianCalendar();
        this.setDateCode();
        this.id = sender.substring(sender.length() / 2)
                + receiver.substring(receiver.length() / 2) + this.date;
    }

    public TransferHandler() {
    }

    /**
     * Compares one handler with other
     * 
     * @return 0 if both are equals any other number if not equals
     */
    @Override
    public int compareTo(Handler another) {
        return this.toString().compareTo(another.toString());
    }

    /**
     * To String class method
     */
    @Override
    public String toString() {
        return this.id;
    }

    /**
     * Getter id
     * 
     * @return
     */
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method that obtains the code from the date
     */
    private void setDateCode() {
        this.date = this.calendar.get(Calendar.DAY_OF_MONTH)
                + Integer.toString(this.calendar.get(Calendar.MONTH) + 1)
                + this.calendar.get(Calendar.YEAR)
                + this.calendar.get(Calendar.HOUR_OF_DAY)
                + this.calendar.get(Calendar.MINUTE)
                + this.calendar.get(Calendar.SECOND);
    }
}
