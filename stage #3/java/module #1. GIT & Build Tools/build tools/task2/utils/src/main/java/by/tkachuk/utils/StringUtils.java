package by.tkachuk.utils;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Class StringUtils
 *
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class StringUtils {
    /**
     * StringUtils.isPositiveNumber("12")        = true
     * StringUtils.isPositiveNumber("-12")       = false
     * StringUtils.isPositiveNumber(null)        = throw IllegalArgumentException
     * StringUtils.isPositiveNumber("-12text")   = throw IllegalArgumentException
     * StringUtils.isPositiveNumber("")          = throw IllegalArgumentException
     * StringUtils.isPositiveNumber("0")         = true
     * Works only with decimal number system
     *
     * @param str - String to check
     * @return = true is Positive Number
     */
    public static boolean isPositiveNumber(String str) {
        if (!isNumbers(str)) {
            throw new IllegalArgumentException("Param is not a Number!");
        }
        return str.charAt(0) != '-';


    }

    /**
     * @param str - String to check
     * @return true if is Number, positive or negative
     */
    public static boolean isNumbers(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (str.charAt(0) == '-') {
            return NumberUtils.isDigits(str.substring(1));
        }
        return NumberUtils.isDigits(str);


    }


}
