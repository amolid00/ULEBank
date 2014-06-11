/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.history.iterator.IteratorBetweenTwoDates;

/**
 *
 * @author runix
 */
public class CommandFilterTransactionByDates<T extends Transaction> implements
        Command {

    private Iterator<T> iteratorFiltered;
    private List<T> result;
    private final Handler commandId;

    public CommandFilterTransactionByDates(Date min, Date max,
            List<T> transactions, Handler commandId) throws WrongArgsException {
        this(min, max, transactions.iterator(), commandId);
    }

    public CommandFilterTransactionByDates(Date min, Date max,
            Iterator<T> iterator, Handler commandId) throws WrongArgsException {
        this.commandId = commandId;
        this.result = new ArrayList<T>();
        this.iteratorFiltered = new IteratorBetweenTwoDates<T>(iterator,
                min.getTime(), max.getTime());
    }

    @Override
    public Handler getID() {
        return this.commandId;
    }

    @Override
    public void execute() {
        if (this.result.isEmpty()) {
            while (this.iteratorFiltered.hasNext()) {
                this.result.add(this.iteratorFiltered.next());
            }
        }
    }

    public List<T> getList() {
        return this.result;
    }

    @Override
    public void undo() {
        // it makes no sense
    }

    @Override
    public void redo() {
        // it makes no sense
    }

}
