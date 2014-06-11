package es.unileon.ulebank.account.liquidation;

import java.util.Date;

import es.unileon.ulebank.account.Account;

/**
 * MockClass for testing
 * 
 * @author runix
 *
 * @param <T>
 */
public class MockFeatureExtractor<T> implements AbstractFeatureExtractor<T> {

    /**
     * Feature
     */
    private final T feature;
    /**
     * Random feature
     */
    private final T randomFeature;
    /**
     * Feature name
     */
    private final String featureName;
    /**
     * Account updated
     */
    private Account accountUpdated;
    /**
     * Last update date
     */
    private Date min;
    /**
     * Last update date
     */
    private Date max;

    /**
     * Create a new mock FeatureExtractor class
     * 
     * @param feature
     * @param randomFeature
     * @param featureName
     */
    public MockFeatureExtractor(T feature, T randomFeature, String featureName) {
        this.feature = feature;
        this.randomFeature = randomFeature;
        this.featureName = featureName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.unileon.ulebank.account.liquidation.AbstractFeatureExtractor#
     * getFeatureName()
     */
    @Override
    public String getFeatureName() {
        return this.featureName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.account.liquidation.AbstractFeatureExtractor#updateFeature
     * (es.unileon.ulebank.account.Account, java.util.Date, java.util.Date)
     */
    @Override
    public void updateFeature(Account account, Date min, Date max) {
        this.accountUpdated = account;
        this.max = max;
        this.min = min;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * es.unileon.ulebank.account.liquidation.AbstractFeatureExtractor#getFeature
     * ()
     */
    @Override
    public T getFeature() {
        return this.feature;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.unileon.ulebank.account.liquidation.AbstractFeatureExtractor#
     * generateRandomFeature()
     */
    @Override
    public T generateRandomFeature() {
        return this.randomFeature;
    }

    /**
     * Get the account
     * 
     * @return
     */
    public Account getAccountUpdated() {
        return this.accountUpdated;
    }

    /**
     * Get the min date
     * 
     * @return
     */
    public Date getMin() {
        return this.min;
    }

    /**
     * Get the max date
     * 
     * @return
     */
    public Date getMax() {
        return this.max;
    }

}
