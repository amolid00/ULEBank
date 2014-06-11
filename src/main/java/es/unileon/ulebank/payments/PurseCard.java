package es.unileon.ulebank.payments;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.handler.Handler;

/**
 * Purse Card Class
 * @author Israel
 * @date 14/05/2014
 * @brief Class which implements the features of the purse card
 */
public class PurseCard extends Card {

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor
	 * @param cardId
	 * @param owner
	 * @param account
	 * @param type
	 * @param buyLimitDiary
	 * @param buyLimitMonthly
	 * @param cashLimitDiary
	 * @param cashLimitMonthly
	 * @param commissionEmission
	 * @param commissionMaintenance
	 * @param commissionRenovate
	 */
	public PurseCard(Handler cardId, Client owner, Account account,
			double buyLimitDiary, double buyLimitMonthly,
			double cashLimitDiary, double cashLimitMonthly,
			FeeStrategy commissionEmission,
			FeeStrategy commissionMaintenance,
			FeeStrategy commissionRenovate) {
		super(cardId, CardType.PURSE.toString(), account, owner, buyLimitDiary, buyLimitMonthly,
				cashLimitDiary, cashLimitMonthly, commissionEmission,
				commissionMaintenance, commissionRenovate);
	}
	
	/**
	 * Method that makes the payment
	 * @param quantity Amount of the payment
	 * @param payConcept Concept of the payment
	 * @throws PaymentException
	 */
	@Override
	public void makeTransaction(double quantity, String payConcept) throws PaymentException{
		throw new UnsupportedOperationException();
	}
}
