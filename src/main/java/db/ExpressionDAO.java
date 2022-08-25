package db;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpressionDAO implements DAO {

    private Connection getConnection() {
        DBManager dbManager = new DBManager("localhost:3306", "root", "12345678", "calc");
        return dbManager.connect();
    }

    @Override
    public Object get(long id) {
        Connection connection = this.getConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE if NOT EXISTS Expressions(Id INTEGER PRIMARY KEY auto_increment, " +
            "Expr VARCHAR(100), Res REAL);");
            ResultSet expressionsSet = statement.executeQuery("SELECT * FROM Expressions;");

            while(expressionsSet.next()) {
                int expressionId = expressionsSet.getInt("Id");
                String expression = expressionsSet.getString("Expr");
                double result = expressionsSet.getDouble("Res");
                Expression e = new Expression(expressionId,expression,result);
                if ((long)expressionId == id) {
                    return e;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Object getByExpression(String exp) {
        Connection connection = this.getConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE if NOT EXISTS Expressions(Id INTEGER PRIMARY KEY auto_increment, " +
                    "Expr VARCHAR(100), Res REAL);");
            ResultSet expressionsSet = statement.executeQuery("SELECT * FROM Expressions;");

            while(expressionsSet.next()) {
                int expressionId = expressionsSet.getInt("Id");
                String expression = expressionsSet.getString("Expr");
                double result = expressionsSet.getDouble("Res");
                Expression e = new Expression(expressionId,expression,result);
                if (expression.equals(exp)) {
                    return e;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List getAll() {
        Connection connection = this.getConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE if NOT EXISTS Expressions(Id INTEGER PRIMARY KEY auto_increment, " +
                    "Expr VARCHAR(100), Res REAL);");
            ResultSet expressionsSet = statement.executeQuery("SELECT * FROM Expressions;");
            ArrayList<Expression> expressions = new ArrayList<>();

            while(expressionsSet.next()) {
                int expressionId = expressionsSet.getInt("Id");
                String expression = expressionsSet.getString("Expr");
                double result = expressionsSet.getDouble("Res");
                Expression e = new Expression(expressionId,expression,result);
                expressions.add(e);
            }
            return expressions;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void save(Object o) throws SQLException {
        Connection connection = this.getConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE if NOT EXISTS Expressions(Id INTEGER PRIMARY KEY auto_increment, " +
            "Expr VARCHAR(100), Res REAL);");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Expression e = (Expression) o;
        PreparedStatement insertExpression = connection.prepareStatement("INSERT INTO Expressions(Expr,Res) VALUES (?,?)");
        insertExpression.setString(1, e.getExpression());
        insertExpression.setDouble(2, e.getResult());
        insertExpression.executeUpdate();
    }

    @Override
    public void update(Object o, String[] args) throws SQLException {
        Connection connection = this.getConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE if NOT EXISTS Expressions(Id INTEGER PRIMARY KEY auto_increment, " +
                    "Expr VARCHAR(100), Res REAL);");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Expression e = (Expression) o;
        PreparedStatement updateExpression = connection.prepareStatement("UPDATE Expressions SET Expr=?, Res=? " +
        "WHERE Id=?");
        updateExpression.setString(1,args[1]);
        updateExpression.setDouble(2,Double.parseDouble(args[2]));
        updateExpression.setInt(3,Integer.parseInt(args[0]));
        updateExpression.executeUpdate();
    }

    @Override
    public void delete(Object o) throws SQLException {
        Connection connection = this.getConnection();

        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE if NOT EXISTS Expressions(Id INTEGER PRIMARY KEY auto_increment, " +
            "Expr VARCHAR(100), Res REAL);");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Expression e = (Expression) o;
        PreparedStatement deleteExpression = connection.prepareStatement("DELETE FROM Expressions WHERE Expr=?");
        deleteExpression.setString(1, e.getExpression());
        deleteExpression.executeUpdate();
    }
}

