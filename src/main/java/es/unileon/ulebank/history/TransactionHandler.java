package es.unileon.ulebank.history;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import es.unileon.ulebank.handler.Handler;

/**
 *
 * @author roobre
 */
public class TransactionHandler implements Handler, Serializable {

    private String id;
    
    private TransactionHandler(){
        
    }

    /**
     *
     * @param id
     * @param timestamp
     */
    public TransactionHandler(long id, String timestamp) {
        this.id = timestamp + "." + Long.toString(id);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.id;
    }

    @Override
    public int compareTo(Handler another) {
        return this.toString().compareTo(another.toString());
    }

    @Override
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 64)
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
        
    }

}