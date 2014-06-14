/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.client;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.handler.Handler;

/**
 * Class tha provides the basic gestion data of a client in a bank
 * 
 * @author Gonzalo Nicolas Barreales
 */
@Entity
@Table(name = "CLIENTS", catalog = "ULEBANK_FINAL")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
public class Client {

    /**
     * Identifier of the client
     */
    private Handler id;

    /**
     * Accounts where the client appear
     */
    private List<Account> accounts;

    /**
     * Constructor of client. Receive the id and initilize the list of accounts
     * 
     * @param clientHandler
     */
    protected Client(Handler clientHandler) {
        this.accounts = new ArrayList<Account>();
        this.id = clientHandler;
    }

    public Client() {
        this.accounts = new ArrayList<Account>();
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ACCOUNTS_CLIENTS", catalog = "ULEBANK_FINAL", joinColumns = { @JoinColumn(name = "client_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "account_number", nullable = false, updatable = false) })
    public List<Account> getAccounts() {
        return this.accounts;
    }

    /**
     * @return id of the client
     */
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    public Handler getId() {
        return this.id;
    }
    
    public String toString(){
        return this.id.toString();
    }
}
