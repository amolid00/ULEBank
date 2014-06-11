/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

/**
 * Class that implements the data of an enterprise
 * 
 * @author Gonzalo Nicolás Barreales
 */
public class Enterprise extends Client {

    /**
     * 
     */
    private final List<Person> authorizedPersons;

    /**
     * 
     */
    private String enterpriseName;

    /**
     * 
     */
    private Address address;

    /**
     * 
     * @param cifLetter
     * @param cifNumber
     */
    public Enterprise(char cifLetter, int cifNumber, char cifControl)
            throws MalformedHandlerException {
        super(new EnterpriseHandler(cifLetter, cifNumber, cifControl));
        this.authorizedPersons = new ArrayList<Person>();
    }

    /**
     * 
     * @param cifLetter
     * @param cifNumber
     * @param enterpriseName
     * @param address
     */
    public Enterprise(char cifLetter, int cifNumber, char cifControl,
            String enterpriseName, Address address)
            throws MalformedHandlerException {
        super(new EnterpriseHandler(cifLetter, cifNumber, cifControl));
        this.authorizedPersons = new ArrayList<Person>();
        this.enterpriseName = enterpriseName;
        this.address = address;
    }

    /**
     * 
     * @param person
     */
    public void addAuthorizedPerson(Person person) {
        if (!this.existsAuthorizedPerson(person.getId())) {
            this.authorizedPersons.add(person);
        }
    }

    /**
     * 
     * @param personHandler
     * @return
     */
    public Person removeAuthorizedPerson(Handler personHandler) {
        Person removed = null;
        final Iterator<Person> iterator = this.authorizedPersons.iterator();
        while ((removed == null) && iterator.hasNext()) {
            final Person person = iterator.next();
            if (person.getId().compareTo(personHandler) == 0) {
                removed = person;
                iterator.remove();
            }
        }

        return removed;
    }

    /**
     * 
     * @param personHandler
     * @return
     */
    public boolean existsAuthorizedPerson(Handler personHandler) {
        boolean result = false;
        final Iterator<Person> iterator = this.authorizedPersons.iterator();
        while (iterator.hasNext()) {
            final Person person = iterator.next();
            if (person.getId().compareTo(personHandler) == 0) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 
     * @return
     */
    public String getEnterpriseName() {
        return this.enterpriseName;
    }

    /**
     * 
     * @param enterpriseName
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    /**
     * 
     * @return
     */
    public Address getAddress() {
        return this.address;
    }

    /**
     * 
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

}
