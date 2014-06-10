package es.unileon.ulebank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class EmployeeTest {

    private Employee employee;
    private Employee employeeWithoutOffice;
    private Office office;
    private Bank bank;

    @Before
    public void setUp() throws Exception {

        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.employee = new Employee("Name", "Surname", "Address", 2100f,
                this.office, new GenericHandler("1234"));
        this.employeeWithoutOffice = new Employee("Name", "Surname", "Address",
                2100f, new GenericHandler("1234"));
    }

    @Test
    public void testGetName() {

        final String employeeName = this.employee.getName();
        Assert.assertTrue(this.employee.getName().equals(employeeName));
        this.employee.setName("Name 2");
        Assert.assertTrue(this.employee.getName().equals("Name 2"));
    }

    @Test
    public void testSetNullName() {

        this.employee.setName(null);
        Assert.assertTrue(this.employee.getName().equals("Name"));
    }

    @Test
    public void testSetEmptyName() {

        this.employee.setName("");
        Assert.assertTrue(this.employee.getName().equals("Name"));
    }

    @Test
    public void testGetSurname() {

        final String employeeSurname = this.employee.getSurname();
        Assert.assertTrue(this.employee.getSurname().equals(employeeSurname));
        this.employee.setSurname("Surname 2");
        Assert.assertTrue(this.employee.getSurname().equals("Surname 2"));
    }

    @Test
    public void testSetNullSurname() {

        this.employee.setSurname(null);
        Assert.assertTrue(this.employee.getSurname().equals("Surname"));
    }

    @Test
    public void testSetEmptySurname() {

        this.employee.setSurname("");
        Assert.assertTrue(this.employee.getSurname().equals("Surname"));
    }

    @Test
    public void testGetAddress() {

        final String employeeAddress = this.employee.getAddress();
        Assert.assertTrue(this.employee.getAddress().equals(employeeAddress));
        this.employee.setAddress("Address 2");
        Assert.assertTrue(this.employee.getAddress().equals("Address 2"));
    }

    @Test
    public void testGetSalary() {

        final float employeeSalary = this.employee.getSalary();
        Assert.assertEquals(employeeSalary, this.employee.getSalary(), 0);
        this.employee.setSalary(1800f);
        Assert.assertEquals(1800f, this.employee.getSalary(), 0);
    }

    @Test
    public void testSetNegativeSalary() {

        this.employee.setSalary(-1800f);
        Assert.assertEquals(2100f, this.employee.getSalary(), 0);
    }

    @Test
    public void testGetOffice() throws WrongArgsException {

        final Office office2 = new Office(new GenericHandler("2345"), this.bank);
        Assert.assertEquals(0, this.employee.getOffice().getIdOffice()
                .compareTo(this.office.getIdOffice()), 0);
        this.employee.setOffice(office2);
        Assert.assertEquals(0, this.employee.getOffice().getIdOffice()
                .compareTo(office2.getIdOffice()), 0);
    }

    @Test
    public void testGetIdEmployee() {

        final Handler idEmployee = this.employee.getIdEmployee();
        final GenericHandler idEmployee2 = new GenericHandler("2345");
        Assert.assertEquals(0,
                this.employee.getIdEmployee().compareTo(idEmployee), 0);
        this.employee.setIdEmployee(idEmployee2);
        Assert.assertEquals(0,
                this.employee.getIdEmployee().compareTo(idEmployee2), 0);
    }

    @Test
    public void testSetNullIdEmployee() {

        final Handler idEmployee = this.employee.getIdEmployee();
        this.employee.setIdEmployee(null);
        Assert.assertEquals(0,
                this.employee.getIdEmployee().compareTo(idEmployee), 0);
    }

    @Test
    public void testIsAdmin() {

        Assert.assertFalse(this.employee.isAdmin());
    }

    @Test
    public void testGetOfficeNullOffice() {

        Assert.assertNull(this.employeeWithoutOffice.getOffice());
    }
}
