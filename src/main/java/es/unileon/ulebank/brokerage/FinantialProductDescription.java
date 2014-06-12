package es.unileon.ulebank.brokerage;

import java.util.ArrayList;

import es.unileon.ulebank.brokerage.TypeFinancialProducts;
public class FinantialProductDescription {
	
	private TypeFinancialProducts productDescription;
	//pregustas necesarias para acceder a este producto
	
	private ArrayList<Integer > requirements = new ArrayList<Integer>();
	
	public FinantialProductDescription(TypeFinancialProducts financialProduct) {
		this.productDescription = financialProduct;
		
		
		if(TypeFinancialProducts.Funds.equals(financialProduct)) {
			this.requirements.add(3);
			this.requirements.add(4);
		}
		
		if(TypeFinancialProducts.Share.equals(financialProduct)){
			this.requirements.add(1);
			this.requirements.add(2);
			this.requirements.add(4);
		}
	}
	
	
	public boolean canOffer(ArrayList<Integer > requirements) {
		return this.requirements.containsAll(requirements);
	}
	
	
	private void addRequirement(int requirements){
		this.requirements.add(requirements);
	}
	
	@Override
	public String toString() {
		return this.productDescription.toString();
	}
}
