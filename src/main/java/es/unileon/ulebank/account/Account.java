package es.unileon.ulebank.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import es.unileon.ulebank.account.liquidation.AbstractLiquidationFee;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.DirectDebitTransaction;
import es.unileon.ulebank.history.GenericTransaction;
import es.unileon.ulebank.history.History;
import es.unileon.ulebank.history.Transaction;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.time.Time;

/**
 *
 * @author runix
 */
public class Account {

    /**
     * The logger of the class
     */
    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    /**
     * The default liquidation frecuency
     */
    public static final int DEFAULT_LIQUIDATION_FREQUENCY = 6;
    /**
     * The account identifier
     */
    private final Handler id;

    /**
     * The account balance
     */
    private double balance;
    /**
     * The account titulars
     */
    private final List<Client> titulars;
    /**
     * The account authorizeds
     */
    private final List<Client> authorizeds;
    /**
     * The cards for this account
     */
    private List<Card> cards;
    /**
     * The account's history
     */
    private final History<Transaction> history;
    /**
     * The account's direct debit history
     */
    private final History<DirectDebitTransaction> directDebitHistory;
    /**
     * Failed liquidation transactions
     */
    private final History<Transaction> failedTransactionsHistory;
    /**
     * The last liquidation
     */
    private Date lastLiquidation;

    /**
     * The liquidation frequency in months
     */
    private int liquidationFrecuency;
    /**
     * The max account's overdraft ( in positive )
     */
    private double maxOverdraft;

    /**
     * Account's direct debits
     */
    private AccountDirectDebits directDebits;

    private List<AbstractLiquidationFee> liquidationFees;

    /**
     * Create a new account
     *
     * @param office
     *            (The office of the account)
     *
     * @param bank
     *            ( The bank of the office )
     *
     * @param accountnumber
     *            (the accountNumber)
     * @param authorized
     *
     * @throws es.unileon.ulebank.handler.MalformedHandlerException
     * @throws es.unileon.ulebank.history.conditions.WrongArgsException
     *
     */
    public Account(Office office, Bank bank, String accountnumber,
            Client authorized) throws MalformedHandlerException,
            WrongArgsException {
        this.id = new AccountHandler(office.getIdOffice(), bank.getID(),
                accountnumber);
        this.titulars = new ArrayList<Client>();
        if (!this.addTitular(authorized)) {
            throw new WrongArgsException(
                    "There were a problem when add the authorized, may be it is null\n");
        } else {
            this.history = new History<Transaction>();
            this.balance = 0.0d;
            this.authorizeds = new ArrayList<Client>();
            this.lastLiquidation = new Date(System.currentTimeMillis());
            this.liquidationFrecuency = Account.DEFAULT_LIQUIDATION_FREQUENCY;
            this.directDebitHistory = new History<DirectDebitTransaction>();
            this.maxOverdraft = 0;
            this.directDebits = new AccountDirectDebits();
            this.liquidationFees = new ArrayList<AbstractLiquidationFee>();
            this.failedTransactionsHistory = new History<Transaction>();
            this.cards = new ArrayList<Card>();
        }
        Account.LOG.info("Create a new account with number " + accountnumber
                + " office " + office.getIdOffice().toString() + " bank "
                + bank.getID());
    }

    /**
     * Set the liquidation frecuency in months
     *
     * ( Default {
     *
     * @see DEFAULT_LIQUIDATION_FREQUENCY )
     *
     * @param liquidationFrecuency
     *            ( new liquidation frecuency )
     *
     * @return (true if success, false if the param is negative or zero)
     */
    public boolean setLiquidationFrecuency(int liquidationFrecuency) {
        if (liquidationFrecuency >= 1) {
            Account.LOG.info("Change liquidation frecuency to "
                    + liquidationFrecuency);
            this.liquidationFrecuency = liquidationFrecuency;
            return true;
        }
        return false;
    }

    /**
     * Get liquidation frecuency
     *
     * @return
     */
    public int getLiquidationFrecuency() {
        return this.liquidationFrecuency;
    }

    /**
     * Set the max account's overdraft
     *
     * @param maxOverdraft
     *            ( the account's overdraft ( in positive ))
     * @return ( true if succes, false otherwise)
     */
    public boolean setMaxOverdraft(double maxOverdraft) {
        if (maxOverdraft >= 0) {
            Account.LOG.info("Change max overdraft to " + maxOverdraft + "\n");
            this.maxOverdraft = maxOverdraft;
            return true;
        }
        return false;
    }

