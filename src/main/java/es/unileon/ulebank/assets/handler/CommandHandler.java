package es.unileon.ulebank.assets.handler;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.time.Time;

public class CommandHandler implements Handler, Serializable {
	
	private String id;
	private String uuid;
	private long time;
	
	public CommandHandler() {
		this.uuid = UUID.randomUUID().toString();
		this.time = Time.getInstance().getTime();
		this.id = this.uuid + "-" + this.time;
	}
	
	
	
	public int compareTo(Handler another) {
		return this.id.toString().compareTo(another.toString());
	}
	
	@Override
	public String toString() {
		return this.id.toString();
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
