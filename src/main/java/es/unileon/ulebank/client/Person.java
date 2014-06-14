/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.client;

import java.util.Date;

import es.unileon.ulebank.handler.MalformedHandlerException;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Gonzalo
 */
@Entity
@Table(name = "CLIENTS", catalog = "ULEBANK_FINAL")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "Person")
public class Person extends Client {

    /**
     * Name of the person
     */
    private String name;

    /**
     * surnames of the person
     */
    private String surnames;

    /**
     * address of the person
     */
    private String address;

    /**
     * marritage state of the person
     */
    private String civilState;

    /**
     * phone numbers 1
     */
    private int phoneNumber1;

    /**
     * phone number 2
     */
    private int phoneNumber2;
    /**
     * proffesion of the person
     */
    private String profession;

    /**
     * birth date of the person
     */
    private Date birthDate;
    /**
     * Person age
     */
    private int age;

    /**
     * creates a new Person instance with only the dni
     * 
     * @param dniNumber
     * @param dniLetter
     * @throws MalformedHandlerException
     */
    public Person(int dniNumber, char dniLetter)
            throws MalformedHandlerException {
        super(new PersonHandler(dniNumber, dniLetter));
    }

    /**
     *
     * @param foreingLetter
     * @param dniNumber
     * @param dniLetter
     * @throws MalformedHandlerException
     */
    public Person(char foreingLetter, int dniNumber, char dniLetter)
            throws MalformedHandlerException {
        super(new PersonHandler(foreingLetter, dniNumber, dniLetter));
    }

    public Person() {

    }

    /**
     *
     * @return the name of the person
     */
    @Column(name = "name", length = 64)
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the person
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return the surnames of the person
     */
    @Column(name = "surnames", length = 64)
    public String getSurnames() {
        return this.surnames;
    }

    /**
     * Sets the surnames of the person
     * 
     * @param surnames
     */
    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    /**
     * 
     * @return the address of the person
     */
    @Column(name = "address", nullable = false, length = 256)
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets the address of the person
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return the marital status of the person
     */
    @Column(name = "civil_state", length = 2)
    public String getCivilState() {
        return this.civilState;
    }

    /**
     * Sets the marital status of the person
     * 
     * @param civilState
     */
    public void setCivilState(String civilState) {
        this.civilState = civilState;
    }

    /**
     * 
     * @param pos
     * @return the phone number of the person in pos
     */
    public int getPhoneNumber(int pos) {
        if (pos == 1) {
            return this.phoneNumber1;
        } else if (pos == 2) {
            return this.phoneNumber2;
        }
        return 0;
    }

    @Column(name = "phone_number1")
    public int getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(int phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    @Column(name = "phone_number2")
    public int getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(int phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    /**
     * Sets a phone number of the person in pos
     * 
     * @param pos
     * @param phoneNumbers
     */
    public void replacePhoneNumber(int pos, int phoneNumbers) {
        if (pos == 1) {
            this.phoneNumber1 = phoneNumbers;
        } else if (pos == 2) {
            this.phoneNumber2 = phoneNumbers;
        }
    }

    /**
     * Remove a phone number in pos
     * 
     * @param pos
     */
    public void removePhoneNumber(int pos) {
        // TODO
    }

    /**
     * 
     * @return the profession of the person
     */
    @Column(name = "profession", length = 64)
    public String getProfession() {
        return this.profession;
    }

    /**
     * Sets the profession of the person
     * 
     * @param profession
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * 
     * @return the birth date of the person
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birth_date", length = 19)
    public Date getBirthDate() {
        return this.birthDate;
    }

    /**
     * Sets the birth date of the person
     * 
     * @param birthDate
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Returns person age
     * 
     * @return
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Changes person age with received age
     * 
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }
}