    /**
     *
     * Add a new titular. The client cannot be repeated, that is, two titulars
     * cannot have the same id, because its id is unique. If we try to add a
     * person that is already added the method return false.
     *
     * @param client
     *            ( client to add)
     *
     * @return ( true if success, else false )
     */
    public boolean addTitular(Client client) {
        boolean found = false;
        if ((client == null) || (client.getId() == null)) {
            found = true;
        } else {
            int i = 0;
            while ((i < this.titulars.size()) && !found) {
                if (this.titulars.get(i++).getId().compareTo(client.getId()) == 0) {
                    found = true;
                }
            }
            if (!found) {
                Account.LOG.info("Add new titular " + client.getId());
                this.titulars.add(client);
            } else {
                Account.LOG.error("Cannot add the titular "
                        + client.getId().toString()
                        + " , the titular already exists");
            }
        }
        return !found;
    }

    /**
     *
     * Delete a titular. If the titular hadn't been added, the method return
     * false.
     *
     * @see es.unileon.ulebank.handler.Handler .
     *
     * @param id
     *            ( The client id )
     *
     * @return ( true if success, false otherwise )
     */
    public boolean deleteTitular(Handler id) {
        boolean found = false;
        int i = 0;
        final StringBuilder err = new StringBuilder();
        if (this.titulars.size() <= 1) {
            err.append("Error, the account must have at least one titular\n");
        } else {
            while ((i < this.titulars.size()) && !found) {
                if (this.titulars.get(i).getId().compareTo(id) == 0) {
                    Account.LOG.info("Delete " + id.toString() + " titular");
                    this.titulars.remove(i);
                    found = true;
                }
                i++;
            }
            if (!found) {
                err.append("Cannot remove the titular ").append(id.toString())
                        .append(" because it doesn't exist");
            }
        }
        if (err.length() > 1) {
            found = false;
            Account.LOG.error(err);
        }
        return found;
    }

    /**
     *
     * Add a new authorized. The authorized cannot be repeated, that is, two
     * authorized cannot have the same id, because its id is unique.If we try to
     * add a person that is already added the method return false.
     *
     *
     * @param authorized
     *            ( authorized to add)
     *
     * @return ( true if success, else false )
     */
    public boolean addAuthorized(Client authorized) {
        boolean found = false;
        int i = 0;
        while ((i < this.authorizeds.size()) && !found) {
            if (this.authorizeds.get(i++).getId().compareTo(authorized.getId()) == 0) {
                found = true;
            }
        }
        if (!found) {
            Account.LOG.info("Add new authorized " + authorized.getId());
            this.authorizeds.add(authorized);
        } else {
            Account.LOG.error("Cannot add the authorized "
                    + authorized.getId().toString()
                    + " , the authorized already exists");
        }
        return !found;
    }

    /**
     *
     * Delete a authorized. If the authorized hadn't been added, the method
     * return false.
     *
     * @see es.unileon.ulebank.handler.Handler .
     *
     * @param id
     *            ( The authorized id )
     *
     * @return ( true if success, else false )
     */
    public boolean deleteAuthorized(Handler id) {
        boolean found = false;
        int i = 0;
        while ((i < this.authorizeds.size()) && !found) {
            if (this.authorizeds.get(i).getId().compareTo(id) == 0) {
                Account.LOG.info("Delete " + id.toString() + " authorized");
                this.authorizeds.remove(i);
                found = true;
            }
            i++;
        }
        if (!found) {
            Account.LOG.error("Cannot remove the authorized " + id.toString()
                    + " because it doesn't exist");
        }
        return found;
    }

    /**
     * Get the account titulars
     *
     * @return ( The titulars )
     */
    public List<Client> getTitulars() {
        return new ArrayList<Client>(this.titulars);
    }

    /**
     * Get the authorizeds
     *
     * @return ( the authorizeds )
     */
    public List<Client> getAuthorizeds() {
        return new ArrayList<Client>(this.authorizeds);
    }

    /**
     * Get the account's balance
     *
     * @return (the balance)
     *
     * @author runix
     */
    public final double getBalance() {
        return this.balance;
    }

