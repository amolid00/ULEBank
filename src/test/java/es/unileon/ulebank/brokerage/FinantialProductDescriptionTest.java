package es.unileon.ulebank.brokerage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class FinantialProductDescriptionTest {
    
    private TypeFinancialProducts typeFinantialProducts;
    private FinantialProductDescription finantialProductDescription;
    private ArrayList<Integer> requirements = new ArrayList<Integer>();
    
    

    @Test
    public void productDescriptionFundstNotNullTest() {
        finantialProductDescription = new FinantialProductDescription(TypeFinancialProducts.Funds);
        assertNotNull(finantialProductDescription);
    }
    
    @Test
    public void productDescriptionFundsTest(){
        finantialProductDescription = new FinantialProductDescription(TypeFinancialProducts.Funds);
        requirements.add(3);
        requirements.add(4);
        assertTrue(finantialProductDescription.canOffer(requirements));
    }
    
    @Test
    public void productDescriptionInsurancesNotNullTest() {
        finantialProductDescription = new FinantialProductDescription(TypeFinancialProducts.Insurances);
        assertNotNull(finantialProductDescription);
    }
    
    @Test
    public void productDescriptionInsurancesTest(){
        finantialProductDescription = new FinantialProductDescription(TypeFinancialProducts.Insurances);
        assertTrue(finantialProductDescription.canOffer(requirements));
    }
    
    @Test
    public void productDescriptionShareNotNullTest() {
        finantialProductDescription = new FinantialProductDescription(TypeFinancialProducts.Share);
        assertNotNull(finantialProductDescription);
    }
    
    @Test
    public void productDescriptionShareTest(){
        finantialProductDescription = new FinantialProductDescription(TypeFinancialProducts.Share);
        requirements.add(1);
        requirements.add(2);
        requirements.add(4);
        assertTrue(finantialProductDescription.canOffer(requirements));
    }
    
    @Test 
    public void productDescriptionToStringTest(){
        finantialProductDescription = new FinantialProductDescription(TypeFinancialProducts.Insurances);
        assertEquals(TypeFinancialProducts.Insurances.toString(), finantialProductDescription.toString());
    }

}
