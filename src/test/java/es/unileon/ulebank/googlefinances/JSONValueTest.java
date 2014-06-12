package es.unileon.ulebank.googlefinances;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JSONValueTest<T> {
	
	private JSONValue<String> intValue;
	private JSONValue<String> stringValue;
	private JSONValue<String> doubleValue;
	
	@Before
	public void setUp() {
		intValue = new JSONValue<String>("3");
		stringValue = new JSONValue<String>("Google");
		doubleValue = new JSONValue<String>("1.23");
	}

	@Test
	public void testGetInt() {
		assertEquals(3, intValue.getInt());
	}

	@Test
	public void testGetDouble() {
		assertEquals(3, intValue.getDouble(), 0.0);
		assertEquals(1.23, doubleValue.getDouble(), 0.0);
	}

	@Test
	public void testGetString() {
		assertEquals("Google", stringValue.getString());
		assertEquals("3", intValue.getString());
		assertEquals("1.23", doubleValue.getString());
	}
	
	@Test (expected = NumberFormatException.class)
	public void testGetDoubleInt() {
		doubleValue.getInt();
	}
	
	@Test (expected = NumberFormatException.class)
	public void testGetStringInt() {
		stringValue.getInt();
	}
	
	@Test (expected = NumberFormatException.class)
	public void testGetBadDouble() {
		stringValue.getDouble();
	}

}
