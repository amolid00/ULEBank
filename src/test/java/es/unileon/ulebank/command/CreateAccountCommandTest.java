/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.unileon.ulebank.command;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.account.AccountHandler;
import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.Person;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

/**
 *
 * @author Tania Pérez
 */
public class CreateAccountCommandTest {

    private Office office;
    private Bank bank;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {
        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
    }

    /**
     * Test of execute method, of class CreateAccountCommand.
     *
     * @throws es.unileon.ulebank.handler.MalformedHandlerException
     */
    @Test
    public void testExecute() throws MalformedHandlerException {
        final Client titular = new Person(71525252, 'J');
        final CreateAccountCommand command = new CreateAccountCommand(
                this.office, this.bank, new Date(System.currentTimeMillis()),
                new GenericHandler(""), titular);
        command.execute();
        Assert.assertEquals(((AccountHandler) this.office.getAccounts().get(0)
                .getID()).getNumber(), "0000000000", "0000000000");
    }

    @Test
    public void testUndo() throws MalformedHandlerException {
        final Client titular = new Person(71525252, 'J');
        final CreateAccountCommand command = new CreateAccountCommand(
                this.office, this.bank, new Date(System.currentTimeMillis()),
                new GenericHandler(""), titular);
        command.undo();
        command.execute();
        Assert.assertEquals(this.office.getAccounts().size(), 1);
        command.undo();
        Assert.assertEquals(this.office.getAccounts().size(), 0);
        command.redo();
        Assert.assertEquals(this.office.getAccounts().size(), 1);
        command.undo();
        Assert.assertEquals(this.office.getAccounts().size(), 0);
    }

    @Test
    public void testRedo() throws MalformedHandlerException {
        final Client titular = new Person(71525252, 'J');
        final CreateAccountCommand command = new CreateAccountCommand(
                this.office, this.bank, new Date(System.currentTimeMillis()),
                new GenericHandler(""), titular);
        command.undo();
        command.execute();
        Assert.assertEquals(this.office.getAccounts().size(), 1);
        command.undo();
        Assert.assertEquals(this.office.getAccounts().size(), 0);
        command.redo();
        Assert.assertEquals(this.office.getAccounts().size(), 1);
    }

    @Test
    public void testGetId() throws MalformedHandlerException {
        final Client titular = new Person(71525252, 'J');
        final CreateAccountCommand command = new CreateAccountCommand(
                this.office, this.bank, new Date(System.currentTimeMillis()),
                new GenericHandler(""), titular);
        Assert.assertEquals(command.getID().compareTo(new GenericHandler("")),
                0);
    }
}
