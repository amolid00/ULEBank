package es.unileon.ulebank.fees;

import java.io.IOException;

import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.exceptions.CommissionException;
import es.unileon.ulebank.utils.CardProperties;

/**
 * @class StrategyCommissionDebitMaintenance
 * @author Rober dCR
 * @date 19/03/2014
 * @brief Class that obtain Commision of Maintenance in Debit Cards
 */
public class DebitMaintenanceFee implements FeeStrategy {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Card owner
     */
    private final Client owner;

    /**
     * Commission establish by the employee
     */
    private double quantity;

    /**
     * Maximum age to differentiate commissions
     */
    private int maximum_age;

    /**
     * Quantity of the default commission
     */
    private double default_commission;

    /**
     * Class constructor
     * 
     * @param owner
     * @param quantity
     * @throws CommissionException
     * @throws IOException
     * @throws NumberFormatException
     */
    public DebitMaintenanceFee(Client owner, double quantity)
            throws CommissionException {
        this.owner = owner;
        this.default_commission = CardProperties.getDefaultFee();
        this.maximum_age = CardProperties.getMaximumAge();

        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new CommissionException("Commission can't been negative.");
        }
    }

    @Override
    public double getFee(double value) {
        if (((Person) this.owner).getAge() > this.maximum_age) {
            return this.default_commission;
        } else {
            return this.quantity;
        }
    }

}
