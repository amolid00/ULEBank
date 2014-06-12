package es.unileon.ulebank.googlefinances;

public class JSONValue<T> {

    private final T value;

    public JSONValue(T value) {
        this.value = value;
    }

    public int getInt() {
        return Integer.parseInt((String) this.value);
    }

    public double getDouble() {
        return Double.parseDouble((String) this.value);
    }

    public String getString() {
        return (String) this.value;
    }
}
