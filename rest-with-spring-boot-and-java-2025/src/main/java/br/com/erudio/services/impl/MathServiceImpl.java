package br.com.erudio.services.impl;

import br.com.erudio.exception.UnsupportedMathOperationException;
import br.com.erudio.services.MathService;
import br.com.erudio.util.NumberConverter;
import br.com.erudio.util.NumberValidation;
import org.springframework.stereotype.Service;

@Service
public class MathServiceImpl implements MathService {

    @Override
    public Double sum(String numberOne, String numberTwo) throws Exception {
        validate(numberOne, numberTwo);
        return NumberConverter.toDouble(numberOne) + NumberConverter.toDouble(numberTwo);
    }

    @Override
    public Double subtraction(String numberOne, String numberTwo) {
        validate(numberOne, numberTwo);
        return NumberConverter.toDouble(numberOne) - NumberConverter.toDouble(numberTwo);
    }

    @Override
    public Double multiplication(String numberOne, String numberTwo) {
        validate(numberOne, numberTwo);
        return NumberConverter.toDouble(numberOne) * NumberConverter.toDouble(numberTwo);
    }

    @Override
    public Double division(String numberOne, String numberTwo) {
        validate(numberOne, numberTwo);
        if (NumberValidation.isZero(numberTwo)) throw new UnsupportedMathOperationException("Division by zero");
        return NumberConverter.toDouble(numberOne) / NumberConverter.toDouble(numberTwo);
    }

    public void validate(String... numbers) {
        for (String number : numbers){
            if (!NumberValidation.isNumeric(number))
                throw new UnsupportedMathOperationException("Please enter a valid number!");

        }
    }

}
