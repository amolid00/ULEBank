/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.client;

import java.util.Date;

import es.unileon.ulebank.handler.MalformedHandlerException;

/**
 *
 * @author Gonzalo
 */
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
    private Address address;

    /**
     * marritage state of the person
     */
    private String civilState;

    /**
     * phone numbers of the person
     */
    private final int[] phoneNumbers;

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
        this.phoneNumbers = new int[2];
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
        this.phoneNumbers = new int[2];
    }

    /**
     *
     * @return the name of the person
     */
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
    public Address getAddress() {
        return this.address;
    }

    /**
     * Sets the address of the person
     * 
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * 
     * @return the marital status of the person
     */
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
        if ((pos <= 1) && (pos >= 0)) {
            return this.phoneNumbers[pos];
        }
        return 0;
    }

    /**
     * Sets a phone number of the person in pos
     * 
     * @param pos
     * @param phoneNumbers
     */
    public void replacePhoneNumber(int pos, int phoneNumbers) {
        if ((pos <= 1) && (pos >= 0)) {
            this.phoneNumbers[pos] = phoneNumbers;
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
