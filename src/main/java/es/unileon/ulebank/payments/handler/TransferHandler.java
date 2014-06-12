package es.unileon.ulebank.payments.handler;

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
public class TransferHandler implements Handler {

    /**
     * Identifier of the handler
     */
    private final String id;
    /**
     * Date of the transfer
     */
    private String date;
    /**
     * Sender account of the transfer
     */
    private final String sender;
    /**
     * Receiver account of the transfer
     */
    private final String receiver;
    /**
     * Calendar for obtain the date of the transfer
     */
    private final Calendar calendar;

    /**
     * Class constructor
     * 
     * @param sender
     * @param receiver
     */
    public TransferHandler(String sender, String receiver) {
        this.sender = sender.substring(sender.length() / 2);
        this.receiver = receiver.substring(receiver.length() / 2);
        this.calendar = new GregorianCalendar();
        this.setDateCode();
        this.id = this.sender + this.receiver + this.date;
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
