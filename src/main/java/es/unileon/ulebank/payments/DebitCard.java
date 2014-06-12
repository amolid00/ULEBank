package es.unileon.ulebank.payments;

import java.io.IOException;
import java.util.Date;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.CardTransaction;
import es.unileon.ulebank.payments.exceptions.PaymentException;

/**
 * @author Israel, Rober dCR Clase que representa una tarjeta de Debito
 */
public class DebitCard extends Card {

    /**
     * Version
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     * @param properties
     * @param cardId
     * @param owner
     * @param account
     * @param buyLimitDiary
     * @param buyLimitMonthly
     * @param cashLimitDiary
     * @param cashLimitMonthly
     * @param commissionEmission
     * @param commissionMaintenance
     * @param commissionRenovate
     * @throws NumberFormatException
     * @throws CommissionException
     * @throws IOException
     * @throws InvalidFeeException
     */
    public DebitCard(Handler cardId, Client owner, Account account) throws PaymentException {
        super(cardId, CardType.DEBIT.toString(), account, owner);
    }

    /**
     * Class constructor
     */
    public DebitCard() {
        super(CardType.DEBIT.toString());
    }

	/**
	 * Method that makes the payment
	 * @param quantity Amount of the payment
	 * @param payConcept Concept of the payment
	 * @throws PaymentException
	 * @throws TransactionException
	 */
	@Override
	public void makeTransaction(double quantity, String payConcept) throws
	PaymentException, TransactionException {

		try{
			//Discount the quantity from sender account
			this.account.doTransaction(new CardTransaction(-quantity, new Date(), payConcept));
		}catch(TransactionException e){
			throw new PaymentException("Denegate Transaction");
		}

	}
}
