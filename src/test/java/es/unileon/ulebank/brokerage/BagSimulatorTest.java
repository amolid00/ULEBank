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
            bagSimulator = bagSimulator.getInstance();
    }
    
    @Test
    public void bagSimulatorNotNullTest(){
        assertNotNull(bagSimulator);
    }
    
//    @Test
//    public void 

//    @Test
//    public void getEnterpriseHandlerTest() throws MalformedHandlerException, InvalidBuyableException {
//        Enterprise enterprise = bagSimulator.getEnterpriseHandler(new EnterpriseHandler("GOOG"));
//        assertTrue(enterprise != null);
//        assertEquals("GOOG", enterprise.getId().toString());
//    }

}
