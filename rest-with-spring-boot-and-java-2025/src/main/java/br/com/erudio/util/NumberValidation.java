package br.com.erudio.util;

public class NumberValidation {

    public static boolean isNumeric(String strNumber) {
        if(strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",",".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static boolean isZero(String strNumber) {
        return strNumber.equals("0");
    }

}
