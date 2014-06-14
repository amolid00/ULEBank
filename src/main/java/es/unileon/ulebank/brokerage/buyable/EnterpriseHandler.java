package es.unileon.ulebank.brokerage.buyable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

public class EnterpriseHandler implements Handler, Serializable{

    private String id;
    
    private EnterpriseHandler(){
        
    }
    
    public EnterpriseHandler(String ticker) throws MalformedHandlerException {
        if(ticker.length() < 6){
            this.id = ticker;
        }else{
            throw new MalformedHandlerException("Invalid ticker");
        }
    }
    
    @Override
    public int compareTo(Handler another) {
        return toString().compareTo(another.toString());
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
    
    public String toString(){
        return this.id;
    }
}
