package es.unileon.ulebank.account.liquidation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.unileon.ulebank.account.Account;

/**
 * Class to manage the AbstractFeatureExtractors
 * 
 * @author runix
 *
 * @param <T>
 */
public class Features<T> {

    /**
     * The list of the featureExtractors
     */
    private final List<AbstractFeatureExtractor<T>> featureExtractors;

    /**
     * Create a new feature
     */
    public Features() {
        this.featureExtractors = new ArrayList<AbstractFeatureExtractor<T>>();
    }

    /**
     * Add a new feature
     * 
     * @param featureExtractor
     *            ( Feature to add )
     * @return ( true if success, else otherwise )
     */
    public boolean addFeature(AbstractFeatureExtractor<T> featureExtractor) {
        return this.featureExtractors.add(featureExtractor);
    }

    /**
     * Get the features
     * 
     * @return
     */
    public Map<String, T> getFeatures() {
        final Map<String, T> features = new HashMap<String, T>();
        for (final AbstractFeatureExtractor<T> feature : this.featureExtractors) {
            features.put(feature.getFeatureName().replace(" ", ""),
                    feature.getFeature());
        }
        return features;
    }

    /**
     * Generate a random features
     * 
     * @return
     */
    public Map<String, T> generateRandomFeatures() {
        final Map<String, T> features = new HashMap<String, T>();
        for (final AbstractFeatureExtractor<T> feature : this.featureExtractors) {
            features.put(feature.getFeatureName().replace(" ", ""),
                    feature.generateRandomFeature());
        }
        return features;
    }

    /**
     * Get the feature names
     * 
     * @return
     */
    public String[] getFeaturesNames() {
        final String[] names = new String[this.featureExtractors.size()];
        int i = 0;
        for (final AbstractFeatureExtractor<T> feature : this.featureExtractors) {
            names[i++] = feature.getFeatureName();
        }
        return names;
    }

    /**
     * Update the features
     * 
     * @param account
     * @param min
     * @param max
     */
    void updateFeatures(Account account, Date min, Date max) {
        for (final AbstractFeatureExtractor<T> feature : this.featureExtractors) {
            feature.updateFeature(account, min, max);
        }
    }
}
