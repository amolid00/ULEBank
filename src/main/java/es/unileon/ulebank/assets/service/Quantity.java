package es.unileon.ulebank.assets.service;

import javax.validation.constraints.Min;

public class Quantity {

	@Min(0)
	private double quantity;

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
}
