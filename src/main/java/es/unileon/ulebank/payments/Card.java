package es.unileon.ulebank.payments;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.unileon.ulebank.account.Account;
import es.unileon.ulebank.client.Client;
import es.unileon.ulebank.exceptions.TransactionException;
import es.unileon.ulebank.fees.FeeStrategy;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.payments.exceptions.IncorrectLengthException;
import es.unileon.ulebank.payments.exceptions.IncorrectLimitException;
import es.unileon.ulebank.payments.exceptions.PaymentException;
import es.unileon.ulebank.utils.CardProperties;

/**
 * @author Israel
 */
public abstract class Card implements Serializable {
    /**
     * Version
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador de la tarjeta
     */
    private Handler id;
    /**
     * Codigo PIN de la tarjeta
     */
    private String pin;
    /**
     * Limite de compra diario de la tarjeta
     */
    private double buyLimitDiary;
    /**
     * Limite de compra mensual de la tarjeta
     */
    private double buyLimitMonthly;
    /**
     * Limite de extraccion en cajero diario de la tarjeta
     */
    private double cashLimitDiary;
    /**
     * Limite de extraccion en cajero mensual de la tarjeta
     */
    private double cashLimitMonthly;
    /**
     * Fecha de emision de la tarjeta
     */
    private String emissionDate;
    /**
     * Fecha de caducidad de la tarjeta
     */
    private String expirationDate;
    /**
     * Tipo de tarjeta
     */
    private final String cardType;
    /**
     * Codigo CVV de la tarjeta
     */
    private String cvv;
    /**
     * Comision de emision de la tarjeta
     */
    private FeeStrategy commissionEmission;
    /**
     * Comision de mantenimiento de la tarjeta
     */
    private FeeStrategy commissionMaintenance;
    /**
     * Comision de renovacion de la tarjeta
     */
    private FeeStrategy commissionRenovate;
    /**
     * Cuenta a la que esta asociada la tarjeta
     */
    private Account account;
    /**
     * Duegno de la tarjeta
     */
    private Client owner;

    /**
     * Historia de las transacciones realizadas con la tarjeta
     */
    // private History<Transaction> transactionHistory;

    /**
     * Constructor de la clase que crea una nueva tarjeta
     * 
     * @param properties
     * @param cardId
     * @param type
     * @param buyLimitDiary
     * @param buyLimitMonthly
     * @param cashLimitDiary
     * @param cashLimitMonthly
     * @param commissionEmission
     * @param commissionMaintenance
     * @param commissionRenovate
     */
    public Card(Handler cardId, String type, Account account, Client client) {
        this.id = cardId;
        this.cardType = type;
        this.account = account;
        this.owner = client;
        this.pin = this.generatePinCode();
        this.emissionDate = this.generateEmissionDate();
        this.expirationDate = this.generateExpirationDate();
        this.cvv = this.generateCVV();
    }

    /**
     * Constructor de tarjeta
     * 
     * @param type
     */
    public Card(String type) {
        this.cardType = type;
    }

    /**
     * Genera el codigo pin de la tarjeta
     * 
     * @return String
     */
    public String generatePinCode() {
        final StringBuilder result = new StringBuilder();
        // Generamos tantos numeros aleatorios como indique la constante
        // PIN_SIZE para formar el codigo PIN
        for (int i = 0; i < CardProperties.getPinSize(); i++) {
            result.append((int) (Math.random() * 10));
        }

        return result.toString();
    }

    /**
     * Genera la fecha de emision de la tarjeta
     * 
     * @return String
     */
    public String generateEmissionDate() {
        // Generamos la fecha dandole el formato estandar dd/MM/yyyy
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormat.format(new Date());
    }

    /**
     * Genera una fecha de caducidad para la tarjeta
     * 
     * @return String
     */
    public String generateExpirationDate() {
        // Obtenemos una instancia del calendario
        final Calendar calendar = Calendar.getInstance();

        // Obtenemos el mes actual que sera devuelto en forma de int comenzando
        // en 0 (Enero) por tanto debemos sumarle 1
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        // Si el mes esta entre enero y septiembre agnadimos un 0 delante al
        // String para que tenga 2 cifras
        if (month.length() == 1) {
            month = "0" + month;
        }

        // Obtenemos el agno actual y cortamos el substring para coger
        // unicamente las dos ultimas cifras
        final String year = Integer.toString(
                CardProperties.getExpirationYear()
                        + calendar.get(Calendar.YEAR)).substring(2);
        // Devolvemos el String con el formato MM/yy
        return month + "/" + year;
    }

