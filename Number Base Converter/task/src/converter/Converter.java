package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Converter {

    /**
     * Method convert integer decimal number to baseTo system
     * @param demNumber - integer number in decimal system
     * @param baseTo - target system
     * @return - number in target system
     */
    public static StringBuilder convertFromDecimalsInteger(BigInteger demNumber, int baseTo) {
        StringBuilder result = new StringBuilder();
        BigInteger currentDem = demNumber;
        BigInteger baseBig = BigInteger.valueOf(baseTo);
        while (currentDem.compareTo(baseBig) >= 0) {
            result.append(convertToLetters(
                    currentDem.remainder(baseBig).intValue())
            );
            currentDem = currentDem.divide(baseBig);
        }
        result.append(convertToLetters(currentDem.intValue()));
        return result.reverse();
    }

    /**
     * Convert fractional decimal to baseTo system
     * @param demFractional - fractional number (integer part = 0) in decimal system
     * @param baseTo - target system
     * @return - fractional number in target system
     */
    public static StringBuilder convertFromDecimalsFractional(BigDecimal demFractional, int baseTo) {
        StringBuilder result = new StringBuilder();
        BigDecimal currentFractional = demFractional;
        BigDecimal baseBig = BigDecimal.valueOf(baseTo);
        for (int i = 0; i < 5; i++) {
            currentFractional = currentFractional.multiply(baseBig);
            System.out.println("currentFractional " + currentFractional);
            if (currentFractional.compareTo(BigDecimal.ONE) >= 0) {
                BigDecimal integerPart = currentFractional.setScale(0, RoundingMode.DOWN);
                result.append(convertToLetters(integerPart.intValue()));
                currentFractional = currentFractional.subtract(integerPart);
            } else {
                result.append('0');
            }
        }

        return result;
    }

    private static String convertToLetters(int n) {
        if (n < 10) {
            return n + "";
        } else {
            return ((char) (n - 10 + 'a')) + "";
        }
    }

    /**
     * Method convert integer number from source base to decimal system
     * @param sourceNumber - number in source system
     * @param baseFrom - source base
     * @return - decimal integer number
     */
    public static BigInteger convertIntegerToDecimals(String sourceNumber, int baseFrom) {
        ArrayList<BigInteger> arrForSum = new ArrayList<>();
        char[] reversedNumber = new StringBuilder(sourceNumber).reverse().toString().toCharArray();
        for (int i = 0; i < reversedNumber.length; i++) {
            if (reversedNumber[i] >= '0' && reversedNumber[i] <= '9') {
                arrForSum.add(BigInteger.valueOf(reversedNumber[i] - '0').multiply(
                        BigInteger.valueOf((long) Math.pow(baseFrom, i)))
                );
            } else if (reversedNumber[i] >= 'a' && reversedNumber[i] <= 'z') {
                arrForSum.add(BigInteger.valueOf(reversedNumber[i] - 'a' + 10).multiply(
                        BigInteger.valueOf((long) Math.pow(baseFrom, i)))
                );
            } else if (reversedNumber[i] >= 'A' && reversedNumber[i] <= 'Z') {
                arrForSum.add(BigInteger.valueOf(reversedNumber[i] - 'A' + 10).multiply(
                        BigInteger.valueOf((long) Math.pow(baseFrom, i)))
                );
            } else {
                // System.out.println("ERROR: wrong number");
            }
        }
        BigInteger sum = BigInteger.valueOf(0);
        for (BigInteger el : arrForSum) {
            sum = el.add(sum);
        }
        return sum;
    }

    /**
     * Method convert fractional number to Decimal system
     * @param sourceNumber - number in baseFrom system
     * @param baseFrom - source system
     * @return fractional decimal number
     */
    public static BigDecimal convertFractionalToDecimals(String sourceNumber, int baseFrom) {
        ArrayList<BigDecimal> arrForSum = new ArrayList<>();
        char[] reversedNumber = sourceNumber.toCharArray();
        for (int i = 0, j = -1; i < reversedNumber.length; i++, j--) {
            BigDecimal multiplicand = BigDecimal.valueOf(Math.pow(baseFrom, j));
            if (reversedNumber[i] >= '0' && reversedNumber[i] <= '9') {
                arrForSum.add(BigDecimal.valueOf(reversedNumber[i] - '0').multiply(multiplicand)
                );
            } else if (reversedNumber[i] >= 'a' && reversedNumber[i] <= 'z') {
                arrForSum.add(BigDecimal.valueOf(reversedNumber[i] - 'a' + 10).multiply(multiplicand)
                );
            } else if (reversedNumber[i] >= 'A' && reversedNumber[i] <= 'Z') {
                arrForSum.add(BigDecimal.valueOf(reversedNumber[i] - 'A' + 10).multiply(multiplicand)
                );
            } else {
                // System.out.println("ERROR: wrong number");
            }
        }
        BigDecimal sum = BigDecimal.valueOf(0);
        for (BigDecimal el : arrForSum) {
            sum = el.add(sum);
        }
        return sum;
    }

    /**
     * Main universal method converts number from source system to target system
     * @param baseFrom - source system
     * @param baseTo - target system
     * @param number - number in source system
     * @return - number in target system
     */
    public static String convert(int baseFrom, int baseTo, String number) {
        StringBuilder result = new StringBuilder();
        if (number.contains(".")) {
            String integer = number.split("\\.")[0];
            String fractional = number.split("\\.")[1];
            BigInteger decimalInteger = convertIntegerToDecimals(integer, baseFrom);
            BigDecimal decimalFractional = convertFractionalToDecimals(fractional, baseFrom);
            result.append(convertFromDecimalsInteger(new BigInteger(String.valueOf(decimalInteger)), baseTo));
            result.append(".");
            result.append(convertFromDecimalsFractional(decimalFractional, baseTo));
        } else {
            BigInteger decimalInteger = convertIntegerToDecimals(number, baseFrom);
            result.append(convertFromDecimalsInteger(new BigInteger(String.valueOf(decimalInteger)), baseTo));
        }
        return String.format(
                "Conversion result: %s",
                result
        );
    }
}
