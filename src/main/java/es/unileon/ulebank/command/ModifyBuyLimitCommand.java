package es.unileon.ulebank.command;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.login.AccountNotFoundException;

import es.unileon.ulebank.command.handler.CommandHandler;
import es.unileon.ulebank.exceptions.CommandException;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.Card;
import es.unileon.ulebank.payments.exceptions.IncorrectLimitException;
import es.unileon.ulebank.payments.exceptions.PaymentException;

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
     *  String of Limit type not defined
     */
    private static final String NOT_DEFINED_TYPE = "Limit type not defined";
    /**
     * String of incorrect limit error
     */
    private static final String INCORRECT_LIMIT = "Diary limit must been lower tha monthly limit";
    /**
     * String of incorrect undo error
     */
    private static final String ERROR_UNDO = "Can't undo because command has not undoned yet.";
    /**
     * String of incorrect redo error
     */
    private static final String ERROR_REDO = "Can't redo because command has not undoned yet.";
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
    public void execute() throws CommandException {
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
            	LOG.log(Level.SEVERE, NOT_DEFINED_TYPE);
            }
        } catch (IncorrectLimitException e) {
            LOG.log(Level.SEVERE, INCORRECT_LIMIT);
            throw new IncorrectLimitException(ModifyBuyLimitCommand.INCORRECT_LIMIT);
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
                } catch (IncorrectLimitException e) {
                    LOG.info(e.getMessage());
                }
                // Si el tipo es mensual
            } else if (this.checkTypeLimit(ModifyBuyLimitCommand.MONTHLY)) {
                try {
                    // Recuperamos el limite anterior
                    this.card.setBuyLimitMonthly(this.oldAmount);
                    this.undone = true;
                } catch (IncorrectLimitException e) {
                    LOG.info(e.getMessage());
                }
                // Si no se indica el tipo de limite a modificar adecuadamente
                // no va a realizar la operacion
            } else {
            	LOG.log(Level.SEVERE, NOT_DEFINED_TYPE);
            }
        } else {
        	LOG.log(Level.SEVERE, ERROR_UNDO);
            throw new PaymentException(ModifyBuyLimitCommand.ERROR_UNDO);
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
                } catch (IncorrectLimitException e) {
                    LOG.info(e.getMessage());
                }
                // Si el tipo es mensual
            } else if (this.checkTypeLimit(ModifyBuyLimitCommand.MONTHLY)) {
                try {
                    // Volvemos a cambiar el limite por el que lo habiamos
                    // cambiado anteriormente
                    this.card.setBuyLimitMonthly(this.newAmount);
                    this.undone = false;
                } catch (IncorrectLimitException e) {
                    LOG.info(e.getMessage());
                }
                // Si no se indica el tipo de limite a modificar adecuadamente
                // no va a realizar la operacion
            } else {
            	LOG.log(Level.SEVERE, NOT_DEFINED_TYPE);
            }
        } else {
        	LOG.log(Level.SEVERE, ERROR_REDO);
             throw new PaymentException(ModifyBuyLimitCommand.ERROR_REDO);
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
