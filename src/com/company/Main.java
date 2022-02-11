package com.company;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(calc("I + II"));

    }

    public static String calc(String inputString) throws Exception {

        CalculatorHelper calculatorHelper = new CalculatorHelper(inputString);
        return calculatorHelper.toCalculate();
    }
}

enum TypeOfNumbers {
    ARABIC, ROMANIAN
}

class CalculatorHelper {

    public final String[] ARABIC_LIST_NUMBERS = {"1", "2", "3", "4", "5" , "6", "7", "8", "9", "10"};
    public final String[] ROMANIAN_LIST_NUMBERS = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    public final String[] ROMANIAN_LIST_DECIMAL_NUMBERS
            = {"X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};

    private String[] elements;
    private TypeOfNumbers typeOfNumbers;
    private String result;
    private String expression;


    public CalculatorHelper(String expression){
        this.expression = expression;
    }

    public  String toArabicNumber(String number){

        int index = Arrays.asList(ROMANIAN_LIST_NUMBERS).indexOf(number);

        return ARABIC_LIST_NUMBERS[index];
    }

    public String toRomanNumbers(String numberString){

        if (numberString.equals("100")) return "C";

        int number = Integer.parseInt(numberString);

        int firstNumberIndex = Arrays.asList(ARABIC_LIST_NUMBERS).indexOf(number / 10 + "");
        String firstDigit = (firstNumberIndex >= 0? ROMANIAN_LIST_DECIMAL_NUMBERS[firstNumberIndex]: "");

        int secondNumberIndex = Arrays.asList(ARABIC_LIST_NUMBERS).indexOf(number % 10 + "");
        String secondDigit = (secondNumberIndex >= 0? ROMANIAN_LIST_NUMBERS[secondNumberIndex]: "");

        String resultNumber  = firstDigit + secondDigit;

        return resultNumber;
    }

    public boolean isRightExpression(){

        elements = expression.split(" ");

        if (elements.length !=3) return false;

        HashSet<String> arabicNumbers = new HashSet<>(List.of(ARABIC_LIST_NUMBERS));
        HashSet<String> romanianNumbers = new HashSet<String>(List.of(ROMANIAN_LIST_NUMBERS));

        HashSet<String> symbols = new HashSet<>();
        symbols.add("+");
        symbols.add("-");
        symbols.add("/");
        symbols.add("*");

        boolean isRomanianExpression =
                romanianNumbers.contains(elements[0]) && romanianNumbers.contains(elements[2]) && symbols.contains(elements[1]);
        boolean isArabicExpression =
                arabicNumbers.contains(elements[0]) && arabicNumbers.contains(elements[2]) && symbols.contains(elements[1]);

        typeOfNumbers = (isArabicExpression? TypeOfNumbers.ARABIC: TypeOfNumbers.ROMANIAN);

        return isArabicExpression || isRomanianExpression;
    }

    public String toCalculate() throws Exception {

        int number1 = 0, number2 = 0, result = 0;

        if (isRightExpression()) {
            if (typeOfNumbers == TypeOfNumbers.ARABIC){
                number1 = Integer.parseInt(elements[0]);
                number2 = Integer.parseInt(elements[2]);
            }
            else if (typeOfNumbers == TypeOfNumbers.ROMANIAN){
                number1 = Integer.parseInt(toArabicNumber(elements[0]));
                number2 = Integer.parseInt(toArabicNumber(elements[2]));
            }

            if (elements[1].equals("+")){
                result = number1 + number2;
            } else if (elements[1].equals("-")){
                result = number1 - number2;
            } else if (elements[1].equals("*")){
                result = number1 * number2;
            } else if (elements[1].equals("/")) {
                result = number1 / number2;
            }

        } else {
            throw new Exception();
        }

        String resultString = "";

        if (typeOfNumbers == TypeOfNumbers.ROMANIAN) {
            if (result <= 0) throw new Exception();
            resultString = toRomanNumbers("" + result);
        } else if (typeOfNumbers == TypeOfNumbers.ARABIC){
            resultString = result + "";
        }
        return resultString;
    }

}

