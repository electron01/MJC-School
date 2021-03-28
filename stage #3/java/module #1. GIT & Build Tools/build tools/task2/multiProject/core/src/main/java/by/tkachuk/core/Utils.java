package by.tkachuk.core;
import by.tkachuk.utils.StringUtils;
import java.util.Arrays;

/**
 * This class contains a method for verifying that an all strings in an array is a positive number.
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class Utils {
    /**
     * Utils.isAllPositiveNumber("12","79")   = true
     * Utils.isAllPositiveNumber("12","-79")  = false
     * Utils.isAllPositiveNumber("0","0","0") = true
     * @param str - strings array for check
     * @return true if all strings in str pass check for is positive number
     */
    public static boolean isAllPositiveNumber(String... str) {
        return Arrays.stream(str).allMatch(StringUtils::isPositiveNumber);
    }
}