    /**
     * Genera el codigo de validacion CVV
     * 
     * @return String
     */
    public String generateCVV() {
        final StringBuilder result = new StringBuilder();
        // Generamos tantos numeros aleatorios como indique la constante
        // CVV_SIZE para formar el codigo CVV
        for (int i = 0; i < CardProperties.getCvvSize(); i++) {
            result.append((int) (Math.random() * 10));
        }

        return result.toString();
    }

    /**
     * Devuelve el codigo PIN de la tarjeta
     * 
     * @return String
     */
    public String getPin() {
        return this.pin;
    }

    /**
     * Cambia el codigo PIN de la tarjeta por el que recibe
     * 
     * @param pin
     * @throws IOException
     */
    public void setPin(String pin) throws PaymentException {
        // Comprobamos que el String recibido contiene solo numeros
        if (this.checkStringNumber(pin)) {
            // Si el pin tiene el tamagno adecuado lo cambiamos
            if (pin.length() == CardProperties.getPinSize()) {
                this.pin = pin;
                // Sino lanzamos una excepcion
            } else {
                throw new IncorrectLengthException("Incorrect length");
            }
            // Sino lanzamos una excepcion ya que solo puede haber numeros
        } else {
            throw new NumberFormatException("The pin must only contain numbers");
        }
    }

    /**
     * Comprueba que el pin sea correcto
     * 
     * @param pin
     * @return boolean
     */
    public boolean checkPin(String pin) {

        return pin.equals(this.pin);
    }

    /**
     * Devuelve el limite de la tarjeta diario para compras
     * 
     * @return double
     */
    public double getBuyLimitDiary() {
        return this.buyLimitDiary;
    }

    /**
     * Cambia el linmite de la tarjeta diario para compras
     * 
     * @param newAmount
     * @throws IncorrectLimitException
     */
    public void setBuyLimitDiary(double newAmount)
            throws IncorrectLimitException {
        // Si el limite mensual es mayor que el limite diario a cambiar y este
        // ultimo es mayor
        // o igual que el limite minimo, cambiamos el limite
        if ((this.buyLimitMonthly > newAmount)
                && (newAmount >= CardProperties.getMinimumLimit())) {
            this.buyLimitDiary = newAmount;
            // en caso contrario lanzamos una excepcion
        } else {
            throw new IncorrectLimitException(
                    "The limit is bigger than current monthly limit or is too small.");
        }
    }

    /**
     * Comprueba que el precio no exceda el limite de la tarjeta
     * 
     * @param price
     * @return boolean
     */
    public boolean checkBuyLimitDiary(double price) {
        // Si el precio es mayor que el limite de compra diario devuelve false
        return !(price > this.buyLimitDiary);
    }

    /**
     * Devuelve el limite de la tarjeta mensual para compras
     * 
     * @return double
     */
    public double getCashLimitMonthly() {
        return this.cashLimitMonthly;
    }

    /**
     * Devuelve el limite de compra mensual
     * 
     * @return double
     */
    public double getBuyLimitMonthly() {
        return this.buyLimitMonthly;
    }

    /**
     * Cambia el linmite de la tarjeta mensual para compras
     * 
     * @param buyLimit
     * @throws IncorrectLimitException
     */
    public void setBuyLimitMonthly(double newAmount)
            throws IncorrectLimitException {
        // Si el limite recibido es mayor o igual que el limite diario lo
        // cambiamos
        if (newAmount >= this.buyLimitDiary) {
            this.buyLimitMonthly = newAmount;
            // sino se lanza una excepcion
        } else {
            throw new IncorrectLimitException(
                    "Monthly limit must be greater than diary limit");
        }
    }

    /**
     * Devuelve el limite de la tarjeta para extracciones en cajeros
     * 
     * @return double
     */
    public double getCashLimitDiary() {
        return this.cashLimitDiary;
    }

    /**
     * Cambia el limite de la tarjeta para extracciones en cajeros
     * 
     * @throws IncorrectLimitException
     */
    public void setCashLimitDiary(double newAmount)
            throws IncorrectLimitException {
        // Si el limite mensual es mayor que el limite diario a cambiar y este
        // ultimo es mayor
        // o igual que el limite minimo, cambiamos el limite
        if ((this.cashLimitMonthly >= newAmount)
                && (newAmount >= CardProperties.getMinimumLimit())) {
            this.cashLimitDiary = newAmount;
            // sino lanzamos una excepcion
        } else {
            throw new IncorrectLimitException(
                    "The limit is bigger than current monthly limit or is too small.");
        }
    }

    /**
     * Comprueba que la cantidad solicitada para extraer en cajero no exceda el
     * limite de la tarjeta
     * 
     * @param cash
     * @return boolean
     */
    public boolean checkCashLimitDiary(double cash) {
        // Si la cantidad solicitada para extraer es mayor que la cantidad
        // maxima diaria devuelve false
        return !(cash > this.cashLimitDiary);
    }

