/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.office;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.Employee;
import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.client.PersonHandler;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;

/**
 *
 * @author Revellado
 */
public class OfficeTest {

    private Office office;
    private Bank bank;
    private Account account;
    private Client titular;
    private int testExpenses;
    private int totalExpenses;
    private int totalIncome;
    private List<Employee> employeeTestList;
    private Employee oneEmployee;
    private PersonHandler dni;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.titular = new Person(71525252, 'J');
        this.account = new Account(this.office, this.bank, "1234567890",
                this.titular);
        this.employeeTestList = new ArrayList<Employee>();
        this.testExpenses = 1000;
        this.totalExpenses = 3000;
        this.totalIncome = 1000;
        this.dni = new PersonHandler(36167364, 'W');
        this.oneEmployee = new Employee("name", "surname", "address", 0,
                this.dni);

        this.office.setExpenses(this.testExpenses, this.testExpenses,
                this.testExpenses);
    }

    @Test
    public void testGetExpenses() {
        Assert.assertTrue(this.office.getExpenses() == this.totalExpenses);
    }

    @Test
    public void testSetExpenses() {
        final int newTestExpenses = 3000;
        final int newTotalExpenses = 9000;
        this.office.setExpenses(newTestExpenses, newTestExpenses,
                newTestExpenses);

        Assert.assertTrue(newTotalExpenses == this.office.getExpenses());
    }

    @Test
    public void testGetTotalIncome() {
        this.office.setTotalIncome(this.totalIncome);
        final int result = this.office.getTotalIncome();
        final int expected = this.totalIncome;

        Assert.assertEquals(result, expected);
    }

    @Test
    public void testSetTotalIncome() {
        final int testIncome = 3000;
        this.office.setTotalIncome(this.totalIncome);
        int result = this.office.getTotalIncome();
        final int expected = this.totalIncome;

        Assert.assertEquals(result, expected);

        this.office.setTotalIncome(testIncome);
        result = this.office.getTotalIncome();

        Assert.assertTrue(result != expected);

    }

    @Test
    public void testSetAndGetBalance() {
        this.office.setBalance();
        Assert.assertEquals(this.office.getBalance(), -3000);
    }

    @Test
    public void testGetEmployeeList() {
        this.office.setEmployeeList(this.employeeTestList);
        final List<Employee> result = this.office.getEmployeeList();
        final List<Employee> expected = this.employeeTestList;

        Assert.assertEquals(result, expected);
    }

    @Test
    public void testSetEmployeeList() {
        this.office.setEmployeeList(this.employeeTestList);
        final List<Employee> result = this.office.getEmployeeList();
        final List<Employee> expected = this.employeeTestList;

        Assert.assertEquals(result, expected);

    }

    @Test
    public void testAddEmployee() {
        this.office.setEmployeeList(this.employeeTestList);
        this.office.addEmployee(this.oneEmployee);

        final Employee result = this.employeeTestList.get(0);
        final Employee expected = this.oneEmployee;

        Assert.assertEquals(result, expected);

    }

    @Test
    public void testDeleteEmployee() {
        this.office.setEmployeeList(this.employeeTestList);
        this.office.addEmployee(this.oneEmployee);

        final Employee result = this.employeeTestList.get(0);
        final Employee expected = this.oneEmployee;

        Assert.assertEquals(result, expected);

        this.office.deleteEmployee(this.oneEmployee);
        Assert.assertTrue(this.employeeTestList.isEmpty());
    }

    /**
     * Test of addAccount method, of class Office.
     */
    @Test
    public void testAddAccount() {
        final int numberOfAccounts = this.office.getAccounts().size();
        Assert.assertTrue(this.office.addAccount(this.account));
        Assert.assertEquals(this.office.getAccounts().size(),
                numberOfAccounts + 1);
        Assert.assertFalse(this.office.addAccount(this.account));
        Assert.assertEquals(this.office.getAccounts().size(),
                numberOfAccounts + 1);
        Assert.assertFalse(this.office.addAccount(null));
    }

    @Test
    public void testAddClient() {
        final int numberOfAccounts = this.office.getAccounts().size();
        Assert.assertTrue(this.office.addClient(this.titular));
        Assert.assertFalse(this.office.addClient(this.titular));
        Assert.assertFalse(this.office.addClient(null));
        Assert.assertEquals(this.office.getClients().size(),
                numberOfAccounts + 1);
    }

    @Test
    public void testDeleteClient() throws MalformedHandlerException {
        final int numberOfAccounts = this.office.getAccounts().size();
        final Client c = new Person(89051755, 'X');
        Assert.assertFalse(this.office.deleteClient(new GenericHandler("..")));
        Assert.assertTrue(this.office.addClient(this.titular));
        Assert.assertTrue(this.office.addClient(c));
        Assert.assertEquals(this.office.getClients().size(),
                numberOfAccounts + 2);
        Assert.assertTrue(this.office.deleteClient(c.getId()));
        Assert.assertFalse(this.office.deleteClient(null));
        Assert.assertFalse(this.office.deleteClient(new GenericHandler("..")));
    }

    /**
     * Test of addAccount method, of class Office.
     */
    @Test
    public void testAddAccountNullAccount() {
        Assert.assertFalse(this.office.addAccount(null));
    }

    /**
     * Test of getID method, of class Office.
     */
    @Test
    public void testGetID() {
        final Handler expResult = new GenericHandler("1234");
        Assert.assertEquals(this.office.getIdOffice().compareTo(expResult), 0);
    }

    @Test
    public void testDeleteAccount() throws MalformedHandlerException,
            WrongArgsException {
        final Account newAccount = new Account(this.office, this.bank,
                this.office.getNewAccountNumber(), this.titular);
        Assert.assertFalse(this.office.deleteAccount(new GenericHandler(
                "!123123123")));
        Assert.assertTrue(this.office.addAccount(newAccount));
        Assert.assertTrue(this.office.deleteAccount(newAccount.getID()));
        Assert.assertTrue(this.office.searchAccount(newAccount.getID()) == null);
        Assert.assertFalse(this.office.deleteAccount(new GenericHandler(
                "!123123123")));
        Assert.assertFalse(this.office.deleteAccount(null));
    }

    @Test
    public void testSearchAccount() throws MalformedHandlerException,
            WrongArgsException {
        this.office.addAccount(this.account);
        final Account newAccount = new Account(this.office, this.bank,
                this.office.getNewAccountNumber(), this.titular);
        this.office.addAccount(newAccount);
        Assert.assertEquals(newAccount,
                this.office.searchAccount(newAccount.getID()));
        Assert.assertEquals(null,
                this.office.searchAccount(new GenericHandler("123123123")));
    }

    @Test
    public void testSearchClinet() throws MalformedHandlerException,
            WrongArgsException {
        this.office.addClient(this.titular);
        final Client c = new Person(89051755, 'X');
        this.office.addClient(c);
        Assert.assertEquals(c, this.office.searchClient(c.getId()));
        Assert.assertEquals(null,
                this.office.searchClient(new GenericHandler("123123123")));
    }

    @Test
    public void testGetNextAccountNumber() {
        // for (long i = 0; i < 1000000000L - 1; i++) {
        // assertEquals(String.format("%010d", i),
        // this.office.getNewAccountNumber());
        // }
        // assertEquals(this.office.getNewAccountNumber(), "");
    }
}
