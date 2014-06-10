/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.account;

import es.unileon.ulebank.handler.Handler;

/**
 *
 * @author runix
 */
public class DirectDebit {

    private final Handler directDebitID;

    public DirectDebit(Handler directDebitID) {
        this.directDebitID = directDebitID;
    }

    public Handler getDirectDebitID() {
        return this.directDebitID;
    }
}
