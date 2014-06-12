package es.unileon.ulebank.assets.handler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.handler.LoanIdentificationNumberCode;
import es.unileon.ulebank.assets.handler.exceptions.LINCMalformedException;

public class FinancialProductHandlerTest {
	
	private LoanIdentificationNumberCode linc1, linc2;
	private FinancialProductHandler financialProduct1, financialProduct2;
	
	@Before
	public void setUp() throws LINCMalformedException {
		this.linc1 = new LoanIdentificationNumberCode("LN", "ES");
		this.linc2 = new LoanIdentificationNumberCode("LN", "NZ");
		
		this.financialProduct1 = new FinancialProductHandler(this.linc1);
		this.financialProduct2 = new FinancialProductHandler(this.linc2);
	}
	
	@Test
	public void testEqual() {
		assertTrue(this.financialProduct1.compareTo(this.financialProduct2) < 0);
		
		String expected = this.linc1.getType() + "-" + this.linc1.getDate() + "-" + this.linc1.getCountryCode()
				+ "-" + this.linc1.getRandomCharacters() + "-" +  this.linc1.getCheckDigit();
		assertTrue(expected.compareTo(this.financialProduct1.toString()) == 0);
		
		
	}
	
	@Test
	public void testNotEqual() {
		assertTrue(this.financialProduct1.compareTo(this.financialProduct2) < 0);
		
		String expected = this.linc1.getDate() + "-" + this.linc1.getCountryCode() + "-" + 
				this.linc1.getRandomCharacters() + "-" +  this.linc1.getCheckDigit() + "-" + this.linc1.getType();
		assertTrue(expected.compareTo(this.financialProduct1.toString()) < 0);
		
		
	}
	
	@Test(expected = LINCMalformedException.class)
	public void testLINCMalformed() throws LINCMalformedException {
		new FinancialProductHandler(new LoanIdentificationNumberCode("NOT", "ES"));
	}

}
