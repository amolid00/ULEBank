/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.client;

import es.unileon.ulebank.handler.Handler;
import es.unileon.ulebank.handler.MalformedHandlerException;
import es.unileon.ulebank.utils.DniLetters;

/**
 * Identifies a person with the dni
 * 
 * @author Gonzalo Nicolas Barreales
 */
public class PersonHandler implements Handler {

    /**
     * DNI number
     */
    private int dni;

    /**
     * DNI letter
     */
    private char letter;

    private char foreingLetter;

    /**
     * Creates the handler of the person with the dni data
     * 
     * @param dni
     * @param letter
     * @throws MalformedHandlerException
     *             if the letter doesn't match with the dni number
     */
    public PersonHandler(int dni, char letter) throws MalformedHandlerException {
        if (DniLetters.getInstance().isDniValid(dni,
                Character.toUpperCase(letter))) {
            this.dni = dni;
            this.letter = Character.toUpperCase(letter);
            this.foreingLetter = ' ';
        } else {
            throw new MalformedHandlerException("Incorrect DNI");
        }
    }

    /**
     *
     * @param foreingLetterRaw
     * @param dni
     * @param letter
     * @throws MalformedHandlerException
     */
    public PersonHandler(char foreingLetter, int dni, char letter)
            throws MalformedHandlerException {
        final char foreingLetterRaw = Character.toUpperCase(foreingLetter);
        final char letterRaw = Character.toUpperCase(letter);
        int addFactor = 0;
        switch (foreingLetterRaw) {
            case 'X':
                break;
            case 'Y':
                addFactor = 10000000;
                break;
            case 'Z':
                addFactor = 20000000;
                break;
            default:
                throw new MalformedHandlerException("Incorrect NIE");
        }
        if (DniLetters.getInstance().isDniValid(addFactor + dni, letterRaw)) {
            this.foreingLetter = foreingLetterRaw;
            this.dni = dni;
            this.letter = letterRaw;
        } else {
            throw new MalformedHandlerException("Invalid NIE");
        }
    }

    @Override
    public int compareTo(Handler another) {
        return this.toString().compareTo(another.toString());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String result = "";
        if (this.foreingLetter != ' ') {
            result += this.foreingLetter;
        }
        result += Integer.toString(this.dni) + this.letter;
        return result;
    }

}
