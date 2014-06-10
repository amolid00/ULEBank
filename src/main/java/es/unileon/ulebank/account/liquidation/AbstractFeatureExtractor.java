package es.unileon.ulebank.account.liquidation;

import java.util.Date;

import es.unileon.ulebank.account.Account;

/**
 * 
 * @author runix
 *
 *         AbstractFeatureExtractor define the interface for the
 *         FeatureExtractors
 *
 * @param <T>
 */
public interface AbstractFeatureExtractor<T> {

    /**
     * Get the feature name
     * 
     * @return
     */
    public String getFeatureName();

    /**
     * Update the feature
     * 
     * @param account
     *            (Account to update)
     * @param min
     *            ( min date)
     * @param max
     *            ( max date )
     */
    public void updateFeature(Account account, Date min, Date max);

    /**
     * Get the feature
     * 
     * @return
     */
    public T getFeature();

    /**
     * Get a random feature
     * 
     * @return
     */
    public T generateRandomFeature();
}
