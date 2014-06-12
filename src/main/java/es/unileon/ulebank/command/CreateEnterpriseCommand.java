/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.command;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unileon.ulebank.client.Address;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.client.ClientNotFoundException;
import es.unileon.ulebank.client.Enterprise;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.office.Office;

/**
 *
 * @author Paula
 */
public class CreateEnterpriseCommand implements Command {
    private final String enterpriseName;
    private final Address address;
    private final int cifNumber;
    private final char cifLetter;
    private final char cifControl;
    private final Office office;
    private Client client;
    private final Handler commandID;
    private int state;
    private static final int STATE_EXECUTED = 1;
    private static final int STATE_REDO = 2;
    private static final int STATE_UNDO = 4;
    private static final int STATE_NORMAL = 0;

    /**
     *
     * @param office
     * @param enterpriseName
     * @param address
     * @param cifNumber
     * @param cifLetter
     * @param effectiveDate
     * @param commandId
     */
    public CreateEnterpriseCommand(Office office, String enterpriseName,
            Address address, int cifNumber, char cifLetter, char cifControl,
            Date effectiveDate, Handler commandId) {
        this.state = CreateEnterpriseCommand.STATE_NORMAL;
        this.enterpriseName = enterpriseName;
        this.address = address;
        this.office = office;
        this.cifNumber = cifNumber;
        this.cifLetter = cifLetter;
        this.cifControl = cifControl;
        this.commandID = commandId;

    }

    /**
     *
     */
    @Override
    public void execute() {
        try {
            if (this.state == CreateEnterpriseCommand.STATE_NORMAL) {
                this.client = new Enterprise(this.cifLetter, this.cifNumber,
                        this.cifControl, this.enterpriseName, this.address);
                this.office.addClient(this.client);
                this.state = CreateEnterpriseCommand.STATE_EXECUTED;
            }
        } catch (final MalformedHandlerException ex) {
            Logger.getLogger(CreateEnterpriseCommand.class.getName()).log(
                    Level.SEVERE, null, ex);
        } catch (ClientNotFoundException e) {
            Logger.getLogger(CreateEnterpriseCommand.class.getName()).log(
                    Level.SEVERE, null, e);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Handler getID() {
        return this.commandID;
    }

    /**
     *
     */
    @Override
    public void undo() {
        if ((this.state & (CreateEnterpriseCommand.STATE_EXECUTED | CreateEnterpriseCommand.STATE_REDO)) != 0) {
            try {
                this.office.deleteClient(this.client.getId());
            } catch (ClientNotFoundException e) {
                Logger.getLogger(CreateEnterpriseCommand.class.getName()).log(
                        Level.SEVERE, null, e);
            }
            this.state = CreateEnterpriseCommand.STATE_UNDO;
        }
    }

    /**
     *
     */
    @Override
    public void redo() {
        if (this.state == CreateEnterpriseCommand.STATE_UNDO) {
            try {
                this.office.addClient(this.client);
            } catch (ClientNotFoundException e) {
                Logger.getLogger(CreateEnterpriseCommand.class.getName()).log(
                        Level.SEVERE, null, e);
            }
            this.state = CreateEnterpriseCommand.STATE_REDO;
        }
    }

}
