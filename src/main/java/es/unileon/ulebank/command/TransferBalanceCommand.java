/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.command;

import es.unileon.ulebank.handler.Handler;

/**
 * 
 * @author Paula
 */
public class TransferBalanceCommand implements Command {
    public TransferBalanceCommand(float balance, float amount) {
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet.");
        // To change body of generated methods, choose Tools | Templates.
    }

    // @Override
    // public Date getEffectiveDate() {
    // // TODO Auto-generated method stub
    // return null;
    // }

    @Override
    public Handler getID() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void undo() {
        // TODO Auto-generated method stub

    }

    @Override
    public void redo() {
        // TODO Auto-generated method stub

    }

}
