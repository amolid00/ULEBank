/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.handler.Handler;

/**
 * Class tha provides the basic gestion data of a client in a bank
 * 
 * @author Gonzalo Nicolas Barreales
 */
public class Client {

    /**
     * Identifier of the client
     */
    private final Handler id;

    /**
     * Accounts where the client appear
     */
    private final List<Account> accounts;

    /**
     * Constructor of client. Receive the id and initilize the list of accounts
     * 
     * @param clientHandler
     */
    protected Client(Handler clientHandler) {
        this.accounts = new ArrayList<Account>();
        this.id = clientHandler;
    }

    /**
     * Adds an account to the list of clients. If the account exists, it won't
     * be added
     * 
     * @param account
     */
    public void add(Account account) {
        if (!this.accounts.contains(account)) {
            this.accounts.add(account);
        }
    }

    /**
     * Remove the account identified with acountHandler
     * 
     * @param accountHandler
     * @return true if account is deleted, false if account doesn't exists
     */
    public boolean removeAccount(Handler accountHandler) {
        boolean removed = false;
        final Iterator<Account> iterator = this.accounts.iterator();
        while (!removed && iterator.hasNext()) {
            final Account account = iterator.next();
            if (account.getID().compareTo(accountHandler) == 0) {
                removed = true;
                iterator.remove();
            }
        }

        return removed;
    }

    /**
     * Check if the account idientified with account Handler exists
     * 
     * @param accountHandler
     * @return true if the account exists, false if it doesn't exists
     */
    public boolean existsAccount(Handler accountHandler) {
        boolean result = false;
        final Iterator<Account> iterator = this.accounts.iterator();
        while (iterator.hasNext()) {
            final Account account = iterator.next();
            if (account.getID().compareTo(accountHandler) == 0) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Search account with received handler into the list
     * 
     * @param handler
     * @return
     */
    public Account searchAccount(Handler handler) {
        final Iterator<Account> iterator = this.accounts.iterator();
        Account account = null;

        if (this.accounts.isEmpty()) {
            throw new NullPointerException("Account list is empty.");
        }

        while (iterator.hasNext()) {
            account = iterator.next();

            if (account.getID().compareTo(handler) == 0) {
                break;
            }
        }

        return account;
    }

    /**
     * Returns account list
     * 
     * @return
     */
    public List<Account> getAccounts() {
        return this.accounts;
    }

    /**
     * @return id of the client
     */
    public Handler getId() {
        return this.id;
    }
}
