package es.unileon.ulebank.assets;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.exceptions.LoanException;
import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.handler.exceptions.LINCMalformedException;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class ClientProductsTest {
	public ClientProducts clientProducts;
	private Loan loanWrongInterest, loanCancel, loanAmortize, loanNotAllowed,
			genericLoan;

	private Bank bank;
	private Office office;
	private Account commercialAccount1, commercialAccount2;
			
	private GenericHandler authorizedHandler1, authorizedHandler2;
	private Person authorized1, authorized2;
	private String description1, description2, description3;
	private FinancialProductHandler handlerLoanAmortize;
	private FinancialProductHandler handlerGenericLoan;
	private FinancialProductHandler handlerLoanCancel;
	
	

	@Before
	public void setUp() throws MalformedHandlerException, TransactionException,
			LoanException, LINCMalformedException, WrongArgsException,
			ParseException {
	
		this.bank = new Bank(new GenericHandler("1234"));
		this.office = new Office(new GenericHandler("1234"), bank);

		this.authorizedHandler1 = new GenericHandler("Miguel");
		this.authorized1 = new Person(71560136,'Y');

		this.authorizedHandler2 = new GenericHandler("Antonio");
		this.authorized2 = new Person(71557005, 'A');

		this.commercialAccount1 = new Account(this.office, this.bank,
				"0000000000", authorized1);
		this.commercialAccount2 = new Account(this.office, this.bank,
				"0000000001", authorized2);
	
		
		this.description1 = "Compra BMW-M3";
		this.description2 = "Hipoteca";
		this.description3 = "Hipoteca cara";
		
		handlerLoanAmortize=new FinancialProductHandler("LN", "ES");
		handlerGenericLoan=new FinancialProductHandler("LN", "ES");
		handlerLoanCancel=new FinancialProductHandler("LN", "ES");

		// we create a loan of 100000 euros linked to the account
		this.loanCancel = new Loan(handlerLoanCancel,
				100000, 0.15, PaymentPeriod.MONTHLY, 23,
				this.commercialAccount1, description1);
		this.genericLoan = new Loan(handlerGenericLoan,
				200000, 0.15, PaymentPeriod.MONTHLY, 23,
				this.commercialAccount1, description3);
		
	
		
		this.loanAmortize = new Loan(handlerLoanAmortize,
				168000, 0.20, PaymentPeriod.ANNUAL, 72,
				this.commercialAccount2, description2);
		this.clientProducts =new ClientProducts();

	}

	@Test
	public void addTest() {
		this.clientProducts.add(genericLoan);
		assertEquals(this.clientProducts.getFinancialProduct(0),this.genericLoan);
		this.clientProducts.add(loanCancel);
		assertEquals(this.clientProducts.getFinancialProduct(1),this.loanCancel);
		
	}
	@Test
	public void reemoveTest() {
		this.clientProducts.add(genericLoan);
		assertEquals(this.clientProducts.getFinancialProduct(0),this.genericLoan);
		this.clientProducts.add(loanCancel);
		assertEquals(this.clientProducts.getFinancialProduct(1),this.loanCancel);
		
		assertEquals(this.clientProducts.remove(loanCancel),this.loanCancel);
	}
	@Test
	public void searchTest() {
		this.clientProducts.add(genericLoan);
		this.clientProducts.add(loanNotAllowed);
		this.clientProducts.add(loanAmortize);
		this.clientProducts.add(loanWrongInterest);
		this.clientProducts.add(loanCancel);
		assertEquals(this.clientProducts.search(handlerLoanCancel).getId().toString(),loanCancel.getId().toString());
	}
	
}
