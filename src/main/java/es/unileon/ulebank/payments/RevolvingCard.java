package es.unileon.ulebank.payments;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.handler.Handler;

/**
 * Revolving Card Class
 * @author Rober dCR
 * @date 14/05/2014
 * @brief Class which implemtens the features of the revolving card
 */
public class RevolvingCard extends Card {
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Interest establish by the office
	 */
	private float interest = 1;
	
	/**
	 * Class Constructor 
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
	 * @param limitDebit
	 */
	public RevolvingCard(Handler cardId, Client owner, Account account,
			double buyLimitDiary, double buyLimitMonthly,
			double cashLimitDiary, double cashLimitMonthly,
			FeeStrategy commissionEmission,
			FeeStrategy commissionMaintenance,
			FeeStrategy commissionRenovate) {
		super(cardId, CardType.REVOLVING.toString(), account, owner, buyLimitDiary, buyLimitMonthly,
				cashLimitDiary, cashLimitMonthly, commissionEmission,
				commissionMaintenance, commissionRenovate);
	}

	/**
	 * Getter of the interest
	 * @return float
	 */
	public float getInterest() {
		return interest;
	}

	/**
	 * Setter of the interest
	 * @param interest
	 */
	public void setInterest(float interest) {
		this.interest = interest;
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
