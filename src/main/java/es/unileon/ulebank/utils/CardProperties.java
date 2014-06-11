package es.unileon.ulebank.utils;


/**
 * @author Israel
 * Clase que contiene las propiedades de la tarjeta extraidos del fichero de propiedades
 */
public class CardProperties {
	/**
	 * Limite minimo de compra o extraccion de la tarjeta
	 */
	private static double minimumLimit;
	/**
	 * Agnos de caducidad de la tarjeta
	 */
	private static int expirationYear;
	/**
	 * Tamagno del codigo CVV de la tarjeta
	 */
	private static int cvvSize;
	/**
	 * Tamagno del codigo PIN de la tarjeta
	 */
	private static int pinSize;
	/**
	 * Limite de compra diario de la tarjeta
	 */
	private static double buyLimitDiary;
	/**
	 * Limite de compra mensual de la tarjeta
	 */
	private static double buyLimitMonthly;
	/**
	 * Limite de extraccion en cajero diario de la tarjeta
	 */
	private static double cashLimitDiary;
	/**
	 * Limite de extraccion en cajero mensual de la tarjeta
	 */
	private static double cashLimitMonthly;
	/**
	 * Filas para la tarjeta de seguridad
	 */
	private static int dimensionRow;
	/**
	 * Columnas para la tarjeta de seguridad
	 */
	private static int dimensionColumns;
	
	/**
	 * Devuelve el limite minimo por defecto
	 * @return
	 */
	public static double getMinimumLimit() {
		return minimumLimit;
	}

	/**
	 * Cambia el limite minimo de la tarjeta
	 * @param minimumLimit
	 */
	public void setMinimumLimit(double minimumLimit) {
		CardProperties.minimumLimit = minimumLimit;
	}
	
	/**
	 * Devuelve los agnos de caducidad de la tarjeta
	 * @return
	 */
	public static int getExpirationYear() {
		return expirationYear;
	}

	/**
	 * Cambia los agnos de caducidad de la tarjeta por los que se indican
	 * @param expirationYear
	 */
	public void setExpirationYear(int expirationYear) {
		CardProperties.expirationYear = expirationYear;
	}

	/**
	 * Devuelve el tamagno del codigo CVV de la tarjeta
	 * @return
	 */
	public static int getCvvSize() {
		return cvvSize;
	}

	/**
	 * Cambia el tamagno del codigo CVV por el indicado
	 * @param cvvSize
	 */
	public void setCvvSize(int cvvSize) {
		CardProperties.cvvSize = cvvSize;
	}

	/**
	 * Devuelve el tamagno del PIN de la tarjeta
	 * @return
	 */
	public static int getPinSize() {
		return pinSize;
	}

	/**
	 * Cambia el tamagno del codigo PIN por el recibido
	 * @param pinSize
	 */
	public void setPinSize(int pinSize) {
		CardProperties.pinSize = pinSize;
	}

	/**
	 * Devuelve el limite de compra diario de la tarjeta
	 * @return
	 */
	public static double getBuyLimitDiary() {
		return buyLimitDiary;
	}

	/**
	 * Cambia el limite de compra diario de la tarjeta por el indicado
	 * @param buyLimitDiary
	 */
	public void setBuyLimitDiary(double buyLimitDiary) {
		CardProperties.buyLimitDiary = buyLimitDiary;
	}

	/**
	 * Devuelve el limite de compra mensual de la tarjeta
	 * @return
	 */
	public static double getBuyLimitMonthly() {
		return buyLimitMonthly;
	}

	/**
	 * Cambia el limite de compra mensual por el recibido
	 * @param buyLimitMonthly
	 */
	public void setBuyLimitMonthly(double buyLimitMonthly) {
		CardProperties.buyLimitMonthly = buyLimitMonthly;
	}

	/**
	 * Devuelve el limite de extraccion en cajero diario de la tarjeta
	 * @return
	 */
	public static double getCashLimitDiary() {
		return cashLimitDiary;
	}

	/**
	 * Cambia el limite de extraccion en cajero por el indicado
	 * @param cashLimitDiary
	 */
	public void setCashLimitDiary(double cashLimitDiary) {
		CardProperties.cashLimitDiary = cashLimitDiary;
	}

	/**
	 * Devuelve el limite de extraccion en cajero mensual de la tarjeta
	 * @return
	 */
	public static double getCashLimitMonthly() {
		return cashLimitMonthly;
	}

	/**
	 * Cambia el limite de extraccion en cajero mensual por el que se indica
	 * @param cashLimitMonthly
	 */
	public void setCashLimitMonthly(double cashLimitMonthly) {
		CardProperties.cashLimitMonthly = cashLimitMonthly;
	}
	
	/**
	 * Devuelve la cantidad de filas de la tarjeta de seguridad
	 * @return
	 */
	public static int getDimensionRow() {
		return dimensionRow;
	}
	
	/**
	 * Cambia la cantidad de filas de la tarjeta de seguridad por las indicadas
	 * @param dimensionRow
	 */
	public void setDimensionRow(int dimensionRow) {
		CardProperties.dimensionRow = dimensionRow;
	}
	
	/**
	 * Deuelve la cantidad de columnas de la tarjeta de seguridad
	 * @return
	 */
	public static int getDimensionColumns() {
		return dimensionColumns;
	}

	/**
	 * Cambia la cantidad de columnas de la tarjeta de seguridad por las que se indican
	 * @param dimensionColumns
	 */
	public void setDimensionColumns(int dimensionColumns) {
		CardProperties.dimensionColumns = dimensionColumns;
	}
	
}