    /**
     * Cambia la cantidad maxima para extraer en un cajero al mes
     * 
     * @param newAmount
     * @throws IncorrectLimitException
     */
    public void setCashLimitMonthly(double newAmount)
            throws IncorrectLimitException {
        // Si el limite recibido es mayor o igual que el limite diario lo
        // cambiamos
        if (newAmount >= this.cashLimitDiary) {
            this.cashLimitMonthly = newAmount;
            // en caso contrario se lanza una excepcion
        } else {
            throw new IncorrectLimitException(
                    "Monthly limit must be greater than diary limit");
        }
    }

    /**
     * Devuelve la fecha de emision de la tarjeta
     * 
     * @return String
     */
    public String getEmissionDate() {
        return this.emissionDate;
    }

    /**
     * Devuelve la fecha de caducidad de la tarjeta
     * 
     * @return String
     */
    public String getExpirationDate() {
        return this.expirationDate;
    }

    /**
     * Cambia la fecha de caducidad por una nueva
     * 
     * @param expirationDate
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Devuelve el tipo de tarjeta
     * 
     * @return CardType
     */
    public String getCardType() {
        return this.cardType.toString();
    }

    /**
     * Devuelve el codigo de validacion CVV
     * 
     * @return String
     */
    public String getCvv() {
        return this.cvv;
    }

    /**
     * Cambia el CVV por el nuevo que ha recibido
     * 
     * @param cvv
     * @throws IOException
     */
    public void setCvv(String cvv) throws PaymentException {
        // Comprueba que el String contenga solo numeros
        if (this.checkStringNumber(cvv)) {
            // Si el tamagno del cvv es correcto se cambia
            if (cvv.length() == CardProperties.getCvvSize()) {
                this.cvv = cvv;
                // sino se lanza una excepcion
            } else {
                throw new IncorrectLengthException("Incorrect length");
            }
            // sino se lanza una excepcion
        } else {
            throw new NumberFormatException("The cvv must only contains numbers");
        }
    }

    /**
     * Devuelve el numero de la tarjeta
     * 
     * @return String
     */
    public Handler getId() {
        return this.id;
    }

    /**
     * Devuelve la comision de emision de la tarjeta
     * 
     * @return float
     */
    public double getCommissionEmission(double value) {
        return this.commissionEmission.getFee(value);
    }

    /**
     * Cambia la comision de emision por la que recibe
     * 
     * @param commissionEmission
     */
    public void setCommissionEmission(FeeStrategy commissionEmission) {
        this.commissionEmission = commissionEmission;
    }

    /**
     * Devuelve la comisionde mantenimiento de la tarjeta
     * 
     * @return float
     */
    public double getCommissionMaintenance(double value) {
        return this.commissionMaintenance.getFee(value);
    }

    /**
     * Cambia la comision de mantenimiento por la que se indica
     * 
     * @param commissionMaintenance
     */
    public void setCommissionMaintenance(FeeStrategy commissionMaintenance) {
        this.commissionMaintenance = commissionMaintenance;
    }

    /**
     * Devuelve la comision de renovacion de la tarjeta
     * 
     * @return float
     */
    public double getCommissionRenovate(double value) {
        return this.commissionRenovate.getFee(value);
    }

    /**
     * Cambia la comision de renovacion por la que se recibe
     * 
     * @param commissionRenovate
     */
    public void setCommissionRenovate(FeeStrategy commissionRenovate) {
        this.commissionRenovate = commissionRenovate;
    }

    /**
     * Comprueba que el String recibido sea solo numerico
     * 
     * @param string
     * @return boolean
     */
    private boolean checkStringNumber(String string) {
        // Crea un patron para indicar que solo debe contener numeros
        final Pattern pattern = Pattern.compile("^[0-9]*$");
        // Comprueba que el String recibido cumple el patron
        final Matcher matcher = pattern.matcher(string);

        // Si se cumple el patron devuelve true
        return matcher.find();

    }

    /**
     * Devuelve el duegno de la tarjeta
     * 
     * @return Client
     */
    public Client getOwner() {
        return this.owner;
    }

    /**
     * Method that get the account associated to the card
     * 
     * @return account
     */
    public Account getAccount() {
        return this.account;
    }

    /**
     * Method that makes the payment
     * 
     * @param quantity
     *            Amount of the payment
     * @param payConcept
     *            Concept of the payment
     * @throws PaymentException
     * @throws TransactionException
     * @throws es.unileon.ulebank.history.TransactionException
     */
    public void makeTransaction(double quantity, String payConcept)
            throws PaymentException, TransactionException {

    }
}
