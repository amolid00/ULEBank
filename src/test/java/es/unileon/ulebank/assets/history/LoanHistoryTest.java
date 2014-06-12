package es.unileon.ulebank.assets.history;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.assets.handler.GenericLoanHandler;
import es.unileon.ulebank.assets.handler.ScheduledPaymentHandler;
import es.unileon.ulebank.assets.strategy.loan.ScheduledPayment;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;

public class LoanHistoryTest {
	private LoanHistory loanHistory;
	private ScheduledPaymentHandler handlerPayment;
	private GenericHandler authorizedHandler1;
	private Person authorized1;

	@Before
	public void setUp() throws MalformedHandlerException {
		this.loanHistory = new LoanHistory();
		
		this.authorizedHandler1 = new GenericHandler("Miguel");
		this.authorized1 = new Person(71560136,'Y');
		
		ArrayList<Client> clients = new ArrayList<Client>();
		clients.add(this.authorized1);
		
		//MG-4-2014-CA-P4YRK-0
		this.handlerPayment = new ScheduledPaymentHandler(new GenericLoanHandler("MG-4-2014-CA-P4YRK-0"), clients, new Date());
		
	}

	@Test
	public void testAddAllPayments() {
		ArrayList<ScheduledPayment> payments = new ArrayList<ScheduledPayment>();
		 payments.add(new ScheduledPayment(new Date(), 100000, 50000, 50000,
		 400000, this.handlerPayment));
		 payments.add(new ScheduledPayment(new Date(), 100000, 50000, 50000,
		 350000, this.handlerPayment));
		 payments.add(new ScheduledPayment(new Date(), 100000, 50000, 50000,
		 200000, this.handlerPayment));
		 payments.add(new ScheduledPayment(new Date(), 100000, 50000, 50000,
		 150000, this.handlerPayment));

		this.loanHistory.addAllPayments(payments);
		assertEquals(4, this.loanHistory.historySize());

		ScheduledPayment payment = new ScheduledPayment(new Date(), 100000,
				50000, 50000, 100000, this.handlerPayment);
		this.loanHistory.addPayment(payment);
		assertEquals(5, this.loanHistory.historySize());
	}

	@Test
	public void testAddPayment() {
		ScheduledPayment payment = new ScheduledPayment(new Date(), 100000,
				50000, 50000, 100000, this.handlerPayment);

		this.loanHistory.addPayment(payment);
		assertEquals(1, this.loanHistory.historySize());
	}

	@Test
	public void testRemovePayment() {
		ScheduledPayment payment = new ScheduledPayment(new Date(), 100000,
				50000, 50000, 100000, this.handlerPayment);

		this.loanHistory.addPayment(payment);
		assertEquals(1, this.loanHistory.historySize());

		this.loanHistory.removePayment(payment);
		assertEquals(0, this.loanHistory.historySize());
	}
}
