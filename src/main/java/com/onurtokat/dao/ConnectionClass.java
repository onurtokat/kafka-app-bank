package com.onurtokat.dao;

import java.sql.*;

public class ConnectionClass {

    public static final String CONNECTION_STRING = "jdbc:hive2://fbtstcld02.fibabanka.local:10000/";
    public static final String USER = "admin";
    public static final String PASSWORD = "admin";
    private ResultSet resultSet = null;
    public static final String SELECT = "SELECT * from ";
    public static final String DRIVER = "org.apache.hive.jdbc.HiveDriver";

    public ConnectionClass(String databaseName, String tableName, String query) {
        String sqlStatementSelect = null;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection connection = DriverManager.getConnection(CONNECTION_STRING
                    + databaseName, USER, PASSWORD);
            Statement statement = connection.createStatement();
            if (query != null) {
                sqlStatementSelect = query;
            } else {
                sqlStatementSelect = SELECT + databaseName + "." + tableName;
            }
            this.resultSet = statement.executeQuery(sqlStatementSelect);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet() {
        return this.resultSet;
    }
}
