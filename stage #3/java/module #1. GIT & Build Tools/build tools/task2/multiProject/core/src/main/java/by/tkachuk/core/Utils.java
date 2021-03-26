package by.tkachuk.core;

import by.tkachuk.utils.StringUtils;

import java.util.Arrays;

public class Utils {
    public static boolean isAllPositiveNumber(String... str) {
        return Arrays.stream(str).allMatch(s -> StringUtils.isPositiveNumber(s));
    }


}
