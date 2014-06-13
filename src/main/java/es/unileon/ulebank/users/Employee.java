package es.unileon.ulebank.users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Patricia
 *
 */
@Entity
@Table(name = "Employees")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Id of the employee
	 */
	@Id
	@Column(name = "employeeID")
	private String employeeID;
	/**
	 * Name of the employee
	 */
	private String name;
	/**
	 * Surname of the employee
	 */
	private String surname;
	/**
	 * Surname of the employee
	 */
	private String address;
	/**
	 * Salary of the employee
	 */
	private float salary;
	/**
	 * Office where the employee works
	 */
	private String idOffice;

	/**
	 * Creates a new employee
	 *
	 * @param name
	 * 
	 * @param surname
	 * 
	 * @param idOffice
	 * 
	 * @param idEmployee
	 * 
	 */
	public Employee(String name, String surname, String address, float salary,
			String idOffice, String idEmployee) {

		if (name != null && name.length() > 0 && surname != null
				&& surname.length() > 0 && address != null && salary > 0
				&& idEmployee != null) {
			this.name = name;
			this.surname = surname;
			this.address = address;
			this.salary = salary;
			this.idOffice = idOffice;
			this.employeeID = idEmployee;
		}
	}

	public Employee() {
	}

	/**
	 * Returns the name of the employee
	 *
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the employee's name
	 *
	 * @param name
	 * 
	 */
	public void setName(String name) {
		if ((name != null) && (name.length() > 0)) {
			this.name = name;
		}
	}

	/**
	 * Returns the surname of the employee
	 *
	 * @return surname
	 */
	public String getSurname() {
		return this.surname;
	}

	/**
	 * Sets the employee's surname
	 *
	 * @param surname
	 * 
	 */
	public void setSurname(String surname) {
		if ((surname != null) && (surname.length() > 0)) {
			this.surname = surname;
		}
	}

	/**
	 * Returns the address of the employee
	 *
	 * @return address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Sets the address of the employee
	 *
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Returns the salary of the employee
	 *
	 * @return salary
	 */
	public float getSalary() {
		return this.salary;
	}

	/**
	 * Sets the salary of the employee
	 *
	 * @param salary
	 * 
	 */
	public void setSalary(float salary) {
		if (salary > 0) {
			this.salary = salary;
		}
	}

	/**
	 * Returns the id of the office where the employee works
	 *
	 * @return office
	 */
	public String getIDOffice() {
		return this.idOffice;
	}

	/**
	 * Sets the id of the office
	 *
	 * @param idOffice
	 */
	public void setIDOffice(String idOffice) {
		this.idOffice = idOffice;

	}

	/**
	 * Returns the id of the employee
	 *
	 * @return idEmployee
	 */
	public String getIdEmployee() {
		return this.employeeID;
	}

	/**
	 * Sets the id of the employee
	 *
	 * @param idEmployee
	 * 
	 */
	public void setIdEmployee(String idEmployee) {
		if (idEmployee != null) {
			this.employeeID = idEmployee;
		}
	}

	/**
	 * Checks if the employee is an admin
	 *
	 * @return true if is an admin
	 */
	public boolean isAdmin() {
		return false;
	}

	/**
	 * toString method
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID: " + this.getIdEmployee());
		buffer.append("Name: " + this.getName());
		buffer.append("Surname: " + this.getSurname());
		buffer.append("Address: " + address + ";");
		buffer.append("Office: " + this.getIDOffice());
		buffer.append("Salary: " + this.getSalary());
		return buffer.toString();
	}
}