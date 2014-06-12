/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unileon.ulebank.brokerage.buyable;

/**
 *
 * @author roobre
 */
public class InvalidBuyableException extends Exception {
    public InvalidBuyableException(String parameter, String comparison, double value) {
        super(parameter + " must be " + comparison + " than " + value);
    }
}
