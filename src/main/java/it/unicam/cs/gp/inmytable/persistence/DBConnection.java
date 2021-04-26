package it.unicam.cs.gp.inmytable.persistence;


import java.sql.*;

public class DBConnection {
    private static DBConnection instance;
    private static final String LOCAL_CONNECTION_STRING = "jdbc:mysql://127.0.0.1:3306/inmytable";
    private static final String LOCAL_USERNAME = "root";
    private static final String LOCAL_PASSWORD =  "root";
    private static Connection connection;
    private static Statement command;
    private static ResultSet data;

    public DBConnection() throws SQLException {
        if(connection==null) this.defaultConnection();
    }

    public DBConnection(String connectionString, String username, String password) throws SQLException {
        if(connection==null) this.connection(connectionString,username,password);
    }

    private void connection(String connectionString, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(connectionString, username, password);
        command = connection.createStatement();
    }

    private void defaultConnection() throws SQLException {
        this.connection(LOCAL_CONNECTION_STRING, LOCAL_USERNAME, LOCAL_PASSWORD);
    }

    public Connection getConnection(){
        return connection;
    }

    public Statement getCommand(){
        return command;
    }

    public ResultSet getData(){
        return data;
    }

    public void setData(String sql) throws SQLException {
        data=command.executeQuery(sql);
    }

    public void close() throws SQLException {
        command.close();
        connection.close();
    }

}
