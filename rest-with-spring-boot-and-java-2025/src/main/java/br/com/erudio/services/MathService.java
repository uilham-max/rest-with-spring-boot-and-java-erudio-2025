package br.com.erudio.services;

public interface MathService {

    public Double sum(String numberOne, String numberTwo) throws Exception;
    public Double subtraction(String numberOne, String numberTwo);
    public Double multiplication(String numberOne, String numberTwo);
    public Double division(String numberOne, String numberTwo);

}
