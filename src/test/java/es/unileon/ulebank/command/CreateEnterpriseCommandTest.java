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

public class CreateEnterpriseCommandTest {

    private CreateEnterpriseCommand createEnterpriseCommand;
    private Office office;
    private Bank bank;
    private Address address;

    @Before
    public void setUp() throws MalformedHandlerException, WrongArgsException {

        this.bank = new Bank(new GenericHandler("1234"));
        this.office = new Office(new GenericHandler("1234"), this.bank);
        this.address = new Address("Street", 4, 2, 'A', "Locality", "Province",
                24000);

        this.createEnterpriseCommand = new CreateEnterpriseCommand(this.office,
                "enterpriseName", this.address, 6582297, 'Q', 'E', new Date(),
                new GenericHandler("2345"));
    }

    @Test
    public void testExecute() {

        final Handler clientId = new GenericHandler("Q6582297E");
        this.createEnterpriseCommand.execute();
        Assert.assertEquals(0, this.office.getClients().get(0).getId()
                .compareTo(clientId), 0);
    }

    @Test
    public void testGetId() {

        final Handler commandId = new GenericHandler("2345");
        Assert.assertEquals(0,
                this.createEnterpriseCommand.getID().compareTo(commandId), 0);
    }

    @Test
    public void testUndo() {

        final Handler clientId = new GenericHandler("Q6582297E");
        this.createEnterpriseCommand.execute();
        Assert.assertEquals(0, this.office.getClients().get(0).getId()
                .compareTo(clientId), 0);
        this.createEnterpriseCommand.undo();
        Assert.assertEquals(0, this.office.getClients().size(), 0);
    }

    @Test
    public void testNullClientUndo() {

        this.createEnterpriseCommand.undo();
    }

    @Test
    public void testRedo() {

        final Handler clientId = new GenericHandler("Q6582297E");
        this.createEnterpriseCommand.execute();
        Assert.assertEquals(0, this.office.getClients().get(0).getId()
                .compareTo(clientId), 0);
        this.createEnterpriseCommand.undo();
        Assert.assertEquals(0, this.office.getClients().size(), 0);
        this.createEnterpriseCommand.redo();
        Assert.assertEquals(0, this.office.getClients().get(0).getId()
                .compareTo(clientId), 0);
    }

    @Test
    public void testWrongStateUndo() {

        this.createEnterpriseCommand.redo();
        Assert.assertEquals(0, this.office.getClients().size(), 0);
    }
}
