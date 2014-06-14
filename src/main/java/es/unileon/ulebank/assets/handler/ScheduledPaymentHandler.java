package es.unileon.ulebank.assets.handler;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;





import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.handler.*;

public class ScheduledPaymentHandler implements Handler, Serializable {
	
	private String id ="";
	
	/**
	 * Empty constructor to persist
	 */
    public ScheduledPaymentHandler(){
        
    }
	/**
	 * List of clients of the account, the date of the payment and
	 * the loan ID
	 * 
	 * loandID-clientID-clientID-year-month-day
	 * @param loanID
	 * @param clients
	 * @param datePayment
	 */
	
	public ScheduledPaymentHandler(Handler loanId, List<Client> clients, Date datePayment) {
		this.id += loanId.toString() + "-"; 
		
		
		for(Client client : clients){
			this.id+= client.toString() + "-";
		    
		}
		
		long time =  datePayment.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		
		this.id += cal.get(Calendar.YEAR) + "-";
		this.id += cal.get(Calendar.MONTH) + "-";
		this.id += cal.get(Calendar.DATE);
	}
	
	@Override
	public int compareTo(Handler another) {
		return this.id.toString().compareTo(another.toString());
	}
	
	@Override
	public String toString() {
		return this.id.toString();
	}

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
        
    }
	
}
