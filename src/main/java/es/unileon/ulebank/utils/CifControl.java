/* Application developed for AW subject, belonging to passive operations
 group.*/

package es.unileon.ulebank.utils;

/**
 *
 * @author gonzalo
 */
public class CifControl {

    private final char[] cifControlLetters;
    private static CifControl instance = null;

    private CifControl() {
        final char[] data = { 'J', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };
        this.cifControlLetters = data;
    }

    public static CifControl instance() {
        if (CifControl.instance == null) {
            CifControl.instance = new CifControl();
        }
        return CifControl.instance;
    }

    public boolean isCifValid(char entityLetter, int provinceCode,
            int registrationCode, char controlCode) {
        boolean result = false;
        final char entityLetterRaw = Character.toUpperCase(entityLetter);
        if (Character.isLetter(entityLetterRaw)
                && CifControl.checkRangeProvinceRange(provinceCode)
                && (Integer.toString(registrationCode).length() <= 5)) {
            final int code = (provinceCode * 100000) + registrationCode;
            final int controlObtained = CifControl.getControl(code);
            if (Character.isLetter(controlCode)) {
                if (this.cifControlLetters[controlObtained] == controlCode) {
                    result = true;
                }
            } else if (controlObtained == Integer.parseInt(Character
                    .toString(controlCode))) {
                result = true;
            }
        }
        return result;
    }

    private static boolean checkRangeProvinceRange(int provinceCode) {
        return (provinceCode < 100) && (provinceCode > 0);
    }

    private static int getControl(int code) {
        int result = 0;
        final String strCode = Integer.toString(code);
        for (int i = 0; i < strCode.length(); i++) {
            if ((((i + 1) / 2) * 2) == (i + 1)) {
                result += 2 * Integer.parseInt(Character.toString(strCode
                        .charAt(i)));
            } else {
                result += Integer
                        .parseInt(Character.toString(strCode.charAt(i)));
            }
        }
        final String controlData = String.valueOf(result);
        result = Integer.parseInt(Character.toString(controlData
                .charAt(controlData.length() - 1)));
        if (result != 0) {
            result = 10 - result;
        }
        return result;
    }
}
