package es.unileon.ulebank.brokerage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.brokerage.buyable.Enterprise;
import es.unileon.ulebank.brokerage.buyable.EnterpriseHandler;
import es.unileon.ulebank.brokerage.buyable.InvalidBuyableException;
import es.unileon.ulebank.handler.MalformedHandlerException;

public class BagSimulatorTest {
    
    private BagSimulator bagSimulator;
    
    @Before
    public void setUp() throws InvalidBuyableException{
            bagSimulator = BagSimulator.getInstance();
    }
    
    @Test
    public void bagSimulatorNotNullTest(){
        assertNotNull(bagSimulator);
    }
    
    @Test
    public void getEnterpriseHandlerTest() throws InvalidBuyableException, MalformedHandlerException{
        Enterprise enterprise = bagSimulator.getEnterpriseHandler(new EnterpriseHandler("GOOG"));
        Enterprise enterprise2 =  new Enterprise(new EnterpriseHandler("GOOG"), 300, 10000);
        assertEquals(enterprise2.getBuyableParticipations(), enterprise.getBuyableParticipations());
        assertEquals(enterprise2.getParticipations(), enterprise.getParticipations());
        assertTrue(enterprise2.getPPP() == enterprise.getPPP());
        assertTrue(enterprise2.getPrice() == enterprise.getPrice());
    }
    

}
