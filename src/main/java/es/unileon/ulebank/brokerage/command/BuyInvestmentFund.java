package es.unileon.ulebank.brokerage.command;

import es.unileon.ulebank.users.Employee;
import es.unileon.ulebank.brokerage.InvestmentAccount;
import es.unileon.ulebank.brokerage.buyable.InvalidBuyableException;
import es.unileon.ulebank.brokerage.buyable.InvestmentFund;
import es.unileon.ulebank.brokerage.buyable.NotEnoughParticipationsException;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.command.Command;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.handler.FundsHandler;
import es.unileon.ulebank.handler.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author steinbeck
 */
public class BuyInvestmentFund implements Command {

    private InvestmentFund investmentFund;
    private InvestmentAccount account;//Implementar contructor, atributos
    private Client investClient;
    private Employee opener;
    private int amount;
    private double totalPrice;
    private Handler id;
    private FundsHandler fundsId;
    private double profitability;
    private FeeStrategy cancellationFee;
    private FeeStrategy fee;

    public BuyInvestmentFund(FundsHandler fundsId, int amount, double totalPrice, Employee opener, FeeStrategy fee, FeeStrategy cancellationFee, double profitability) {

        this.fundsId = fundsId;
        this.amount = amount;
        this.opener = opener;
        this.totalPrice = totalPrice;
        this.profitability = profitability;
        this.fee = fee;
        this.cancellationFee = cancellationFee;

        try {

            this.investmentFund = new InvestmentFund(this.fundsId, this.amount, this.totalPrice, this.opener, this.fee, this.cancellationFee, profitability);
        } catch (InvalidBuyableException ex) {
            Logger.getLogger(BuyInvestmentFund.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
//    public Date getEffectiveDate() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
    public void execute() {
        try {
            this.investmentFund.buy(this.amount, this.opener);
        } catch (NotEnoughParticipationsException ex) {
            Logger.getLogger(BuyInvestmentFund.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override

    //Esto es temporal ya que se queja porque esta override y habría que
    //modificarlo en el padre. SE comprobará estos dias proximos
    //
    public void undo() {
        try {
            throw new NotUndoCommandException("This action is not avai");
        } catch (NotUndoCommandException ex) {
            Logger.getLogger(BuyInvestmentFund.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void redo() {
        try {
            this.investmentFund.buy(this.amount, this.opener);
        } catch (NotEnoughParticipationsException ex) {
            Logger.getLogger(BuyInvestmentFund.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Handler getID() {
        return this.fundsId;
    }

}
