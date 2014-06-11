package es.unileon.ulebank.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.unileon.ulebank.bank.BankHandler;

/**
 * @author Israel
 */
public class CardHandler implements Handler {
	/**
	 * Cantidad de digitos del identificador del banco
	 */
	private static final int BANK_ID_LENGTH = 4;
	/**
	 * Cantidad de digitos de la tarjeta
	 */
	private static final int CARD_LENGTH = 16;
	/**
	 * Tamagno del identificador de la oficina
	 */
	private static final int OFFICE_ID_LENGTH = 2;
	/**
	 * Tamagno del identificador de la tarjeta
	 */
	private static final int CARD_ID_LENGTH = CARD_LENGTH - BANK_ID_LENGTH - OFFICE_ID_LENGTH - 1;
	/**
	 * Separador de bloques
	 */
	private static final String SEPARATOR = " ";
	/**
	 * Identificador de nuestro banco
	 */
	private Handler bankId;
	/**
	 * Identificador de la tarjeta
	 */
	private String cardId;
	/**
	 * Identificador de la oficina
	 */
	private String officeId;
	/**
	 * Digito de control
	 */
	private int controlDigit;

	/**
	 * Constructor de la clase
	 * @param bankId
	 * @param officeId
	 * @param cardId
	 * @throws MalformedHandlerException
	 */
	public CardHandler(Handler bankId, String officeId, String cardId) throws MalformedHandlerException {
		StringBuilder errors = new StringBuilder();
		//Creo un patron de comprobacion para verificar que no se introducen caracteres en los numeros de la tarjeta
		Pattern pattern = Pattern.compile("^[0-9]*$");

		this.bankId = bankId;

		Matcher matcher = pattern.matcher(officeId);
		if (!matcher.find()) {
			errors.append("The office ID must only contains numbers");
		}

		if (officeId.length() != OFFICE_ID_LENGTH) {
			errors.append("The office ID must be " + OFFICE_ID_LENGTH + "\n");
		}

		matcher = pattern.matcher(cardId);
		if (!matcher.find()) {
			errors.append("The card ID must only contains numbers");
		}

		if (cardId.length() != CARD_ID_LENGTH) {
			errors.append("The card ID must be " + CARD_ID_LENGTH + "\n");
		}

		if (errors.length() != 0) {
			throw new MalformedHandlerException(errors.toString());
		}

		this.bankId = bankId;
		this.officeId = officeId;
		this.cardId = cardId;
		this.controlDigit = generateControlDigit(stringToIntArray(bankId.toString() + officeId + cardId));
	}

	/**
	 * Constructor de la clase que crea un CardHandler a partir del String recibido
	 * @param number
	 * @throws MalformedHandlerException 
	 */
	public CardHandler(String number) throws MalformedHandlerException {
		StringBuilder errors = new StringBuilder();
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher matcher = pattern.matcher(number);
		if (!matcher.find()) {
			errors.append("Card number must only contains numbers.\n");
		}

		if (number.length() >= 16) {
			errors.append("The card number must be smaller than 16\n");
		}

		if (errors.length() != 0) {
			throw new MalformedHandlerException(errors.toString());
		}
		separateNumber(number);
	}

	/**
	 * Metodo para separar los distintos componentes del numero de la tarjeta
	 * @param number
	 * @throws MalformedHandlerException 
	 */
	private void separateNumber(String number) throws MalformedHandlerException {
		this.bankId = new BankHandler(number.substring(0, 4));
		this.officeId = number.substring(4, 6);
		this.cardId = number.substring(6);
		this.controlDigit = generateControlDigit(stringToIntArray(this.bankId.toString() + this.officeId + this.cardId));
	}

	/**
	 * Convierte el String que recibe a un array de enteros
	 * @param string
	 * @return
	 */
	private int[] stringToIntArray(String string) {
		int[] result = new int[string.length()];
		String substring;

		for (int i = 0; i < string.length(); i++) {
			if (i != string.length()) {
				substring = string.substring(i, i+1);
			} else {
				substring = string.substring(i);
			}
			result[i] = Integer.parseInt(substring);
		}

		return result;
	}

	/**
	 * Genera el digito de control empleando el algoritmo de Luhn
	 * @param digits
	 * @return int
	 */
	protected int generateControlDigit(int[] digits) {
		return (10 - verifyCardNumber(digits)) % 10;
	}

	/**
	 * Verifica que el numero de tarjeta es correcto si el resultado es 0,
	 * tambien se puede emplear para generar el digito de control realizando 
	 * la operacion 10-verifyCardNumber(digits[])
	 * @param digits
	 * @return int
	 */
	protected int verifyCardNumber(int[] digits) {
		return (sumOddPlaces(digits) + sumEvenPlaces(digits)) % 10;
	}

	/**
	 * Realiza la suma de las posiciones impares de izquierda a derecha del numero de la tarjeta
	 * @param digits
	 * @return int
	 */
	private int sumOddPlaces(int[] digits) {
		int sum = 0;
		//Recorremos las posiciones impares
		for (int i = 0; i < digits.length; i+=2) {
			//Doblamos el digito y lo guardamos
			int aux = digits[i] << 1;
			//Si el numero tiene 2 digitos los sumamos juntos
			if (aux >= 10) {
				sum += 1 + aux - 10;
			} else {
				sum += aux;
			}
		}

		return sum;
	}

	/**
	 * Realiza la suma de las posiciones pares de izquierda a derecha del numero de la tarjeta
	 * @param digits
	 * @return int
	 */
	private int sumEvenPlaces(int[] digits) {
		int sum = 0;
		//Recorremos las posiciones pares y sumamos los digitos
		for (int i = 1; i < digits.length; i+=2) {
			sum += digits[i];
		}

		return sum;
	}

	/**
	 * Devuelve el identificador del banco
	 * @return Handler
	 */
	public Handler getBankHandler() {
		return bankId;
	}

	/**
	 * Devuelve el identificador de la sucursal
	 * @return String
	 */
	public String getOfficeId() {
		return officeId;
	}

	/**
	 * Devuelve el digito de control de la tarjeta
	 * @return int
	 */
	public int getControlDigit() {
		return controlDigit;
	}

	/**
	 * Devuelve el identificador de la tarjeta
	 * @return String
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * Compara el identificador actual con el que se indica
	 * @return devuelve un 0 si son iguales
	 * @return devuelve otro numero si son distintos
	 */
	public int compareTo(Handler another) {
		return this.toString().compareTo(another.toString());
	}

	/**
	 * Devuelve el identificador con el formato de la tarjeta dividido en bloques de 4
	 * @return String
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		String cardNumber = bankId.toString() + officeId + cardId + controlDigit;
		String substring;
		System.out.println(cardNumber);
		for (int i = 0; i < CARD_LENGTH; i++) {
			if (i != CARD_LENGTH) {
				substring = cardNumber.substring(i, i+1);
			} else {
				substring = cardNumber.substring(i);
			}
			result.append(substring);

			if ((i+1) % 4 == 0) {
				result.append(SEPARATOR);
			}
		}
		//trim() because cut white space after the number
		return result.toString().trim();
	}

	/**
	 * Devuelve el tamagno de la tarjeta
	 * @return int
	 */
	public int getCardLength() {
		return CARD_LENGTH;
	}

	/**
	 * Devuelve el tamagno del identificador del banco
	 * @return int
	 */
	public int getBankIdLength() {
		return BANK_ID_LENGTH;
	}

	/**
	 * Devuelve el tamagno del identificador de la oficina
	 * @return int
	 */
	public int getOfficeIdLength() {
		return OFFICE_ID_LENGTH;
	}

}
