package es.unileon.ulebank.assets.handler;

import java.util.Date;
import java.util.UUID;

import es.unileon.ulebank.handler.Handler;

public class CommandHandler implements Handler {
	
	private String id;
	private UUID uuid;
	private long time;
	private Date date;
	
	public CommandHandler() {
		this.uuid = UUID.randomUUID();
		this.date = new Date();
		this.time = this.date.getTime();
		this.id = this.uuid.toString() + "-" + this.time;
	}
	
	
	
	public int compareTo(Handler another) {
		return this.id.toString().compareTo(another.toString());
	}
	
	@Override
	public String toString() {
		return this.id.toString();
	}

}
