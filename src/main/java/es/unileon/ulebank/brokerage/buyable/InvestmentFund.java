package es.unileon.ulebank.brokerage.buyable;

import es.unileon.ulebank.users.Employee;
import es.unileon.ulebank.brokerage.pack.InvestmentFundPack;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.handler.Handler;

public class InvestmentFund extends Buyable {

    private final Employee opener;
    private double profitability;
    private FeeStrategy fee, cancellationFee;

    public InvestmentFund(Handler id, int amount, double totalPrice, Employee opener, FeeStrategy fee, FeeStrategy cancellationFee, double profitability) throws InvalidBuyableException {
        super(id, amount, totalPrice);


        if (profitability < 1) {
            throw new InvalidBuyableException("Profitability", "greater", 1);
        }

        this.opener = opener;
        this.fee = fee;
        this.cancellationFee = cancellationFee;
        this.profitability = profitability;
    }

    /**
     * @return the opener
     */
    public Employee getOpener() {
        return opener;
    }

    /**
     * @param participations the participations to set
     * @throws es.unileon.ulebank.brokerage.buyable.TotalLowerThanBoughtException
     */
    public void setParticipations(int participations) throws TotalLowerThanBoughtException {
        if (participations < this.purchasedAmount) {
            throw new TotalLowerThanBoughtException();
        }

        this.amount = participations;
    }

    /**
     * @return the fee
     */
    public FeeStrategy getFee() {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(FeeStrategy fee) {
        this.fee = fee;
    }

    /**
     * @return the profitability
     */
    public double getProfitability() {
        return profitability;
    }

    /**
     * @param profitability the profitability to set
     */
    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    @Override
    public InvestmentFundPack buy(long amount, Employee operator) throws NotEnoughParticipationsException {
        if (amount > (this.amount - this.purchasedAmount)) {
            throw new NotEnoughParticipationsException();
        }

        this.purchasedAmount += amount;

        return new InvestmentFundPack(this, amount);
    }
}
