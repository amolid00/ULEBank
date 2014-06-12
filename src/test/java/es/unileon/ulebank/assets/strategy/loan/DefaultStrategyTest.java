package es.unileon.ulebank.assets.strategy.loan;

import org.junit.Assert;
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
    private String description1, description2;

    @Before
    public void setUp() throws LINCMalformedException, CommissionException,
            LoanException, MalformedHandlerException, WrongArgsException {

        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);

        this.authorizedHandler1 = new GenericHandler("Carlitos");
        this.authorized1 = new Person(71560136, 'Y');

        this.commercialAccount1 = new Account(this.office, this.bank,
                "0000000000", this.authorized1);

        this.commercialAccount1.addTitular(this.authorized1);
        this.idLoan = new FinancialProductHandler("LN", "ES");
        this.description1 = "Compra BMW-M3";
        this.description2 = "Compra moto";
        // inicializo una loan con una deuda de 3000
        this.loanBig = new Loan(this.idLoan, 3000, 0.01, PaymentPeriod.MONTHLY,
                30, this.commercialAccount1, this.authorized1,
                this.description1);
        this.loanSmall = new Loan(this.idLoan, 2, 0.01, PaymentPeriod.MONTHLY,
                1, this.commercialAccount1, this.authorized1, this.description2);

        final DefaultLoanStrategy strategy = new DefaultLoanStrategy(
                this.loanBig);
        this.loanBig.setStrategy(strategy);

    }

    // compruebo que los pagos se planifican y que el numero de pagos es el que
    // espero
    @Test
    public void paymentsAreCalculatedInBigLoan() {

        Assert.assertNotNull(this.loanBig.getPayments());
        // ya que aumenta la deuda no se puede pagar en 30 plazos y se tiene que
        // pagar en 31
        Assert.assertEquals(31, this.loanBig.getPayments().size());
    }

    // compruebo que los valores de los pagos planificados estan bien
    @Test
    public void paymentsCalculatedPropperlyInBigLoan() {
        Assert.assertEquals(101.3, this.loanBig.getPayments().get(0)
                .getImportOfTerm(), 0.01);
        Assert.assertEquals(2901.2, this.loanBig.getPayments().get(0)
                .getOutstandingCapital(), 0.1);
        Assert.assertEquals(101.3, this.loanBig.getPayments().get(1)
                .getImportOfTerm(), 0.1);
        Assert.assertEquals(2802.32, this.loanBig.getPayments().get(1)
                .getOutstandingCapital(), 0.1);

    }

    @Test
    public void paymentsAreCalculatedInSmallLoan() {

        Assert.assertNotNull(this.loanSmall.getPayments());
        // ya que aumenta la deuda no se puede pagar en 30 plazos y se tiene que
        // pagar en 31
        Assert.assertEquals(1, this.loanSmall.getPayments().size());

    }

    // compruebo que los valores de los pagos planificados estan bien
    @Test
    public void paymentsCalculatedPropperlyISmallLoan() {
        Assert.assertEquals(2, this.loanSmall.getPayments().get(0)
                .getImportOfTerm(), 0.01);

    }

    // compruebo que en la lista de pagos planificados solo hay un elemento
    // forzando excepcion
    // IndexOutOfBondException
    @Test(expected = java.lang.IndexOutOfBoundsException.class)
    public void justOneElementInSmallLoan() {
        Assert.assertEquals(2, this.loanSmall.getPayments().get(1)
                .getImportOfTerm(), 0.01);

    }
}
