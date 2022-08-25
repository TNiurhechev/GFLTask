package db;

public class Expression {
    private int id;
    private String expression;
    private double result;

    public Expression(String expression, double result){
        this.expression = expression;
        this.result = result;
    }

    public Expression(int id, String expression, double result){
        this.id = id;
        this.expression = expression;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public double getResult() {
        return result;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "Expression: "+expression+" with the result of "+result+" is saved in the database under the id of "+id;
    }
}
