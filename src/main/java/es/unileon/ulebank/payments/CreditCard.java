package es.unileon.ulebank.payments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.CardTransaction;
import es.unileon.ulebank.payments.exceptions.PaymentException;

/**
 * @author Israel, Rober dCR Clase que representa la tarjeta de credito
 */
public class CreditCard extends Card {

    /**
     * Version
     */
    private static final long serialVersionUID = 1L;
    /**
     * Dia del mes en el que se carga los gastos de la tarjeta
     */
    private int monthDay;
    /**
     * Lista de transacciones de la tarjeta
     */
    private List<CardTransaction> transactionList;

	/**
     * Constructor de la clase que crea una tarjeta de Credito
     * 
     * @param cardId
     * @param owner
     * @param account
     * @throws PaymentException
     */
    public CreditCard(Handler cardId, Client owner, Account account) throws PaymentException {
        super(cardId, CardType.CREDIT.toString(), account, owner);
        transactionList = new ArrayList<CardTransaction>();
    }

    /**
     * Constructor de la clase
     */
    public CreditCard() {
        super(CardType.CREDIT.toString());
    }

    /**
     * Method that makes the payment
     * 
     * @param quantity
     *            Amount of the payment
     * @param payConcept
     *            Concept of the payment
     * @throws PaymentException
     * @throws TransactionException
     * @throws TransactionException
     */
    @Override
    public void makeTransaction(double quantity, String payConcept)
            throws PaymentException {
        // Agyadimos la transaccion a la lista
        final CardTransaction transaction = new CardTransaction(quantity,
                new Date(), payConcept);
        transaction.setEffectiveDate(this.obtainEffectiveDate());
        this.transactionList.add(transaction);
    }

    /**
     * Method that obtain the day when the amount of the purchases is done.
     * 
     * @return
     */
    public int getMonthDay() {
        return this.monthDay;
    }

    /**
     * Method that sets the day when the amount of the purchases is done.
     * 
     * @param monthDay
     */
    public void setMonthDay(int monthDay) {
        this.monthDay = monthDay;
    }

    /**
     * Method that calculate the effective day when the payment is taken
     * 
     * @return date
     */
    @SuppressWarnings("deprecation")
    private Date obtainEffectiveDate() {
        final Date effectiveDate = new Date();
        effectiveDate.setDate(this.monthDay);
        if (effectiveDate.getMonth() != 11) {
            effectiveDate.setMonth(effectiveDate.getMonth() + 1);
        } else {
            effectiveDate.setMonth(0);
            effectiveDate.setYear(effectiveDate.getYear() + 1);
        }

        return effectiveDate;
    }

    /**
     * Method that calculate the amount of all transaction which have the
     * effectiveDate
     * 
     * @param effectiveDate
     * @return amount of purchases
     */
    public double getAmount(Date effectiveDate) {
        double amount = 0.0;

        // Recorremos todas las transacciones de la lista acumulando las
        // cantidades de dichas transacciones
        for (int i = 0; i < this.transactionList.size(); i++) {
            if (this.transactionList.get(i).getEffectiveDate()
                    .compareTo(effectiveDate) == 0) {
                amount += this.transactionList.get(i).getAmount();
            }
        }

        return amount;
    }
    
    /**
     * Method that obtain the list of transactions
     * 
     * @return
     */
    public List<CardTransaction> getTransactionList() {
		return transactionList;
	}

    /**
     * Method to set the transaction list
     * 
     * @param transactionList
     */
	public void setTransactionList(List<CardTransaction> transactionList) {
		this.transactionList = transactionList;
	}
}
