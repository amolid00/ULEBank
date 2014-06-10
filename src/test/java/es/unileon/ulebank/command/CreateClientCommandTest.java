package es.unileon.ulebank.command;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.unileon.ulebank.bank.Bank;
import es.unileon.ulebank.client.Address;
import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.history.conditions.WrongArgsException;
import es.unileon.ulebank.office.Office;

public class CreateClientCommandTest {

    CreateClientCommand createClientCommand;
    Office office;
    Bank bank;
    Address address;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {

        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.address = new Address("Street", 4, 2, 'A', "Locality", "Province",
                24000);

        this.createClientCommand = new CreateClientCommand(this.office, "Name",
                "Surnames", this.address, "Civil State", 999999999, 999999999,
                "Profession", new Date(), 'X', 71525252, 'J',
                new GenericHandler("2345"));
    }

    @Test
    public void testExecute() {

        final Handler clientId = new GenericHandler("X71525252J");
        this.createClientCommand.execute();
        Assert.assertEquals(0, this.office.getClients().get(0).getId()
                .compareTo(clientId), 0);
    }

    @Test
    public void testGetId() {

        final Handler commandId = new GenericHandler("2345");
        Assert.assertEquals(0,
                this.createClientCommand.getID().compareTo(commandId), 0);
    }

    @Test
    public void testUndo() {

        final Handler clientId = new GenericHandler("X71525252J");
        this.createClientCommand.execute();
        Assert.assertEquals(0, this.office.getClients().get(0).getId()
                .compareTo(clientId), 0);
        this.createClientCommand.undo();
        Assert.assertEquals(0, this.office.getClients().size(), 0);
    }

    @Test
    public void testNullClientUndo() {

        this.createClientCommand.undo();
    }

    @Test
    public void testRedo() {

        final Handler clientId = new GenericHandler("X71525252J");
        this.createClientCommand.execute();
        Assert.assertEquals(0, this.office.getClients().get(0).getId()
                .compareTo(clientId), 0);
        this.createClientCommand.undo();
        Assert.assertEquals(0, this.office.getClients().size(), 0);
        this.createClientCommand.redo();
        Assert.assertEquals(0, this.office.getClients().get(0).getId()
                .compareTo(clientId), 0);
    }

    @Test
    public void testWrongStateUndo() {

        this.createClientCommand.redo();
        Assert.assertEquals(0, this.office.getClients().size(), 0);
    }
}
