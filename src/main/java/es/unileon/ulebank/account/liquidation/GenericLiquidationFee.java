/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.account.liquidation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.history.Transaction;

/**
 *
 * @author runix
 * @param <T>
 */
public class GenericLiquidationFee<T> implements AbstractLiquidationFee<T> {

    /**
     * List of feeCases
     */
    private final List<AbstractFeeCase<T>> feeCases;
    /**
     * Features
     */
    private final Features<T> features;
    /**
     * Account
     */
    private final Account account;
    /**
     * LiquidationFee id
     */
    private final Handler id;

    /**
     * Create a new GenericLiquidationFee
     * 
     * @param account
     * @param id
     * @param features
     */
    public GenericLiquidationFee(Account account, Handler id,
            Features<T> features) {
        this.feeCases = new ArrayList<AbstractFeeCase<T>>();
        this.account = account;
        this.features = features;
        this.id = id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.account.liquidation.AbstractLiquidationFee#addFeeCase
     * (es.unileon.ulebank.account.liquidation.AbstractFeeCase)
     */
    @Override
    public boolean addFeeCase(AbstractFeeCase<T> feeCase) {
        if (feeCase.getFeatures() == this.features) {
            return this.feeCases.add(feeCase);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.account.liquidation.AbstractLiquidationFee#calculateFee
     * (java.util.Date, java.util.Date)
     */
    @Override
    public Transaction calculateFee(Date min, Date max)
            throws TransactionException {
        boolean foundValidCase = false;
        Transaction result = null;
        int i = -1;
        this.features.updateFeatures(this.account, min, max);
        while (!foundValidCase && (++i < this.feeCases.size())) {
            foundValidCase = this.feeCases.get(i).triggerCase();
        }
        if (foundValidCase) {
            result = this.feeCases.get(i).calculateAmount();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.account.liquidation.AbstractLiquidationFee#getId()
     */
    @Override
    public Handler getId() {
        return this.id;
    }

}
