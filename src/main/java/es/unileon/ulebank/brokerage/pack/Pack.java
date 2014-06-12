/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.brokerage.pack;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.brokerage.buyable.Buyable;

/**
 *
 * @author roobre
 */
public abstract class Pack {

    protected final Buyable product;

    protected Account account;
    protected long amount;

    public Pack(Buyable product, long amount) {
        this.product = product;
        this.amount = amount;
    }

    /**
     * @return the amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @return the product
     */
    public Buyable getProduct() {
        return product;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }
    
    /**
     * @param amount the amount to set
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }
}
