package es.unileon.ulebank.command.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.unileon.ulebank.handler.Handler;

/**
 * CommandHandler class
 * 
 * @author Rober dCR
 * @date 9/04/2014
 * @brief Class of the identifier for Commands
 */
public class CommandHandler implements Handler {

    /**
     * Identifier of the object which makes the command
     */
    private final Handler id;
    /**
     * Date when the commandHandler is created
     */
    private final String date;

    /**
     * Class constructor
     * 
     * @param handler
     */
    public CommandHandler(Handler handler) {
        this.id = handler;
        final DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssss");
        this.date = dateFormat.format(new Date());
    }

    /**
     * Compara el identificador actual con el que se indica
     * 
     * @param another
     * @return devuelve un 0 si son iguales
     * @return devuelve otro numero si son distintos
     */
    @Override
    public int compareTo(Handler another) {
        return this.toString().compareTo(another.toString());
    }

    /**
     * Getter id
     * 
     * @return id
     */
    public Handler getId() {
        return this.id;
    }

    /**
     * Getter date
     * 
     * @return String
     */
    public String getDate() {
        return this.date.toString();
    }

    /**
     * Devuelve en una cadena de strings el id y la fecha
     */
    @Override
    public String toString() {
        return this.id.toString() + " " + this.date.toString();
    }
}
