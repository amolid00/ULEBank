package es.unileon.ulebank.googlefinances;

public abstract class EnterpriseDataListener {

    public final EnterpriseData enterprise;

    public EnterpriseDataListener(EnterpriseData enterprise) {
        this.enterprise = enterprise;
    }

    public EnterpriseData getEnterpriseData() {
        return this.enterprise;
    }

    public void start() {
        try {
            System.out.println("Starting listener for " + this.enterprise.getValue("name") + "...");
        } catch (ElementNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void pause() {
        try {
            System.out.println("Stopping listener for " + this.enterprise.getValue("name") + "...");
        } catch (ElementNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public abstract void exec();
}
