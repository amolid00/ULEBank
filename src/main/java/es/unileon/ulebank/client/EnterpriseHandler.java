/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.client;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.utils.CifControl;

/**
 * Identifier of enterprises
 * 
 * @author Gonzalo Nicolas Barreales
 */

// TODO modify cif with the corresponding data
public class EnterpriseHandler implements Handler {

    /**
     * 
     */
    char entityLetter;

    /**
     * Two digits to identify the province
     */
    int provinceCode;

    /**
     * Five digits imposed by the Administration in function of the provice
     */
    int registrationCode;

    /**
     * The control code is a number if the entity letter is K, Q or S, and is a
     * number if the entitity letter is A, B, E or H With the rest of the entity
     * letters, the control code can be a number or a letter
     */
    char controlCode;

    /**
     * 
     * @param entityLetter
     * @param cifNumber
     * @param cifFinalDigit
     */
    public EnterpriseHandler(char entityLetter, int cifNumber,
            char cifFinalDigit) throws MalformedHandlerException {
        if ((Integer.toString(cifNumber).length() <= 7)
                && (Integer.toString(cifNumber).length() >= 6)) {
            this.entityLetter = entityLetter;
            this.provinceCode = cifNumber / 100000;
            this.registrationCode = cifNumber - (this.provinceCode * 100000);
            this.controlCode = cifFinalDigit;
            if (!CifControl.instance().isCifValid(entityLetter,
                    this.provinceCode, this.registrationCode, this.controlCode)) {
                throw new MalformedHandlerException("Invalid control code");
            }
        } else {
            throw new MalformedHandlerException("Invalid CIF");
        }
    }

    @Override
    public int compareTo(Handler another) {
        return this.toString().compareTo(another.toString());
    }

    @Override
    public String toString() {
        return this.entityLetter + Integer.toString(this.provinceCode)
                + Integer.toString(this.registrationCode) + this.controlCode;
    }

}
