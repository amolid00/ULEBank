/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.unileon.ulebank.brokerage.buyable;

/**
 * 
 * @author furiios
 */
public class NotEnoughParticipationsException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotEnoughParticipationsException() {
		super("There are not enough participations available.");
	}
}
