package es.unileon.ulebank.brokerage.buyable;

public class InvalidNumberOfParticipationsException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidNumberOfParticipationsException(double price, double ppp) {
		super(price + " is not divisible by " + ppp);
	}
}
