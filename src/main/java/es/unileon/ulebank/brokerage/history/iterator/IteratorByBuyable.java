/*
 * File created by Roberto Santalla "Roobre" for ULEBank.
 * I'm not responsible of any harm dealt by the following code, 
 * including unhandled exceptions, segmentation faults, computer explosions
 * or nuclear wars.
 */
package es.unileon.ulebank.brokerage.history.iterator;

import es.unileon.ulebank.brokerage.buyable.Buyable;
import es.unileon.ulebank.brokerage.history.PackTransaction;
import es.unileon.ulebank.history.History;
import java.util.Iterator;

/**
 *
 * @author roobre
 */
public class IteratorByBuyable implements Iterator {

    private final Iterator iterator;
    private final Buyable lookingFor;
    private Buyable next;

    public IteratorByBuyable(History<PackTransaction> h, Buyable b) {
        this.iterator = h.getIterator();
        this.lookingFor = b;
        this.calcNext();
    }
    
    private void calcNext() {
        boolean found = false;
        Buyable b;
        
        while (this.iterator.hasNext() && !found) {
            b = ((PackTransaction) this.iterator.next()).getPack().getProduct();
            
            if (b.equals(this.lookingFor)) {
                this.next = b;
                found = true;
            }
        }
        
        if (!found) {
            this.next = null;
        }
    }

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public Buyable next() {
        Buyable b = this.next;
        this.calcNext();
        return b;
    }

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
