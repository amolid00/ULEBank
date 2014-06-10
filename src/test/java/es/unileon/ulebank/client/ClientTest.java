/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.account.AccountHandler;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.bank.BankHandler;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

/**
 *
 * @author Oigrex
 */
public class ClientTest {

    private Client cliente;
    private Bank bank;
    private Office office;
    private Account cuenta;
    private Handler clientHandler;
    private Handler officeHandler;
    private AccountHandler accountHandler;
    private BankHandler bankHandler;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.bankHandler = new BankHandler("1234");
        this.office = new Office(new GenericHandler("4321"), this.bank);
        this.officeHandler = new GenericHandler("4321");
        this.clientHandler = new GenericHandler("Peter Griffin");
        this.bank.addOffice(this.office);
        this.cliente = new Client(this.clientHandler);
        this.cuenta = new Account(this.office, this.bank, "1234567890",
                this.cliente);
        this.accountHandler = new AccountHandler(this.officeHandler,
                this.bankHandler, "1234567890");
    }

    /**
     * Test of add method, of class Client.
     */
    @Test
    public void testAdd() {
        Assert.assertFalse(this.cliente.existsAccount(this.accountHandler));
        this.cliente.add(this.cuenta);
        Assert.assertTrue(this.cliente.existsAccount(this.accountHandler));
    }

    /**
     * Test of removeAccount method, of class Client.
     */
    @Test
    public void testRemoveAccount() {
        Assert.assertFalse(this.cliente.existsAccount(this.accountHandler));
        this.cliente.add(this.cuenta);
        Assert.assertTrue(this.cliente.existsAccount(this.accountHandler));
        Assert.assertTrue(this.cliente.removeAccount(this.accountHandler));
        Assert.assertFalse(this.cliente.existsAccount(this.accountHandler));
    }

    @Test
    public void testRemoveNotFoundAccount() {
        Assert.assertFalse(this.cliente.existsAccount(this.accountHandler));
        this.cliente.add(this.cuenta);
        Assert.assertTrue(this.cliente.existsAccount(this.accountHandler));
        final Handler cuentaABorrar = new GenericHandler("1234567890");
        Assert.assertFalse(this.cliente.removeAccount(cuentaABorrar));
    }

    /**
     * Test of existsAccount method, of class Client.
     */
    @Test
    public void testExistsAccount() {
        Assert.assertFalse(this.cliente.existsAccount(this.accountHandler));
        this.cliente.add(this.cuenta);
        Assert.assertTrue(this.cliente.existsAccount(this.accountHandler));
    }

    /**
     * Test of getId method, of class Client.
     */
    @Test
    public void testGetId() {
        Assert.assertNotNull(this.cliente.getId());
        Assert.assertEquals(this.cliente.getId().toString(), "Peter Griffin");
    }

}
