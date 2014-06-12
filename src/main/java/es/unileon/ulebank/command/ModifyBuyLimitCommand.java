package es.unileon.ulebank.command;

import javax.security.auth.login.AccountNotFoundException;

import org.apache.log4j.Logger;

import es.unileon.ulebank.command.exceptions.CommandException;
import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.exceptions.CardNotFoundException;
import es.unileon.ulebank.payments.exceptions.IncorrectLimitException;

/**
 * @author Israel
 */
public class ModifyBuyLimitCommand implements Command {
    /**
     * Logger de la clase
     */
    private static final Logger LOG = Logger
            .getLogger(ModifyBuyLimitCommand.class.getName());
    /**
     * String of type diary limit
     */
    private static final String DIARY = "diary";
    /**
     * String of type monthly limit
     */
    private static final String MONTHLY = "monthly";
    /**
     * Identificador del comando
     */
    private final Handler id;
    /**
     * Objeto tarjeta del que se modificaran los datos
     */
    private final Card card;
    /**
     * Cantidad nueva a modificar
     */
    private final double newAmount;
    /**
     * Cantidad antes de realizar la modificacion
     */
    private double oldAmount;
    /**
     * Tipo de limite a modificar (diario o mensual)
     */
    private final String type;
    /**
     * Variable para saber si el comando ha sido ejecutado o no
     */
    private boolean executed = false;
    /**
     * Variable para saber si el comando ha sido deshecho o no
     */
    private boolean undone = false;

    /**
     * Constructor de la clase
     * 
     * @param cardId
     * @param card
     * @param amount
     * @param type
     * @throws AccountNotFoundException
     */
    public ModifyBuyLimitCommand(Handler cardId, Card card, double amount,
            String type) {
        this.id = new CommandHandler(cardId);
        this.card = card;
        this.newAmount = amount;
        this.type = type;
    }

    /**
     * Realiza la modificacion del limite de compra ya sea diario o mensual
     * 
     * @throws CardNotFoundException
     */
    @Override
    public void execute() throws CardNotFoundException, IncorrectLimitException {
        // Buscamos la tarjeta con el identificador de la misma en la lista de
        // tarjetas de la cuenta
        try {
            // Si el limite a modificar es diario
            if (this.checkTypeLimit(ModifyBuyLimitCommand.DIARY)) {
                // Guardamos la cantidad anterior para poder deshacer la
                // operacion
                this.oldAmount = this.card.getBuyLimitDiary();
                // Cambiamos el limite por el indicado
                this.card.setBuyLimitDiary(this.newAmount);
                this.executed = true;
                // Si el limite a modificar es mensual
            } else if (this.checkTypeLimit(ModifyBuyLimitCommand.MONTHLY)) {
                // Guardamos la cantidad anterior para poder deshacer la
                // operacion
                this.oldAmount = this.card.getBuyLimitMonthly();
                // Cambiamos el limite por el indicado
                this.card.setBuyLimitMonthly(this.newAmount);
                this.executed = true;
                // Si no se indica el tipo de limite a modificar adecuadamente
                // no va a realizar la operacion
            } else {
                ModifyBuyLimitCommand.LOG.info("Limit type not defined");
            }
        } catch (final IncorrectLimitException e) {
            ModifyBuyLimitCommand.LOG.info(e.getMessage());
            throw new IncorrectLimitException(
                    "Diary limit must been lower tha monthly limit");
        }
    }

    /**
     * Deshace la modificacion del limite de compra dejandolo como estaba
     * 
     * @throws CommandException
     */
    @Override
    public void undo() throws CommandException {
        // Si el tipo es diario
        if (this.executed) {
            if (this.checkTypeLimit(ModifyBuyLimitCommand.DIARY)) {
                try {
                    // Recuperamos el limite anterior
                    this.card.setBuyLimitDiary(this.oldAmount);
                    this.undone = true;
                } catch (final IncorrectLimitException e) {
                    ModifyBuyLimitCommand.LOG.info(e.getMessage());
                }
                // Si el tipo es mensual
            } else if (this.checkTypeLimit(ModifyBuyLimitCommand.MONTHLY)) {
                try {
                    // Recuperamos el limite anterior
                    this.card.setBuyLimitMonthly(this.oldAmount);
                    this.undone = true;
                } catch (final IncorrectLimitException e) {
                    ModifyBuyLimitCommand.LOG.info(e.getMessage());
                }
                // Si no se indica el tipo de limite a modificar adecuadamente
                // no va a realizar la operacion
            } else {
                ModifyBuyLimitCommand.LOG.info("Limit type not defined");
            }
        } else {
            ModifyBuyLimitCommand.LOG
                    .info("Can't undo because command has not executed yet.");
            throw new CommandException(
                    "Can't undo because command has not executed yet.");
        }

    }

    /**
     * Rehace la modificacion del limite de compra despues de haberlo deshecho
     * 
     * @throws CommandException
     */
    @Override
    public void redo() throws CommandException {
        if (this.undone) {
            // Si el tipo es diario
            if (this.checkTypeLimit(ModifyBuyLimitCommand.DIARY)) {
                try {
                    // Volvemos a cambiar el limite por el que lo habiamos
                    // cambiado anteriormente
                    this.card.setBuyLimitDiary(this.newAmount);
                    this.undone = false;
                } catch (final IncorrectLimitException e) {
                    ModifyBuyLimitCommand.LOG.info(e.getMessage());
                }
                // Si el tipo es mensual
            } else if (this.checkTypeLimit(ModifyBuyLimitCommand.MONTHLY)) {
                try {
                    // Volvemos a cambiar el limite por el que lo habiamos
                    // cambiado anteriormente
                    this.card.setBuyLimitMonthly(this.newAmount);
                    this.undone = false;
                } catch (final IncorrectLimitException e) {
                    ModifyBuyLimitCommand.LOG.info(e.getMessage());
                }
                // Si no se indica el tipo de limite a modificar adecuadamente
                // no va a realizar la operacion
            } else {
                ModifyBuyLimitCommand.LOG.info("Limit type not defined");
            }
        } else {
            ModifyBuyLimitCommand.LOG
                    .info("Can't undo because command has not undoned yet.");
            throw new CommandException(
                    "Can't undo because command has not undoned yet.");
        }
    }

    /**
     * Devuelve el identificador del comando
     * 
     * @return command id
     */
    @Override
    public Handler getID() {
        return this.id;
    }

    private boolean checkTypeLimit(String typeLimit) {
        return typeLimit.equalsIgnoreCase(this.type);
    }
}
