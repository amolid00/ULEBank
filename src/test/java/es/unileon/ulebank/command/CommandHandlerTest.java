package es.unileon.ulebank.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.payments.handler.CardHandler;


public class CommandHandlerTest {

	private CommandHandler test;
	private CommandHandler test2;
	private Handler handler;
	private Handler bankHandler;

	@Before
	public void setUp() throws Exception {
		bankHandler = new BankHandler("1234");
		this.handler = new CardHandler(this.bankHandler, "01", "123456789");
		test = new CommandHandler(this.handler);
	}

	@Test
	public void testCommandHandlerNotNull() {
		assertNotNull(test);
	}

	@Test
	public void testCommandHandlerNull() {
		assertNull(test2);
	}

	@Test
	public void testCommandHandlerCompareEquals() {
		assertEquals(0, test.compareTo(test));
	}

	@Test
	public void testCommandHandlerCompareNotEquals() throws MalformedHandlerException {
		Handler handlerAux = new CardHandler(this.bankHandler, "02", "123456789");
		this.test2 = new CommandHandler(handlerAux);
		assertFalse(test2.compareTo(test)==0);
	}

	@Test
	public void testCommandGetId() {
		assertEquals(test.getId(), this.handler);
	}

	@Test
	public void testCommandGetDate() {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssss");
		String date = dateFormat.format(new Date());
		assertEquals(date, test.getDate());
	}

	@Test
	public void testCommandToString() {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmssss");
		String date = dateFormat.format(new Date());
		assertEquals(this.handler.toString() + " " + date.toString(), test.toString());
	}
}
