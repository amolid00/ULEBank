package es.unileon.ulebank.googlefinances;

public class AboveValueListener extends EnterpriseDataListener {

    private final double limit;

    public AboveValueListener(EnterpriseData enterprise, double limit) {
        super(enterprise);
        this.limit = limit;
    }

    @Override
    public void exec() {
        try {
            if (this.enterprise.getValue("l_fix").getDouble() < this.limit) {
                System.out.println("(AboveValueListener) Limit: " + this.limit + "\t Current: " + this.enterprise.getValue("l_fix"));
            } else {
                System.out.println("(AboveValueListener) Nothing to do.");
            }
        } catch (ElementNotFoundException ex) {
            System.out.println(ex);
        }
    }
}
