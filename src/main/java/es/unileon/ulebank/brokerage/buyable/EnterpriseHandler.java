package es.unileon.ulebank.brokerage.buyable;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

public class EnterpriseHandler implements Handler{

    private String ticker;
    
    public EnterpriseHandler(String ticker) throws MalformedHandlerException {
        if(ticker.length() < 6){
            this.ticker = ticker;
        }else{
            throw new MalformedHandlerException("Invalid ticker");
        }
    }
    
    @Override
    public int compareTo(Handler another) {
        return toString().compareTo(another.toString());
    }    
}
