/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.office;

import java.util.ArrayList;
import java.util.List;

import es.unileon.ulebank.Employee;
import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.ClientNotFoundException;
import es.unileon.ulebank.client.Enterprise;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;

/**
 *
 * @author runix
 */
public class Office {

    /**
     * Max account number
     */
    public static final long MAX_ACCOUNT_NUMBER = 1000000000L - 1;

    /**
     * The list of accounts of this office
     */
    private final List<Account> accounts;
    /**
     * The list of clients of this office
     */
    private final List<Client> clients;
    /**
     * Office's id
     */
    private final Handler id;
    /**
     * Next account number
     */
    private long nextAccountNumber;
    /**
     * The costs of the local of the office
     */
    private int localCost;
    /**
     * The costs of the light, water and gas of the office
     */
    private int utilitiesCost;
    /**
     * The expenses in the salaries of the employees
     */
    private int employeeCost;
    /**
     * The total expenses or costs of the office
     */
    private int totalExpenses;
    /**
     * The total income of the office
     */
    private int totalIncome;
    /**
     * The total balance of the office
     */
    private int balance;
    /**
     * The list of employees of this office
     */
    private List<Employee> employeeList;
    /**
     * The account of the office
     */
    private Account account;
    /**
     * Office's account user
     */
    private Client officeClient;

    /**
     * Create a new office
     *
     * @param id
     *            ( the office's id )
     *
     * @param bank
     */
    public Office(Handler id, Bank bank) throws WrongArgsException {
        // WTF ???? IdOffice must be used instead of using the generic handler
        // TODO for integration
        this.id = id;
        this.accounts = new ArrayList<Account>();
        this.clients = new ArrayList<Client>();
        this.nextAccountNumber = 0;
        this.employeeList = new ArrayList<Employee>();
        // botched job, I know.. I am awaiting integration's changes
        // I don't change it because when it will be pushed into
        // integration all code and tests aren't going to compile
        try {
            this.officeClient = new Enterprise('A', 5881850, '1');
            this.account = new Account(this, bank, this.getNewAccountNumber(),
                    this.officeClient);
        } catch (MalformedHandlerException e) {
            throw new WrongArgsException(e.toString());
        } catch (WrongArgsException excep) {
            throw new WrongArgsException(excep.toString());
        }

    }

    /**
     * Get the account of the office
     * 
     * @return
     */
    public Account getOfficeAccount() {
        return this.account;
    }

    /**
     * Add a new account. The account cannot be repeated.
     *
     * @param account
     *            ( Account to add)
     * @return ( true if success, false otherwise )
     */
    public synchronized boolean addAccount(Account account) {
        if ((account != null) && (this.searchAccount(account.getID()) == null)) {
            return this.accounts.add(account);
        }
        return false;
    }

    /**
     * Add a new client.
     *
     * @param client
     *            ( client to add )
     * @return ( true if success, false otherwise )
     * @throws ClientNotFoundException 
     */
    public synchronized boolean addClient(Client client) throws ClientNotFoundException {
        if ((client != null) && (this.searchClient(client.getId()) == null)) {
            return this.clients.add(client);
        }
        return false;
    }

    /**
     * Delete a client.
     *
     * @param id
     *            ( client's id)
     * @return (true if success, false otherwise )
     * @throws ClientNotFoundException 
     */
    public synchronized boolean deleteClient(Handler id) throws ClientNotFoundException {
        if ((id != null) && (this.searchClient(id) != null)) {
            return this.clients.remove(this.searchClient(id));
        }
        return false;
    }

    /**
     * Delete a account.
     *
     * @param id
     *            ( account's id )
     * @return ( true if success, false otherwise )
     */
    public synchronized boolean deleteAccount(Handler id) {
        if ((id != null) && (this.searchAccount(id) != null)) {
            return this.accounts.remove(this.searchAccount(id));
        }
        return false;
    }

    /**
     * Get the list of clients
     *
     * @return
     */
    public List<Client> getClients() {
        return new ArrayList<Client>(this.clients);
    }

    /**
     * Get the office's id
     *
     * @return
     */
    public Handler getIdOffice() {
        return this.id;
    }

    /**
     * 
     * @param id
     * @return
     */
    public Account searchAccount(Handler id) {
        Account result = null;
        int i = -1;
        while ((++i < this.accounts.size()) && (result == null)) {
            if (this.accounts.get(i).getID().compareTo(id) == 0) {
                result = this.accounts.get(i);
            }
        }
        return result;
    }

    public Client searchClient(Handler id) throws ClientNotFoundException {
        Client result = null;
        int i = -1;
        while ((++i < this.clients.size()) && (result == null)) {
            if (this.clients.get(i).getId().compareTo(id) == 0) {
                result = this.clients.get(i);
            }
        }
        
        if (result == null) {
            throw new ClientNotFoundException("Client is not registered");
        }
        
        return result;
    }

    /**
     * Gets the next accountNumber. This number is incremental, from zero to
     * MAX_ACCOUNT_NUMBER.
     *
     * @return ( The number in String format )
     */
    public synchronized String getNewAccountNumber() {
        String accountNumber;
        if (this.nextAccountNumber == Office.MAX_ACCOUNT_NUMBER) {
            accountNumber = "";
        } else {
            accountNumber = String.format("%010d", this.nextAccountNumber++);
        }
        return accountNumber;
    }

    /**
     * Gets the list of accounts
     * 
     * @return
     */
    public List<Account> getAccounts() {
        return this.accounts;
    }

    /**
     * Returns the expenses of the office
     */
    public int getExpenses() {
        return this.totalExpenses;
    }

    /**
     * Sets the total expenses of the office
     */
    public void setExpenses(int localCost, int utilitiesCost, int employeeCost) {

        this.localCost = localCost;
        this.utilitiesCost = utilitiesCost;
        this.employeeCost = employeeCost;

        this.totalExpenses = this.localCost + this.utilitiesCost
                + this.employeeCost;
    }

    /**
     * Returns the income of the office
     */
    public int getTotalIncome() {
        return this.totalIncome;
    }

    /**
     * Sets the total income of the office
     */
    public void setTotalIncome(int totalIncome) {
        // Addition of the types of incomes.
        this.totalIncome = totalIncome;
    }

    /**
     * Returns the balance of the office
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * Sets the total balance of the office
     */
    public void setBalance() {
        this.balance = this.totalIncome - this.totalExpenses;
    }

    /**
     * Returns a copy of the list of employees of the office
     */
    public List<Employee> getEmployeeList() {
        return new ArrayList<Employee>(this.employeeList);
    }

    /**
     * Sets the list of employees of the office
     */
    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    /**
     * Adds an employee to the list of employees
     */
    public boolean addEmployee(Employee employee) {
        return this.employeeList.add(employee);
    }

    /**
     * Deletes an employee to the list of employees
     *
     * @param employee
     * @return
     */
    public boolean deleteEmployee(Employee employee) {
        return this.employeeList.remove(employee);
    }
}
