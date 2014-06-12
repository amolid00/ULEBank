package es.unileon.ulebank.brokerage.pack;

import es.unileon.ulebank.brokerage.buyable.Buyable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Iterates over the packs matching a given Buyable.
 *
 * @author roobre
 */
public class IteratorByBuyable implements Iterator {
    
    private final Iterator<Pack> it;
    private final Buyable product;
    private Buyable next;
    
    public IteratorByBuyable(Collection<Pack> col, Buyable b) {
        this.it = col.iterator();
        this.product = b;
        this.calcNext();
    }
    
    private void calcNext() {
        boolean foundNext = false;
        while (this.it.hasNext() && !foundNext) {
            this.next = this.it.next().getProduct();
            if (this.next.equals(this.product)) {
                foundNext = true;
            } else {
                this.next = null;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public Buyable next() {
        Buyable n = this.next;
        this.calcNext();
        return n;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
