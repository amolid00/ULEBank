package es.unileon.ulebank.brokerage;

import static org.junit.Assert.*;

import org.junit.Test;

public class TypeFinancialProductsTest {

    @Test
    public void shareNotNullTest() {
        assertNotNull(TypeFinancialProducts.Share);
        assertNotNull(TypeFinancialProducts.valueOf("Share"));
    }
    
    @Test
    public void shareTest(){
        assertEquals("Share", TypeFinancialProducts.Share.toString());
    }
    
    @Test
    public void fundsNotNullTest() {
        assertNotNull(TypeFinancialProducts.Funds);
        assertNotNull(TypeFinancialProducts.valueOf("Funds"));
    }
    
    @Test
    public void fundsTest(){
        assertEquals("Funds", TypeFinancialProducts.Funds.toString());
    }
    
    @Test
    public void insurancesNotNullTest() {
        assertNotNull(TypeFinancialProducts.Insurances);
        assertNotNull(TypeFinancialProducts.valueOf("Insurances"));
    }
    
    @Test
    public void insurancesTest(){
        assertEquals("Insurances", TypeFinancialProducts.Insurances.toString());
    }

}
