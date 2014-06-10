/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.account;

/**
 *
 * @author runix
 */
public class DetailedInformation {

    private final StringBuilder info;
    private boolean nonEditable;

    /**
     *
     * @param information
     */
    public DetailedInformation(String information) {
        this.info = new StringBuilder(information);
        this.nonEditable = false;
    }

    /**
     *
     */
    public DetailedInformation() {
        this("");
    }

    /**
     *
     * @param information
     */
    public void appendInformation(String information) {
        if (!this.nonEditable) {
            this.info.append(information);
        }
    }

    /**
     *
     */
    public void doFinal() {
        this.nonEditable = true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.info.toString();
    }
}
