package es.unileon.ulebank.assets.iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.exceptions.LoanException;
import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class LoanIteratorDatesTest {


	private Bank bank;
	private Office office;
	private Account commercialAccount;
	private GenericHandler authorizedHandler1;
	private Person authorized1;

	private Loan loan;
	private Calendar dateWrapStart, dateWrapEnd;
	private String description;

	@Before
	public void setUp() throws MalformedHandlerException, LoanException, WrongArgsException {
		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);
		this.authorizedHandler1 = new GenericHandler("Miguel");
		this.authorized1 = new Person(71560136,'Y');
		this.commercialAccount = new Account(office, bank, "0000000000", authorized1);
		this.description = "Compra BMW-M3";
		this.loan = new Loan(new FinancialProductHandler("LN", "ES"), 100000,
				0.15, PaymentPeriod.MONTHLY, 24, this.commercialAccount,description);

		this.dateWrapStart = Calendar.getInstance();
		this.dateWrapEnd = Calendar.getInstance();
		this.dateWrapEnd.add(Calendar.MONTH, 5);

	}

	@Test
	public void testIteratorDates() {
		int expectedSize = 5;
		int actualSize = 0;
		
		LoanIteratorDates iterator = this.loan.iterator(
				this.dateWrapStart.getTime(), this.dateWrapEnd.getTime());
		while (iterator.hasNext()) {
			actualSize++;
			iterator.next();
		}

		assertEquals(expectedSize, actualSize, 0);
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());

	}

}
