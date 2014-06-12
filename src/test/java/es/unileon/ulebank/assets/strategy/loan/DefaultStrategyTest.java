package es.unileon.ulebank.assets.strategy.loan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.exceptions.LoanException;
import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.handler.exceptions.LINCMalformedException;
import es.unileon.ulebank.assets.strategy.commission.exception.CommissionException;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class DefaultStrategyTest {

	private Loan loanBig, loanSmall;
	private FinancialProductHandler idLoan;

	private Bank bank;
	private Office office;
	private Account commercialAccount1;
	private GenericHandler authorizedHandler1;
	private Person authorized1;
	private String description1,description2;
	
	@Before
	public void setUp() throws LINCMalformedException, CommissionException,
			LoanException, MalformedHandlerException, WrongArgsException {
		
		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);
		
		this.authorizedHandler1 = new GenericHandler("Carlitos");
		this.authorized1 = new Person(71560136,'Y');

		this.commercialAccount1 = new Account(this.office, this.bank,
				"0000000000", authorized1);
		
		this.commercialAccount1.addTitular(authorized1);
		idLoan = new FinancialProductHandler("LN", "ES");
		this.description1 = "Compra BMW-M3";
		this.description2 = "Compra moto";
		// inicializo una loan con una deuda de 3000
		loanBig = new Loan(idLoan, 3000, 0.01, PaymentPeriod.MONTHLY, 30,
				commercialAccount1,description1);
		loanSmall = new Loan(idLoan, 2, 0.01, PaymentPeriod.MONTHLY, 1,
				commercialAccount1,description2);

		DefaultLoanStrategy strategy = new DefaultLoanStrategy(loanBig);
		loanBig.setStrategy(strategy);

	}

	// compruebo que los pagos se planifican y que el numero de pagos es el que
	// espero
	@Test
	public void paymentsAreCalculatedInBigLoan() {

		assertNotNull(loanBig.getPayments());
		// ya que aumenta la deuda no se puede pagar en 30 plazos y se tiene que
		// pagar en 31
		assertEquals(31, loanBig.getPayments().size());
	}

	// compruebo que los valores de los pagos planificados estan bien
	@Test
	public void paymentsCalculatedPropperlyInBigLoan() {
		assertEquals(100.01, loanBig.getPayments().get(0).getImportOfTerm(),
				0.01);
		assertEquals(2900.01, loanBig.getPayments().get(0)
				.getOutstandingCapital(), 0.01);
		assertEquals(100.01, loanBig.getPayments().get(1).getImportOfTerm(),
				0.01);
		assertEquals(2800.03, loanBig.getPayments().get(1)
				.getOutstandingCapital(), 0.01);

	}

	@Test
	public void paymentsAreCalculatedInSmallLoan() {

		assertNotNull(loanSmall.getPayments());
		// ya que aumenta la deuda no se puede pagar en 30 plazos y se tiene que
		// pagar en 31
		assertEquals(1, loanSmall.getPayments().size());

	}

	// compruebo que los valores de los pagos planificados estan bien
	@Test
	public void paymentsCalculatedPropperlyISmallLoan() {
		assertEquals(2, loanSmall.getPayments().get(0).getImportOfTerm(), 0.01);

	}

	// compruebo que en la lista de pagos planificados solo hay un elemento
	// forzando excepcion
	// IndexOutOfBondException
	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void justOneElementInSmallLoan() {
		assertEquals(2, loanSmall.getPayments().get(1).getImportOfTerm(), 0.01);

	}
}
