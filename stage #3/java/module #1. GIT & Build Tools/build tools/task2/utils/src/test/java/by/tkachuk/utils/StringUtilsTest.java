package by.tkachuk.utils;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class StringUtilsTest {
    @Test
    public void isPositiveNumberTest() {
        String test = "12";
        Assertions.assertTrue(StringUtils.isPositiveNumber(test));
    }

    @Test
    public void isNotPositiveNumberTest() {
        String test = "-12";
        Assertions.assertFalse(StringUtils.isPositiveNumber(test));
    }

    @Test()
    public void isPositiveNumberTestWithNullParam() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> StringUtils.isPositiveNumber(null));
    }


}
