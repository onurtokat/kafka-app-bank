package com.onurtokat.dao;

import com.onurtokat.util.ResultSetReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class HiveAccess {

    private static Logger logger = LoggerFactory.getLogger(HiveAccess.class);

    public ResultSet getTableRecords(String databaseName, String tableName, String query) {
        return new ConnectionClass(databaseName, tableName, query).getResultSet();
    }
}
