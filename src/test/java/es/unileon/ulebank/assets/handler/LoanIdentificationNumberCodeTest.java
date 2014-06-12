package es.unileon.ulebank.assets.handler;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.assets.handler.LoanIdentificationNumberCode;
import es.unileon.ulebank.assets.handler.exceptions.LINCMalformedException;

public class LoanIdentificationNumberCodeTest {
	
	private LoanIdentificationNumberCode lincSpain, lincNewZealand, lincItaly, lincCanada;
	
	@Before
	public void setUp() throws LINCMalformedException {
		//Loan for Spain
		this.lincSpain = new LoanIdentificationNumberCode("LN", "ES");
		//Loan for New Zealand
		this.lincNewZealand = new LoanIdentificationNumberCode("LN", "NZ");
		//Loan for Italy
		this.lincItaly = new LoanIdentificationNumberCode("LN", "IT");
		//Mortgage for Canada
		this.lincCanada = new LoanIdentificationNumberCode("MG", "CA");
	}
	
	@Test
	public void testRandomCharactersAreDifferent() {
		assertTrue(this.lincSpain.getRandomCharacters() != this.lincItaly.getRandomCharacters());
		assertTrue(this.lincSpain.getRandomCharacters() != this.lincNewZealand.getRandomCharacters());
		assertTrue(this.lincSpain.getRandomCharacters() != this.lincCanada.getRandomCharacters());
		assertTrue(this.lincItaly.getRandomCharacters() != this.lincNewZealand.getRandomCharacters());
		assertTrue(this.lincItaly.getRandomCharacters() != this.lincCanada.getRandomCharacters());
		assertTrue(this.lincCanada.getRandomCharacters() != this.lincNewZealand.getRandomCharacters());
	}
	
	@Test
	public void testCountryCode(){
		assertTrue(this.lincSpain.getCountryCode() == "ES");
		assertTrue(this.lincNewZealand.getCountryCode() == "NZ");
		assertTrue(this.lincItaly.getCountryCode() == "IT");
		assertTrue(this.lincCanada.getCountryCode() == "CA");
		assertTrue(this.lincSpain.getCountryCode() != this.lincItaly.getCountryCode());
		assertTrue(this.lincSpain.getCountryCode() != this.lincNewZealand.getCountryCode());
		assertTrue(this.lincSpain.getCountryCode() != this.lincCanada.getCountryCode());
		assertTrue(this.lincItaly.getCountryCode() != this.lincNewZealand.getCountryCode());
		assertTrue(this.lincItaly.getCountryCode() != this.lincCanada.getCountryCode());
		assertTrue(this.lincCanada.getCountryCode() != this.lincNewZealand.getCountryCode());
	}
	
	@Test(expected = LINCMalformedException.class)
	public void testCountryCodeException() throws LINCMalformedException{
		
		String expectedMessageException = "Country code not exist.\n";
		
		try{
			new LoanIdentificationNumberCode("LN", "COUNTRY IS NOT VALID");
		}catch(Exception ex) {
			assertEquals(expectedMessageException.compareTo(ex.getMessage()), 0);
		}
		
		new LoanIdentificationNumberCode("LN", "COUNTRY IS NOT VALID");
	}
	
	@Test(expected = LINCMalformedException.class)
	public void testTypeException() throws LINCMalformedException{
		String expectedMessageException = "Type not exist.\n";
		
		try{
			new LoanIdentificationNumberCode("TYPE IS NOT VALID", "ES");
		}catch(Exception ex) {
			assertEquals(expectedMessageException.compareTo(ex.getMessage()), 0);
		}
		
		new LoanIdentificationNumberCode("TYPE IS NOT VALID", "ES");
	}
	
	@Test(expected = LINCMalformedException.class)
	public void testTypeAndCountryCodeException() throws LINCMalformedException {
		
		String expectedMessageException = "Country code not exist.\nType not exist.\n";
		
		try{
			new LoanIdentificationNumberCode("TYPE IS NOT VALID", "COUNTRY IS NOT VALID");
		}catch(Exception ex) {
			assertEquals(expectedMessageException.compareTo(ex.getMessage()), 0);
		}
		
		new LoanIdentificationNumberCode("TYPE IS NOT VALID", "COUNTRY IS NOT VALID");
	}
	
	@Test
	public void testDate() {
		String expectedDate = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + "-" +
				String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
			
		assertEquals(expectedDate.compareTo(this.lincSpain.getDate()), 0);
		assertEquals(expectedDate.compareTo(this.lincNewZealand.getDate()), 0);
		assertEquals(expectedDate.compareTo(this.lincItaly.getDate()), 0);
		assertEquals(expectedDate.compareTo(this.lincCanada.getDate()), 0);
		
	}
	
	@Test
	public void testCheckDigit() {

		
		assertTrue(this.lincItaly.getCheckDigit() >= 0 && this.lincItaly.getCheckDigit() < 10);
		assertTrue(this.lincNewZealand.getCheckDigit() >= 0 && this.lincNewZealand.getCheckDigit() < 10);
		assertTrue(this.lincCanada.getCheckDigit() >= 0 && this.lincCanada.getCheckDigit() < 10);
		
		int actualCheckDigitSpain = this.lincSpain.getCheckDigit();
		assertTrue(actualCheckDigitSpain >= 0 && actualCheckDigitSpain < 10);
		
		
		
		
		/**
		 * El valor del digito de control es la suma modulo nueve de todos los caracteres (Valor ASCII) 
		 * y los respectivos numeros.
		 */
		String characters = this.lincSpain.getType() + this.lincSpain.getDate() + 
				this.lincSpain.getCountryCode() +this.lincSpain.getRandomCharacters();
		
		int expectedCheckDigitSpain = 0;
		
		for(int i=0; i<characters.length(); i++){
			
			if(Character.isLetter(characters.charAt(i))){
				expectedCheckDigitSpain += Character.valueOf(characters.charAt(i));
		    } else {
		    	try {
		    		expectedCheckDigitSpain += Integer.parseInt(String.valueOf(characters.charAt(i)));
		    	} catch(NumberFormatException e) { 
				    
			    }
		    }
		}
		
		assertEquals(expectedCheckDigitSpain%9, actualCheckDigitSpain);
		
		
	}
	
}
