package es.unileon.ulebank.brokerage.buyable;

import es.unileon.ulebank.brokerage.pack.StockPack;
import es.unileon.ulebank.users.Employee;
import java.util.Date;

public class Enterprise extends Buyable {

    public Enterprise(EnterpriseHandler id, long amount, double totalPrice) throws InvalidBuyableException {
        super(id, amount, totalPrice);
    }

    @Override
    public StockPack buy(long amount, Employee operator) throws NotEnoughParticipationsException {
        if (amount > (this.amount - this.purchasedAmount)) {
            throw new NotEnoughParticipationsException();
        }

        this.purchasedAmount += amount;

        return new StockPack(this, amount, this.getPPP(), new Date());
    }
}
