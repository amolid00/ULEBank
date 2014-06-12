/*
 * File created by Roberto Santalla "Roobre" for ULEBank.
 * I'm not responsible of any harm dealt by the following code, 
 * including unhandled exceptions, segmentation faults, computer explosions
 * or nuclear wars.
 */

package es.unileon.ulebank.exceptions;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;

/**
 *
 * @author roobre
 */
public class AccountNotBelongingToUserException extends Exception {
    public AccountNotBelongingToUserException(Client c, Account acc) {
        super("Account " + acc + " does not belong to " + c);
    }
}
