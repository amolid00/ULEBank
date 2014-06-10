/* Application developed for AW subject, belonging to passive operations
 group.*/
package es.unileon.ulebank.utils;

/**
 * Class based in Songleton pattern that gets the dni letter of a dni number
 *
 * @author Gonzalo Nicolas Barreales
 */
public class DniLetters {

    /**
     * instance of the class
     */
    private static DniLetters instance = null;

    /**
     * dni letters table
     */
    private final char[] dniLetters;

    /**
     * Gets the unique instance of the class, if it doesn't exists, the instance
     * is created
     *
     * @return instance
     */
    public static DniLetters getInstance() {
        if (DniLetters.instance == null) {
            DniLetters.instance = new DniLetters();
        }
        return DniLetters.instance;
    }

    /**
     * Constructos that create the dni letters table.
     */
    private DniLetters() {
        this.dniLetters = new char[] { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F',
                'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L',
                'C', 'K', 'E' };
    }

    /**
     * Obtains the dni correct letter and compare it with the parameter
     * dniLetter
     *
     * @param dniNumber
     * @param dniLetter
     * @return true if the letter is valid, false if the letter is not correct
     */
    public boolean isDniValid(int dniNumber, char dniLetter) {
        boolean result = false;
        final int dniRest = dniNumber % 23;
        if ((this.dniLetters[dniRest] == dniLetter)
                && (Integer.toString(dniNumber).length() <= 8)) {
            result = true;
        }
        return result;
    }

}
