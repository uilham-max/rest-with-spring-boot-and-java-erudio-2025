package br.com.erudio.util;

import br.com.erudio.exception.UnsupportedMathOperationException;

public class NumberConverter {

    public static Double toDouble(String strNumber){
        if(strNumber == null || strNumber.isEmpty()) throw new UnsupportedMathOperationException("Please provide a valid number!");
        String number = strNumber.replace(",",".");
        return Double.parseDouble(number);
    }

}
