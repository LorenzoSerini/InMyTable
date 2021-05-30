package it.unicam.cs.gp.inmytable.persistence;


import org.apache.tomcat.jni.Local;

import java.sql.*;

public abstract class DBConnection {
    private static final String AMAZON_CONNECTION_STRING = "jdbc:mysql://inmytable.c7ew54dy4rle.us-east-2.rds.amazonaws.com:3306/inmytable";
    private static final String AMAZON_USERNAME = "lollock";
    private static final String AMAZON_PASSWORD =  "GroupProject_InMyTable";
    private static final String IMT_CONNECTION_STRING = "jdbc:mysql://lhcp3182.webapps.net:3306/fy4dfjj1_uniu21";
    private static final String IMT_USERNAME = "fy4dfjj1";
    private static final String IMT_PASSWORD =  "KpyQg92E_Gng";
    private static final String LOCAL_CONNECTION_STRING = "jdbc:mysql://127.0.0.1:3306/inmytable";
    private static final String LOCAL_USERNAME = "root";
    private static final String LOCAL_PASSWORD =  "root";
    private static Connection connection;
    private static Statement command;
    private static ResultSet data;

    public DBConnection() throws SQLException {
        if(connection==null||connection.isClosed()) this.defaultConnection();
    }

    public DBConnection(String connectionString, String username, String password) throws SQLException {
        if(connection==null||connection.isClosed()) this.connection(connectionString,username,password);
    }

    private void connection(String connectionString, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(connectionString, username, password);
        command = connection.createStatement();
    }

    private void defaultConnection() throws SQLException {
        this.connection(AMAZON_CONNECTION_STRING, AMAZON_USERNAME, AMAZON_PASSWORD);
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
