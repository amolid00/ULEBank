package es.unileon.ulebank.fees;

import java.io.Serializable;

public interface FeeStrategy extends Serializable {

    /**
     * Returns the fee that should be applied to the given amount.
     *
     * @param value
     * @return The fee that should be applied to the given amount
     */
    public double getFee(double value);
    
}
