/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.handler;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Handler pattern.
 * 
 * @author runix
 */
@Entity
@Table(name = "GENERIC_HANDLER", catalog = "ULEBANK_FINAL")
public interface Handler extends Comparable<Handler>, Serializable {

    /**
     * Compare the actual handler with another
     *
     * @param another
     *            ( Handler to compare )
     * @return (0 if are equals, != 0 otherwise )
     */
    @Override
    public int compareTo(Handler another);

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 64)
    public String getId();
    
    
    public void setId(String id);
    /**
     *
     * @return
     */
    @Override
    public String toString();
}