package db;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
    private String host;
    private String user;
    private String pass;
    private String dbName;
    private Connection conn = null;

    public DBManager(String host, String user, String pass, String dbName) {
        this.dbName = dbName;
        this.host = host;
        this.pass = pass;
        this.user = user;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException var6) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, (String)null, var6);
        }

    }

    public Connection connect() {
        String url = "jdbc:mysql://" + this.host + "/" + this.dbName + "?useUnicode=true&characterEncoding=utf-8";

        try {
            this.conn = DriverManager.getConnection(url, this.user, this.pass);
            return this.conn;
        } catch (SQLException var3) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, (String)null, var3);
            return null;
        }
    }

    public ResultSet getSelectQuery(String sql, Connection conn) {
        Statement comm = null;
        ResultSet set = null;

        try {
            comm = conn.createStatement();
            set = comm.executeQuery(sql);
        } catch (SQLException var6) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, (String)null, var6);
        }

        return set;
    }
}
