/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.handler;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.DiscriminatorType;

import es.unileon.ulebank.handler.Handler;

/**
 *
 * @author runix
 *
 */
@Entity
@Table(name = "GENERIC_HANDLER", catalog = "ULEBANK_FINAL")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "GenericHandler")
public class GenericHandler implements Handler {

    /**
     * Generic id
     */
    private String id;

    /**
     * Create a new Generic Handler
     *
     * @param id
     *            (The id)
     * @author runix
     */
    public GenericHandler() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public GenericHandler(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(Handler another) {
        return this.id.compareTo(another.toString());
    }

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 64)
    public String getId() {
        return this.id;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.id;
    }

}