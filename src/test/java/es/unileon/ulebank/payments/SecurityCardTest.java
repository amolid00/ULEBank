package es.unileon.ulebank.payments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.payments.exceptions.SecurityCardException;
import es.unileon.ulebank.payments.handler.CardHandler;
import es.unileon.ulebank.utils.CardProperties;

/**
 * Test about SecurityCard Class
 * @author Rober dCR
 * @date 26/03/2014
 */
public class SecurityCardTest {

	public Card card;
	public SecurityCard secCard;
	public SecurityCard secCard2;

	@Before
	public void setUp() throws CommandException, MalformedHandlerException, NumberFormatException, IOException{
		CardProperties properties = new CardProperties();
		properties.setCvvSize(3);
		properties.setPinSize(4);
		properties.setMinimumLimit(200.0);
		properties.setExpirationYear(3);
		properties.setDimensionColumns(10);
		properties.setDimensionRow(4);
		this.card = new CreditCard(new CardHandler("123401123456789"), new Person(71034506,'H'), null);
		this.card.setPin("0000");
		this.secCard = new SecurityCard(this.card);
	}

	@Test
	public void securityCardOk() {
		assertNotNull(this.secCard);
	}

	@Test
	public void securityCardNull() {
		assertNull(this.secCard2);
	}

	@Test
	public void securityCarNotdOk() {
		assertNotNull(this.secCard);
	}

	@Test
	public void checkCoordinateTest() throws SecurityCardException, SecurityCardException {
		Integer[][] auxSec = this.secCard.deliverSecurityCard("0000");
		assertFalse(this.secCard.checkCoordinates(0, 0, auxSec[0][0]+1));
		assertTrue(this.secCard.checkCoordinates(0, 0, auxSec[0][0]));
	}

	@Test (expected = SecurityCardException.class)
	public void checkCoordinateRowOutOfRangeTest() throws SecurityCardException{
		this.secCard.checkCoordinates(4, 0, 20);
	}

	@Test (expected = SecurityCardException.class)
	public void checkCoordinateColumnOutOfRangeTest() throws SecurityCardException{
		this.secCard.checkCoordinates(0, 10, 20);
	}

	@Test
	public void deliverSecurityCardOkTest() throws SecurityCardException{
		assertNotNull(this.secCard.deliverSecurityCard("0000"));
	}

	@Test (expected = SecurityCardException.class)
	public void deliverSecurityCardNotOkTest() throws SecurityCardException{
		this.secCard.deliverSecurityCard("00");
	}

	@Test (expected = SecurityCardException.class)
	public void deliverSecurityCardTwiceTest() throws SecurityCardException{
		assertNotNull(this.secCard.deliverSecurityCard("0000"));
		this.secCard.deliverSecurityCard("0000");
	}
	
	@Test
	public void getAssociatedCardTest() {
		assertEquals(this.secCard.getAssociatedCard(), this.card);
	}
}
