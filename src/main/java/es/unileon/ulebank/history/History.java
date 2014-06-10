package es.unileon.ulebank.history;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import es.unileon.ulebank.handler.Handler;

/**
 *
 * @author roobre
 * @param <T>
 */
public class History<T extends Transaction> {

    /**
     * Collection to store the transactions
     */
    private final Collection<T> transactions;

    /**
     * Create a new history
     */
    public History() {
        this.transactions = new ArrayList<T>();
    }

    /**
     *
     * @param transaction
     * @return
     */
    public boolean add(T transaction) {
        boolean found = false;
        final Iterator<T> it = this.transactions.iterator();
        while (it.hasNext() && !found) {
            final T t = it.next();
            if (t.getId().compareTo(transaction.getId()) == 0) {
                found = true;
            }
        }
        if (!found) {
            return this.transactions.add(transaction);
        }
        return false;

    }

    /**
     * Get the history iterator
     *
     * @return
     */
    public Iterator<T> getIterator() {
        return this.transactions.iterator();
    }

    /**
     * Remove a transaction
     *
     * @param id
     *            (transaction's id )
     *
     * @return ( true if success, false otherwise )
     */
    public boolean remove(Handler id) {
        boolean found = false;
        final Iterator<T> it = this.transactions.iterator();
        while (it.hasNext() && !found) {
            final T t = it.next();
            if (t.getId().compareTo(id) == 0) {
                it.remove();
                found = true;
            }
        }
        return found;
    }
}
