package es.unileon.ulebank.account.liquidation;

import java.util.Date;

import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.Transaction;

/**
 * Define the liquidationFee's structure
 * 
 * @author runix
 *
 * @param <T>
 */
public interface AbstractLiquidationFee<T> {

    /**
     * Add a new case
     * 
     * @param feeCase
     *            (fee case to add)
     * @return ( true if success, false otherwise)
     */
    public boolean addFeeCase(AbstractFeeCase<T> feeCase);

    /**
     * Calculate the fee.
     * 
     * @param min
     *            ( min date )
     * @param max
     *            ( max date )
     * @return ( The transaction )
     * @throws TransactionExceptiond
     *             (If the transaction cannot be generated)
     */
    public Transaction calculateFee(Date min, Date max)
            throws TransactionException;

    /**
     * Get the liquidationFee's id
     * 
     * @return
     */
    public Handler getId();
}
