package es.unileon.ulebank.assets.documents;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.assets.Loan;
import es.unileon.ulebank.assets.handler.FinancialProductHandler;
import es.unileon.ulebank.assets.handler.LoanIdentificationNumberCode;
import es.unileon.ulebank.assets.strategy.commission.PercentCommission;
import es.unileon.ulebank.assets.strategy.commission.StrategyCommission;
import es.unileon.ulebank.assets.strategy.loan.FrenchMethod;
import es.unileon.ulebank.assets.support.PaymentPeriod;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.office.Office;

public class GeneratePDFDocumentTest {

	// French Method example

	private static FrenchMethod loan;
	private Loan loan2;
	private LoanIdentificationNumberCode linc1;
	private FinancialProductHandler financialProduct1;
	private Person cliente;
	
	@Before
	public void setUp() throws Exception {
		this.linc1 = new LoanIdentificationNumberCode("LN", "ES");

		this.financialProduct1 = new FinancialProductHandler(this.linc1);

		StrategyCommission cancelCommission = new PercentCommission(0.2);
		StrategyCommission openningCommission = new PercentCommission(0.2);
		StrategyCommission modifyCommission = new PercentCommission(0.2);
		StrategyCommission studyCommission = new PercentCommission(0.2);
		
        Bank bank = new Bank(new GenericHandler("1234"));
        Office office = new Office(new GenericHandler("1234"), bank);
        GenericHandler authorizedHandler1 = new GenericHandler("Miguel");
        Client authorized1 = new Person(71560136,'Y');
        Account commercialAccount = new Account(office, bank, "0000000000", authorized1);
        commercialAccount.addAuthorized(authorized1);
        String description = "Compra BMW-M3";
        
		loan2 = new Loan(financialProduct1, 100000, 0.08,
				PaymentPeriod.MONTHLY, 10, commercialAccount,description);
		loan = new FrenchMethod(loan2);
		loan.doCalculationOfPayments();
		
		cliente = new Person(71461580, 'M');
	}
	
	@Test
	public void test() {
		GeneratePDFDocument pdf = new GeneratePDFDocument(1,new Date(), new Date(), loan, cliente);
		pdf.generatePDFDocument();
	}
}
