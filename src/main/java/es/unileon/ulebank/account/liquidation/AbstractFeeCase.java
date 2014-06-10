package es.unileon.ulebank.account.liquidation;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.history.Transaction;

/**
 * Define the structure for a liquidation case
 * 
 * @author runix
 *
 * @param <T>
 */
public interface AbstractFeeCase<T> {

    /**
     * Check if the case if triggered
     * 
     * @return
     */
    public boolean triggerCase();

    /**
     * Calculate the amount
     * 
     * @return
     * @throws TransactionException
     */
    public Transaction calculateAmount() throws TransactionException;

    /**
     * Get the features used by the FeeCase
     * 
     * @return
     */
    public Features<T> getFeatures();
}
