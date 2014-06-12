package es.unileon.ulebank.brokerage;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.brokerage.buyable.Enterprise;
import es.unileon.ulebank.brokerage.buyable.InvalidBuyableException;
import es.unileon.ulebank.brokerage.buyable.InvestmentFund;
import es.unileon.ulebank.brokerage.buyable.NotEnoughParticipationsException;
import es.unileon.ulebank.brokerage.history.PackTransaction;
import es.unileon.ulebank.brokerage.pack.InvestmentFundPack;
import es.unileon.ulebank.brokerage.pack.Pack;
import es.unileon.ulebank.brokerage.pack.StockPack;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.AccountNotBelongingToUserException;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.fees.FeeTransaction;
import es.unileon.ulebank.fees.InvalidFeeException;
import es.unileon.ulebank.fees.LinearFee;
import es.unileon.ulebank.history.History;
import es.unileon.ulebank.users.Employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvestmentAccount {

    private final Client client;
    private final Account account;
    private final ArrayList<Pack> stockPacks;
    private final ArrayList<Pack> fundPacks;
    private final History<PackTransaction> history;
    private FeeStrategy buyStockageFee, sellStockageFee;

    public InvestmentAccount(Client c, Account acc) {
        this.stockPacks = new ArrayList<Pack>();
        this.fundPacks = new ArrayList<Pack>();

        this.history = new History<PackTransaction>();

        this.client = c;
        this.account = acc;

        try {
            this.buyStockageFee = new LinearFee(5, 0.01);
            this.sellStockageFee = new LinearFee(5, 0.01);
        } catch (InvalidFeeException ex) {
            // This will NEVER happen.
            // At least I hope so.
            Logger.getLogger(InvestmentAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Builds a new StockPack, generates the transaction and adds it to the
     * history and withraws the money from the buyer's account.
     *
     * @param e
     * @param acc Account where the amount will be charged.
     * @param amount Amount of stockage to buy
     * @param operator Bank operator which performs the transaction (the one who
     * is logged in probably).
     * @throws es.unileon.ulebank.brokerage.buyable.InvalidBuyableException
     * @throws es.unileon.ulebank.account.exception.BalanceException
     * @throws
     * es.unileon.ulebank.brokerage.buyable.NotEnoughParticipationsException
     * @throws es.unileon.ulebank.history.TransactionException
     * @throws es.unileon.ulebank.exceptions.AccountNotBelongingToUserException
     */
    public void buyStockage(Enterprise e, Account acc, int amount, Employee operator) throws InvalidBuyableException, NotEnoughParticipationsException, TransactionException, AccountNotBelongingToUserException {
        if (this.client.existsAccount(this.account.getID())) {
            throw new AccountNotBelongingToUserException(this.client, this.account);
        }

        StockPack p = e.buy(amount, operator);
        p.setAccount(acc);
        stockPacks.add(p);

        double price = amount * e.getPPP();
        PackTransaction transaction = new PackTransaction(-price, new Date(), "Compra de acciones", p, operator);
        FeeTransaction feeTransaction = new FeeTransaction(-this.getSellStockageFee().getFee(price), new Date(), transaction);

        try {
            p.getAccount().doTransaction(transaction);
            p.getAccount().doTransaction(feeTransaction);
        } catch (TransactionException ex) {
            // TODO: Wtf is TransactionException.
            Logger.getLogger(InvestmentAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.history.add(transaction);
    }

    public void sellStockage(StockPack p, int amount, Employee operator) throws NotEnoughParticipationsException, TransactionException, AccountNotBelongingToUserException {
        if (this.client.existsAccount(this.account.getID())) {
            throw new AccountNotBelongingToUserException(this.client, this.account);
        }

        if (p.getAmount() < amount) {
            throw new NotEnoughParticipationsException();
        }

        p.setAmount(p.getAmount() - amount);

        double price = p.getProduct().getPPP() * amount;
        PackTransaction transaction = new PackTransaction(price, new Date(), "Venta de acciones", p, operator);
        FeeTransaction feeTransaction = new FeeTransaction(-this.getSellStockageFee().getFee(price), new Date(), transaction);

        try {
            p.getAccount().doTransaction(transaction);
            p.getAccount().doTransaction(feeTransaction);
        } catch (TransactionException ex) {
            // TODO: Wtf is TransactionException.
            Logger.getLogger(InvestmentAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.history.add(transaction);

        this.prunePacks();
    }

    public void buyInvestmentFund(InvestmentFund i, Account acc, int amount, Employee operator) throws InvalidBuyableException, NotEnoughParticipationsException, TransactionException, AccountNotBelongingToUserException {
        if (this.client.existsAccount(this.account.getID())) {
            throw new AccountNotBelongingToUserException(this.client, this.account);
        }

        InvestmentFundPack ifp = i.buy(amount, operator);
        fundPacks.add(ifp);
        double price = amount * i.getPPP();
        PackTransaction transaction = new PackTransaction(-price, new Date(), "Compra de fondos", ifp, operator);
        FeeTransaction feeTransaction = new FeeTransaction(-i.getFee().getFee(price), new Date(), transaction);

        try {
            acc.doTransaction(transaction);
            acc.doTransaction(feeTransaction);
        } catch (TransactionException ex) {
            // TODO: Wtf is TransactionException.
            Logger.getLogger(InvestmentAccount.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.history.add(transaction);
    }

    /**
     * @return the fundHistory
     */
    public History<PackTransaction> getHistory() {
        return history;
    }

    private void prunePacks() {
        // TODO: Delete 0-participations packs.
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @return the buyStockageFee
     */
    public FeeStrategy getBuyStockageFee() {
        return buyStockageFee;
    }

    /**
     * @param buyStockageFee the buyStockageFee to set
     */
    public void setBuyStockageFee(FeeStrategy buyStockageFee) {
        this.buyStockageFee = buyStockageFee;
    }

    /**
     * @return the sellStockageFee
     */
    public FeeStrategy getSellStockageFee() {
        return sellStockageFee;
    }

    /**
     * @param sellStockageFee the sellStockageFee to set
     */
    public void setSellStockageFee(FeeStrategy sellStockageFee) {
        this.sellStockageFee = sellStockageFee;
    }
}
