/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.account;

import java.util.ArrayList;
import java.util.List;

import es.unileon.ulebank.handler.Handler;

/**
 *
 * @author runix
 */
public class AccountDirectDebits {

    private final List<DirectDebit> directDebits;

    public AccountDirectDebits() {
        this.directDebits = new ArrayList<DirectDebit>();
    }

    public boolean addDirectDebit(DirectDebit d) {
        int i = -1;
        boolean duplicated = false;
        while ((++i < this.directDebits.size()) && !duplicated) {
            if (this.directDebits.get(i).getDirectDebitID()
                    .compareTo(d.getDirectDebitID()) == 0) {
                duplicated = true;
            }
        }
        if (!duplicated) {
            return this.directDebits.add(d);
        }
        return false;
    }

    public boolean removeDirectDebit(Handler id) {
        int i = -1;
        boolean removed = false;
        while ((++i < this.directDebits.size()) && !removed) {
            if (this.directDebits.get(i).getDirectDebitID().compareTo(id) == 0) {
                this.directDebits.remove(i);
                removed = true;
            }
        }
        return removed;
    }
}
