package es.unileon.ulebank.payments.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.payments.handler.CardHandler;

public class CardHandlerTest {

	CardHandler test;
	CardHandler test2;

	@Before
	public void setUp() throws Exception {
		test = new CardHandler(new BankHandler("1234"), "01", "123456789");
	}

	@Test
	public void testCardHandlerNotNull() {
		assertNotNull(test);
	}

	@Test
	public void testCardHandlerNull() {
		assertNull(test2);
	}

	@Test
	public void testCardHandlerIntInt() throws MalformedHandlerException {
		test2 = new CardHandler(new BankHandler("1234"),"01", "123456789");
		assertNotNull(test2);
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerFailOffice() throws MalformedHandlerException {
		test2 = new CardHandler(new BankHandler("1234"),"0f", "123456789");
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerFailCardId() throws MalformedHandlerException {
		test2 = new CardHandler(new BankHandler("1234"),"01", "12345678g");
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerFailCardNumberFormat() throws MalformedHandlerException {
		test2 = new CardHandler("12345678g012345");
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerFailCardNumberSize() throws MalformedHandlerException {
		test2 = new CardHandler("12345678901234567");
	}
	
	@Test
	public void testCardHandlerString() throws MalformedHandlerException {
		test2 = new CardHandler("123400009876123");
		assertEquals("1234", test2.getBankHandler().toString());
		assertEquals("00", test2.getOfficeId());
		assertEquals("009876123", test2.getCardId());
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerStringFailLength() throws MalformedHandlerException {
		test2 = new CardHandler("1232546546789052");
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerStringContainsLetter() throws MalformedHandlerException {
		test2 = new CardHandler("908423489j34242");
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerStringEmpty() throws MalformedHandlerException {
		test2 = new CardHandler("");
	}
	
	@Test
	public void testCardHandlerWithHandler() throws MalformedHandlerException {
		test2 = new CardHandler(new GenericHandler("123456789098765"));
		assertEquals("1234", test2.getBankHandler().toString());
		assertEquals("56", test2.getOfficeId());
		assertEquals("789098765", test2.getCardId());
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerWithHandlerFailLength() throws MalformedHandlerException {
		test2 = new CardHandler(new GenericHandler("1234567890987651"));
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerWithHandlerContainsLetter() throws MalformedHandlerException {
		test2 = new CardHandler(new GenericHandler("1234567890T8765"));
	}
	
	@Test (expected = MalformedHandlerException.class)
	public void testCardHandlerWithHandlerEmpty() throws MalformedHandlerException {
		test2 = new CardHandler(new GenericHandler(""));
	}
	
	@Test
	public void testGetBankIdOK() {
		assertTrue(test.getBankHandler().toString().length() == test.getBankIdLength());
		assertEquals("1234", test.getBankHandler().toString());
	}

	@Test 
	public void testGetBankIdDifferentFAIL() throws MalformedHandlerException {
		test2 = new CardHandler(new BankHandler("1235"), "01", "123456789");
		assertTrue(!test2.getBankHandler().toString().equals("1234")); //bankID is diferent
	}

	@Test (expected = MalformedHandlerException.class)
	public void testGetBankIdLenghtFAIL() throws MalformedHandlerException {
		test2 = new CardHandler(new BankHandler("12345"), "01", "123456789");
		assertTrue(test2.getBankHandler().toString().length() == test.getBankIdLength()); //bankID is too long

		test2 = new CardHandler(new BankHandler("123"), "01", "123456789");
		assertTrue(test2.getBankHandler().toString().length() == test.getBankIdLength());//bankID is too short
	}

	@Test (expected = MalformedHandlerException.class)
	public void testGetCardNumberLenghtFAIL() throws MalformedHandlerException {
		test = new CardHandler(new BankHandler("1234"), "01", "1234567890");
		assertTrue(test.toString().length() == test.getCardLength()); //too long

		test = new CardHandler(new BankHandler("1234"), "01", "12345678");
		assertTrue(test.toString().length() == test.getCardLength()); //too short
	}

	@Test
	public void testGetCardNumberOK() throws MalformedHandlerException {
		test2 = new CardHandler(new BankHandler("1234"), "01", "123456789");
		assertTrue(test2.getBankHandler().toString().length()+test2.getCardId().length()+test2.getOfficeId().length()+1 == test2.getCardLength());
	}

	@Test
	public void testGetOfficeIdOK() {
		assertTrue(test.getOfficeId().length() == test.getOfficeIdLength());
		assertEquals("01", test.getOfficeId());
	}

	@Test 
	public void testGetOfficeIdDifferentFAIL() throws MalformedHandlerException {
		test2 = new CardHandler(new BankHandler("1234"), "05", "123456789");
		assertTrue(!test2.getOfficeId().equals("01")); //officeID is diferent
	}

	@Test (expected = MalformedHandlerException.class)
	public void testGetOfficeIdLenghtFAIL() throws MalformedHandlerException {
		test2 = new CardHandler(new BankHandler("1234"), "012", "123456789");
		assertTrue(test2.getOfficeId().length() == test.getOfficeIdLength()); //officeID is too long

		test2 = new CardHandler(new BankHandler("1234"), "2", "123456789");
		assertTrue(test2.getOfficeId().length() == test.getOfficeIdLength()); //officeID is too short
	}

	@Test
	public void testGetCardIdOK() {
		assertTrue(test.getCardId().length() == test.getCardLength()-test.getBankIdLength()-test.getOfficeIdLength()-1); //lenght of cardId is equals to cardLength-bankIdLength-officeIdLength-digitControlLength
	}

	@Test
	public void testGetControlDigit() throws MalformedHandlerException {
		test2 = new CardHandler(new BankHandler("4918"), "47", "963243801");
		assertEquals(5, test2.getControlDigit());
	}

	@Test
	public void testCompareTo() throws MalformedHandlerException {
		test = new CardHandler(new BankHandler("1234"), "01", "789012345");
		test2 = new CardHandler(new BankHandler("1234"), "01", "321012345");

		assertTrue(test.compareTo(test2) != 0);
	}

	@Test
	public void testToString() throws MalformedHandlerException {
		test = new CardHandler(new BankHandler("1234"), "01", "123456789");
		assertEquals("1234 0112 3456 7892", test.toString());
	}

}
