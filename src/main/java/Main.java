import db.Expression;
import db.ExpressionDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        ExpressionDAO ed = new ExpressionDAO();
        while(choice!=4){
            System.out.println("Welcome to console calculator! Choose what you want to do:\n" +
                    "1 - calculate the result of an expression\n" +
                    "2 - search for saved expressions within range\n" +
                    "3 - edit saved expression\n" +
                    "4 - exit:");
            choice = scanner.nextInt();
            switch(choice){
                case 1:
                    String expr;
                    System.out.println("Enter an expression:");
                    expr = scanner.next();
                    Calculator calculator = new Calculator(expr);
                    if(!calculator.isValid())
                    break;
                    else{
                    double result = calculator.parse();
                    System.out.println("The result is "+result);
                    System.out.println("Expression contains "+calculator.numbersInExpression()+" numbers");
                    ed.save(new Expression(expr,(int)result));
                    }
                    break;
                case 2:
                    System.out.println("Enter a number for your search to be based on:");
                    double num = scanner.nextDouble();
                    int ch = 0;
                    ArrayList<Expression> expressionsList = (ArrayList<Expression>) ed.getAll();
                    System.out.println("Select search pattern:\n" +
                            "1 - less than \n" +
                            "2 - equal to\n" +
                            "3 - more than your input");
                    ch = scanner.nextInt();
                    switch(ch){
                        case 1:
                            for(Expression e:expressionsList){
                                if(e.getResult()<num)
                                    System.out.println(e.toString());
                            }
                            break;
                        case 2:
                            for(Expression e:expressionsList){
                                if(e.getResult()==num)
                                    System.out.println(e.toString());
                            }
                            break;
                        case 3:
                            for(Expression e:expressionsList){
                                if(e.getResult()>num)
                                    System.out.println(e.toString());
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Enter an expression you want to edit:");
                    String exp;
                    exp = scanner.next();
                    Expression searchedExpression = (Expression) ed.getByExpression(exp);
                    if(searchedExpression==null) {
                        System.out.println("No such expression was found, try again!");
                        break;
                    }
                    else{
                        System.out.println("Enter new expression: ");
                        String newExpr;
                        newExpr = scanner.next();
                        Calculator newExprCalc = new Calculator(newExpr);
                        if(!newExprCalc.isValid())
                            break;
                        else{
                            double newRes = newExprCalc.parse();
                            System.out.println("The result is "+newRes);
                            System.out.println("Expression contains "+newExprCalc.numbersInExpression()+" numbers");
                            String[] arguments = {Integer.toString(searchedExpression.getId()),newExpr,Double.toString(newRes)};
                            ed.update(new Expression(searchedExpression.getId(),newExpr,newRes),
                                    arguments);
                        }
                    }
                    break;
                case 4:
                    System.out.println("Thanks for using this console calculator! See ya)");
                    return;
                default:
                    System.out.println("No such option exists!");
                    break;
            }
        }
    }
}
