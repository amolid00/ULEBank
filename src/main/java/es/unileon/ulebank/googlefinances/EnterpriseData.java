package es.unileon.ulebank.googlefinances;

import es.unileon.ulebank.brokerage.buyable.Enterprise;
import es.unileon.ulebank.brokerage.buyable.EnterpriseHandler;
import es.unileon.ulebank.brokerage.buyable.InvalidBuyableException;
import es.unileon.ulebank.handler.MalformedHandlerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;

/**
 * EnterpriseData allows you to retrieves information giving a specific key.
 *
 * @author furiios
 */
public class EnterpriseData<T> {

    private final ArrayList<EnterpriseDataListener> listeners;
    private Map map;

    public EnterpriseData(Map map) {
        this.listeners = new ArrayList<EnterpriseDataListener>();
        this.map = map;
    }

    /**
     * Retrieves the specific data.
     *
     * @param key The key to find.<br />
     * <br />(Integer)<br />id ()<br />
     * <br />(Double)<br />l_fix ()<br />c_fix ()<br />cp_fix ()<br />pcls_fix
     * ()<br />el_fix ()<br />ec_fix ()<br />ecp_fix ()<br />
     * <br />(String)<br />t ()<br />e ()<br />l ()<br />l_cur ()<br />s ()<br
     * />ltt ()<br />lt ()<br />c ()<br />cp ()<br />ccol ()<br />
     * el ()<br />el_cur ()<br />elt ()<br />ec ()<br />ecp ()<br />eccol ()<br
     * />div ()<br />yld ()<br />eo ()<br />delay ()<br />
     * op ()<br />hi ()<br />lo ()<br />vo ()<br />avvo ()<br />hi52 ()<br
     * />lo52 ()<br />mc ()<br />pe ()<br />fwpe ()<br />beta ()<br />
     * eps ()<br />shares ()<br />inst_own ()<br />name ()<br />type ()<br />
     * @return T (Generic Type: Integer || Double || String)
     * @throws ElementNotFoundException
     */
    public JSONValue getValue(String key) throws ElementNotFoundException {
        if (!map.containsKey(key) || map.get(key).equals("")) {
            throw new ElementNotFoundException(key);
        }
        return new JSONValue((T) map.get(key).toString());
    }
    
    public void setValue(String key, String value) throws ElementNotFoundException {
        if (!map.containsKey(key) || map.get(key).equals("")) {
            throw new ElementNotFoundException(key);
        }
        map.put(key, value);
    }

    public void refresh() throws IOException, ParseException, ElementNotFoundException {
        this.map = GoogleFinancesApi.getInstance().searchToParsedMap(getValue("t").getString());
        callListeners();
    }

    public void addListener(EnterpriseDataListener listener) {
        GoogleFinancesApi.getInstance().addEnterprise(listener.getEnterpriseData());
        listener.start();
        this.listeners.add(listener);
    }

    public void removeListener(EnterpriseDataListener listener) throws ElementNotFoundException {
        listener.pause();
        this.listeners.remove(listener);

        if (this.listeners.isEmpty()) {
            GoogleFinancesApi.getInstance().removeEnterprise(this);
        }
    }

    private void callListeners() {
        for (EnterpriseDataListener listener : this.listeners) {
            listener.exec();
        }
    }

    public Enterprise getEnterprise() {
        Enterprise e = null;
        try {
            long shares = parseShortenedNumber(getValue("shares").getString());
            e = new Enterprise(new EnterpriseHandler(getValue("t").getString()), shares, (Double) (shares * getValue("l_fix").getDouble()));
        } catch (ElementNotFoundException ex) {
            Logger.getLogger(EnterpriseData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedHandlerException e1) {
			e1.printStackTrace();
		} catch (InvalidBuyableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return e;
    }

    private long parseShortenedNumber(String value) {
        Character suffix = value.charAt(value.length() - 1);
        value = value.substring(0, value.length() - 1);
        if (suffix == 'K') {
            return (long) (Double.parseDouble(value) * 1000);
        } else if (suffix == 'M') {
            return (long) (Double.parseDouble(value) * 1000000);
        } else if (suffix == 'G') {
            return (long) (Double.parseDouble(value) * 1000000000);
        } else {
            return Long.parseLong(value);
        }
    }

    @Override
    public boolean equals(Object object) {
        try {
            return object != null && getValue("id").getString().equals((((EnterpriseData) object).getValue("id").getString()));
        } catch (ElementNotFoundException ex) {
            System.out.println(ex);
            return false;
        }
    }
}
