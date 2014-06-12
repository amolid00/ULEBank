/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.brokerage.buyable;

import es.unileon.ulebank.brokerage.pack.Pack;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.users.Employee;

/**
 *
 * @author roobre
 */
public abstract class Buyable {

    protected final Handler id;
    protected long amount;
    protected int purchasedAmount;
    protected double totalPrice;

    public Buyable(Handler id, long amount, double totalPrice) throws InvalidBuyableException {
        if (totalPrice < 0) {
            throw new InvalidBuyableException("Price", "greater", 0);
        }

        if (amount < 0) {
            throw new InvalidBuyableException("Participations", "greater", 0);
        }

        this.id = id;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.purchasedAmount = 0;
    }

    public double getPPP() {
        return this.totalPrice / this.amount;
    }

    public void setPPP(double ppp, int participations) {
        this.totalPrice = ppp * participations;
    }

    public void setPPP(double ppp, double totalPrice) throws InvalidNumberOfParticipationsException, TotalLowerThanBoughtException {
        if (totalPrice % ppp != 0) {
            throw new InvalidNumberOfParticipationsException(totalPrice, ppp);
        } else if ((int) (totalPrice / ppp) < this.purchasedAmount) {
            throw new TotalLowerThanBoughtException();
        }
        this.amount = (int) (totalPrice / ppp);
    }

    /**
     * @return the fundID
     */
    public Handler getId() {
        return id;
    }

    /**
     * @return the participations
     */
    public long getParticipations() {
        return amount;
    }

    /**
     * @return the totalPrice
     */
    public double getPrice() {
        return totalPrice;
    }

    public long getBuyableParticipations() {
        return this.amount - this.purchasedAmount;
    }

    public abstract Pack buy(long amount, Employee operator) throws NotEnoughParticipationsException;
}