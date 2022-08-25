import java.util.Arrays;

public class Calculator {
    private final String[] expressionArray;
    private int position;
    private static final String[] operators = {"+","-","*","/","(",")"};
    private static final String[] ops = {"+","-","*","/"};
    private static final String[] validStrings = {"+","-","*","/","0","1","2","3","4","5","6","7","8","9","(",")"," "};

    public Calculator(String expression){
        StringBuilder sb = new StringBuilder(expression);
        //separating expression elements by spaces
        StringBuilder spaceSeparated = new StringBuilder();
        for(int i = 0; i < sb.length(); i++){
            char current = sb.charAt(i);
            boolean isOperator = false;
            for(String o:operators){
                if(sb.charAt(i)==o.charAt(0)) {
                    isOperator = true;
                }
            }
            if(isOperator==true){
                spaceSeparated.append(" ");
                spaceSeparated.append(String.valueOf(current));
                spaceSeparated.append(" ");
            }
            else
                spaceSeparated.append(String.valueOf(current));
        }
        if(spaceSeparated.charAt(sb.length()-1)==' ')
            spaceSeparated.deleteCharAt(sb.length()-1);
        if(spaceSeparated.charAt(0)==' ')
            spaceSeparated.deleteCharAt(0);
        for(int i = 0; i < spaceSeparated.length(); i++){
            if(spaceSeparated.charAt(i)==' ')
                if(spaceSeparated.charAt(i-1)==' ')
                    spaceSeparated.deleteCharAt(i-1);
        }
        this.expressionArray = spaceSeparated.toString().split(" ");
        this.position = 0;
    }

    //checks for any invalid symbols or sequences of symbols
    public boolean isValid(){
        int bracketsCounter = 0;
        boolean valid = false;
        for(int i = 1; i < expressionArray.length; i++){
            for(String o: Arrays.asList(ops)){
                if(expressionArray[i].equals("+")||expressionArray[i].equals("*")||expressionArray[i].equals("/")){
                    for(String op:ops){
                        if(expressionArray[i-1].equals(op))
                            throw new IllegalArgumentException("Invalid sequence of operators!");
                    }
                }
            }
        }
        for(String s:expressionArray){
            if(s.equals("(")||s.equals(")"))
                bracketsCounter++;
            if(s.length()==1) {
                for (String v : validStrings) {
                    if (s.equals(v))
                        valid = true;
                }
            }
            else{
                String[] elems = s.split("");
                for(String e:elems){
                    for (String v : validStrings) {
                        if (e.equals(v))
                            valid = true;
                    }
                }
            }
            if(valid==false) {
                throw new IllegalArgumentException("Invalid symbols in expression!");
            }
        }
        if(bracketsCounter%2==1) {
            throw new IllegalArgumentException("No closing bracket was found!");
        }
        return true;
    }

    //counts numbers in expression
    public int numbersInExpression(){
        int counter = 0;
        for(String e:expressionArray)
            if(!e.equals("+")&&!e.equals("-")&&!e.equals("*")&&!e.equals("/")&&!e.equals("(")&&!e.equals(")"))
                counter++;
        return counter;
    }

    public double parse() {
        Double result = calculate();
        if (position != expressionArray.length) {
            throw new IllegalStateException("Error in expression at " + expressionArray[position]);
        }
        return result;
    }

    //calculates low priority operations(+,-)
    public double calculate(){
        double firstElem = calculateComplexOperations();
        while(position < expressionArray.length){
            String operator = expressionArray[position];
            if (!operator.equals("+")&&!operator.equals("-")) {
                break;
            }
            else {
                position++;
            }
            double secondElem = calculateComplexOperations();
            if(operator.equals("+"))
                firstElem += secondElem;
            else
                firstElem -= secondElem;
        }
        return firstElem;
    }

    //calculates high priority operations(*,/)
    public double calculateComplexOperations(){
        double firstElem = calculateBrackets();
        while(position < expressionArray.length){
            String operator = expressionArray[position];
            if (!operator.equals("*")&&!operator.equals("/"))
                break;
            else
                position++;
            double secondElem = calculateBrackets();
            if(operator.equals("*"))
                firstElem *= secondElem;
            else
                firstElem /= secondElem;
        }
        return firstElem;
    }

    //calculates within brackets
    public double calculateBrackets(){
        String current = expressionArray[position];
        double res;
        if(current.equals("(")){
            position++;
            res = calculate();
            String secondBracket;
            if (position < expressionArray.length) {
                secondBracket = expressionArray[position];
            }
            else{
                throw new IllegalArgumentException("No closing bracket was found!");
            }
            if(position<expressionArray.length&&secondBracket.equals(")")){
                position++;
                return res;
            }
            throw new IllegalArgumentException("\")\" was expected instead of "+secondBracket+"!");
        }
        position++;
        return Double.parseDouble(current);
    }
}