    /**
     * Get the max account's overdraft
     *
     * @return (the account's overdraft )
     */
    public final double getMaxOverdraft() {
        return this.maxOverdraft;
    }

    /**
     * Do a direct debit. The Transaction is added in the direct transaction
     * history and in the common history.
     *
     * @see es.unileon.ulebank.account.Account.doTransaction
     *
     * @param transaction
     *            ( transaction to do )
     *
     * @throws TransactionException
     *             ( if the max overdraft is reached or sth like that ( see
     *             doTransaction exception )
     */
    public synchronized void doDirectDebit(DirectDebitTransaction transaction)
            throws TransactionException {
        this.doTransaction(transaction);
        this.directDebitHistory.add(transaction);
    }

    /**
     * Do a transaction. The transaction's amount and the account's amount is
     * added, so, if the transaction's amount is negative the balance is
     * decrease.
     *
     * @param transaction
     *            ( transaction to do )
     *
     * @throws TransactionException
     *             ( if the max overdraft is reached )
     */
    public synchronized void doTransaction(Transaction transaction)
            throws TransactionException {
        final StringBuilder err = new StringBuilder();

        if ((this.balance + transaction.getAmount()) < -this.maxOverdraft) {
            err.append(
                    "Cannot withdrawal money, max overdraft reached. Max overdraft = ")
                    .append(this.maxOverdraft).append("\n");
        }

        if (err.length() > 0) {
            throw new TransactionException(err.toString());
        }

        final boolean success = this.history.add(transaction);
        if (success) {
            this.balance += transaction.getAmount();
        } else {
            final String error = "Cannot store the transaction\n";
            Account.LOG.error(error);
            throw new TransactionException(error);
        }
    }

    /**
     * Perform account liquidation.
     *
     * @param office
     */
    public void doLiquidation(Office office) {
        for (final AbstractLiquidationFee fee : this.liquidationFees) {
            Transaction t = null;
            try {
                t = fee.calculateFee(this.lastLiquidation, new Date(Time
                        .getInstance().getTime()));
                final Transaction tNegate = new GenericTransaction(
                        -t.getAmount(), t.getDate(), t.getSubject()
                                + "Accoun : " + this.getID().toString()
                                + "id = " + t.getId());
                this.doTransaction(t);
                office.getOfficeAccount().doTransaction(tNegate);
                // TODO
                // Transaction forward transaction to office
            } catch (TransactionException e) {
                this.failedTransactionsHistory.add(t);
            }
        }
    }

    public boolean removeLiquidationFee(Handler id) {
        int i = -1;
        boolean removed = false;
        while ((++i < this.liquidationFees.size()) & !removed) {
            if (this.liquidationFees.get(i).getId().compareTo(id) == 0) {
                this.liquidationFees.remove(i);
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Get the account's transactions
     *
     * @return (The transactions)
     */
    public History<Transaction> getHistory() {
        return this.history;
    }

    /**
     * Get the direct debit transactions
     *
     * @return
     */
    public History<DirectDebitTransaction> getDirectDebitHistory() {
        return this.directDebitHistory;
    }

    /**
     * Get the account ID
     *
     * @return (the account id)
     * @author runix
     */
    public final Handler getID() {
        return this.id;
    }

    /**
     * Adds card into the card list
     * @param card
     */
    public boolean addCard(Card card) {
        if ((this.searchCard(card.getId()) == null) && (card != null)) {
            return  this.cards.add(card);
        }
       return false;
    }

    /**
     * Deletes card with received card ID
     * @param cardId
     * @return
     * @throws CardNotFoundException 
     */
    public boolean removeCard(Handler cardId) {
        if ((this.searchCard(cardId) != null) && (cardId != null)) {
            return  this.cards.remove(this.searchCard(cardId));
        }
        return false;
    }

    /**
     * Searches card with received card ID
     * @param cardId
     * @return
     * @throws CardNotFoundException 
     */
    public Card searchCard(Handler cardId) {
        final Iterator<Card> iterator = this.cards.iterator();
        Card card = null;

        while (iterator.hasNext()) {
            card = iterator.next();

            if (card.getId().compareTo(cardId) == 0) {
                break;
            }
        }
        return card;
    }

    /**
     * Return card list
     * @return
     */
    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Returns card list amount
     * @return
     */
    public int getCardAmount() {
        return this.cards.size();
    }
    
    /**
     * Changes balance with received balance
     * @param balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
