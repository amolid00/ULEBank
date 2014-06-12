package es.unileon.ulebank.handler;

import java.util.regex.Pattern;

public class FundsHandler implements Handler {

	private String fundName;
	private String fundManager;
	private String fundType;
	private int fundShare;
	private String stockMarket;

	public FundsHandler(String fundName, String fundManager, String fundType,
			int fundShare, String stockMarket) throws MalformedHandlerException {
		StringBuilder errors = new StringBuilder();

		Pattern patternTicker = Pattern.compile("[A-Z]");
		// Matcher matcherTicker = patternTicker.matcher(ticker);

		if (!(fundManager.length() >= 10 && fundManager.length() <= 30)) {
			errors.append("The fundManager must be at least 10 characters.\n");
		}

		if (!(fundType.length() >= 10 && fundType.length() <= 20)) {
			errors.append("The fund type must be at least 10 characters.\n");
		}

		if (!(fundName.length() >= 10 && fundName.length() <= 30)) {
			errors.append("Ticker is malformed.\n");
		}

		if (!(fundShare >= 1000000) || (fundShare <= 100000000)) {
			errors.append("FundShares must be between 1000000 and 100000000 shares.\n");

		}

		if (!(stockMarket.length() >= 10 && stockMarket.length() <= 20)) {
			errors.append("The name of the Stock Market must be between 10 and 20 characters.\n");
		}

		// if (fundName || fundType || fundManager || ){

		// }

		if (errors.length() > 0) {
			throw new MalformedHandlerException(errors.toString());
		}

		this.fundName = fundName;
		this.fundManager = fundManager;
		this.fundType = fundType;
		this.fundShare = fundShare;
		this.stockMarket = stockMarket;
	}

	public String toString() {
		return this.fundName + " by " + this.fundManager + " " + this.fundType
				+ " is floated on" + this.stockMarket + " with "
				+ this.fundShare + " shares.\n";
	}

	public int compareTo(Handler other) {
		return this.toString().compareTo(other.toString());
	}

}
