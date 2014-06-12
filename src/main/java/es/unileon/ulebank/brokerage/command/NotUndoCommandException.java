/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unileon.ulebank.brokerage.command;

/**
 *
 * @author steinbeck
 */
public class NotUndoCommandException extends Exception{
    
    public NotUndoCommandException(String msg){
        super(msg);
    }        
            
}
