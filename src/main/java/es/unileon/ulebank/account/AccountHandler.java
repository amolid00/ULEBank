/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.unileon.ulebank.handler.GenericHandler;
import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;

/**
 *
 * @author runix
 */
public class AccountHandler implements Handler {
    /**
     * the number of digits ( account number )
     */
    private static final int ACCOUNT_NUMBER_LENGTH = 10;
    /**
     * the number of digits ( office number )
     */
    private static final int OFFICE_NUMBER_LENGTH = 4;
    /**
     * the number of digits ( bank number )
     */
    private static final int BANK_NUMBER_LENGTH = 4;
    /**
     * number of fields
     */
    private static final int NUMBER_OF_FIELDS = 4;
    /**
     * The number of digits ( control digits)
     */
    private static final int DC_NUMBER_LENGTH = 2;
    /**
     * Number pattern
     */
    private static Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$");
    /**
     * Separator of the fields
     */
    public static final String SEPARATOR = "-";
    /**
     * The account number, the number of digits of this number is given by {
     *
     * @see ACCOUNT_NUMBER_LENGHT
     */
    private final String accountNumber;

    /**
     * The office identifier
     */
    private final Handler officeHandler;
    /**
     * The bank identifier
     */
    private final Handler bankHandler;
    /**
     * The control digits
     */
    private final String dc;

    /**
     * Create a new AccountHandler
     *
     * @param office
     *            ( the office id )
     * @param bank
     *            ( the bank id )
     * @param accountNumber
     *            ( the account number )
     * @author runix
     * @throws MalformedHandlerException
     *             ( If the account number, office handler, or bank handler
     *             aren't correct )
     */
    public AccountHandler(Handler office, Handler bank, String accountNumber)
            throws MalformedHandlerException {
        final StringBuilder errors = new StringBuilder();
        Matcher matcher;

        if (accountNumber == null) {
            errors.append("Error, account number is null\n");
        } else {
            matcher = AccountHandler.NUMBER_PATTERN.matcher(accountNumber);
            if (!matcher.find()) {
                errors.append("The accountNumber can only have numbers\n");
            }

            if (accountNumber.length() != AccountHandler.ACCOUNT_NUMBER_LENGTH) {
                errors.append("The accountNumber length must be "
                        + AccountHandler.ACCOUNT_NUMBER_LENGTH + "\n");
            }
        }

        if ((office == null) || (office.toString() == null)) {
            errors.append("Error, office is null\n");
        } else {
            matcher = AccountHandler.NUMBER_PATTERN.matcher(office.toString());
            if (!matcher.find()) {
                errors.append("The office id can only have numbers\n");
            }
            if (office.toString().length() != AccountHandler.OFFICE_NUMBER_LENGTH) {
                errors.append("The office id length must be "
                        + AccountHandler.OFFICE_NUMBER_LENGTH + " \n");
            }
        }

        if ((bank == null) || (bank.toString() == null)) {
            errors.append("Error, bank is null\n");
        } else {
            matcher = AccountHandler.NUMBER_PATTERN.matcher(bank.toString());
            if (!matcher.find()) {
                errors.append("The bank id can only have numbers\n");
            }

            if (bank.toString().length() != AccountHandler.BANK_NUMBER_LENGTH) {
                errors.append("The bank id length must be "
                        + AccountHandler.BANK_NUMBER_LENGTH + " \n");
            }
        }

        if (errors.length() > 1) {
            throw new MalformedHandlerException(errors.toString());
        }
        this.officeHandler = office;
        this.bankHandler = bank;
        this.accountNumber = accountNumber;
        this.dc = AccountHandler.calculateDC(office.toString(),
                bank.toString(), accountNumber + "");
    }

    /**
     *
     * @param another
     * @throws MalformedHandlerException
     */
    public AccountHandler(Handler another) throws MalformedHandlerException {
        this(AccountHandler.getField(another, 1), AccountHandler.getField(
                another, 0), AccountHandler.getField(another, 3).toString());
        final StringBuilder error = new StringBuilder();
        if (!AccountHandler.getField(another, 2).toString().equals(this.dc)) {
            error.append("Wrong control digits");
        }
        if (error.length() > 0) {
            throw new MalformedHandlerException(error.toString());
        }
    }

    /**
     *
     * @return
     */
    public Handler getBankHandler() {
        return this.bankHandler;
    }

    /**
     *
     * @return
     */
    public Handler getOfficeHandler() {
        return this.officeHandler;
    }

    /**
     *
     * @return
     */
    public String getNumber() {
        return this.accountNumber;
    }

    /**
     *
     * @return
     */
    public String getDC() {
        return this.dc;
    }

    @Override
    public int compareTo(Handler another) {
        return this.toString().compareTo(another.toString());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.bankHandler.toString() + AccountHandler.SEPARATOR
                + this.officeHandler.toString() + AccountHandler.SEPARATOR
                + this.dc + AccountHandler.SEPARATOR + this.accountNumber;
    }

    private static Handler getField(Handler another, int number)
            throws MalformedHandlerException {
        String[] splitHandler = null;
        final StringBuilder error = new StringBuilder();

        if ((another == null) || (another.toString() == null)) {
            error.append("The handler is null \n");
        } else {
            splitHandler = another.toString().split(AccountHandler.SEPARATOR);
            if (splitHandler.length != AccountHandler.NUMBER_OF_FIELDS) {
                error.append("The handler must have "
                        + AccountHandler.NUMBER_OF_FIELDS
                        + " fields splitted by " + AccountHandler.SEPARATOR
                        + "\n");
            } else {
                final String raw = another.toString().replace(
                        AccountHandler.SEPARATOR, "");
                if (!(AccountHandler.NUMBER_PATTERN.matcher(raw).find() && (raw
                        .length() == (AccountHandler.ACCOUNT_NUMBER_LENGTH
                        + AccountHandler.BANK_NUMBER_LENGTH
                        + AccountHandler.OFFICE_NUMBER_LENGTH + AccountHandler.DC_NUMBER_LENGTH)))) {
                    error.append("There are letters in the handler, only numbers are allowed\n");
                }
            }
        }

        if (error.length() > 0) {
            throw new MalformedHandlerException(error.toString());
        }
        return new GenericHandler(splitHandler[number]);
    }

    /**
     * Calculate control digits of the account
     *
     * @param office
     *            ( office number in String format )
     * @param bank
     *            ( bank number in String format )
     * @param accountNumber
     *            ( accountNumber in String format)
     * @return ( control digits of the account )
     * @author runix
     */
    private static String calculateDC(String office, String bank,
            String accountNumber) {
        return String.valueOf(AccountHandler.calculateDigit("00"
                + bank.toString() + office.toString()))
                + String.valueOf(AccountHandler.calculateDigit(accountNumber
                        + ""));
    }

    /**
     * Calculate the control digit. The length of the string must be 10
     *
     * @param number
     *            ( The string with the numbers )
     *
     * @return ( the control digits )
     * @author runix
     */
    private static int calculateDigit(String number) {
        final int[] weights = new int[] { 1, 2, 4, 8, 5, 10, 9, 7, 3, 6 };
        final int length = number.length();
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += Integer.valueOf(number.charAt(i) + "") * weights[i];
        }
        int digit = 11 - (sum % 11);
        if (digit == 11) {
            digit = 0;
        } else if (digit == 10) {
            digit = 1;
        }
        return digit;
    }

}
