package com.onurtokat.dao;

import antlr.SemanticException;
import org.apache.hive.service.cli.HiveSQLException;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionClassTest {

    private static final String DATABASE = "ods";
    private static final String TABLENAME = "cash_loan";
    private static final String SELECT = "select * from ";

    @Test
    void getResultSetNotNull() {
        assertNotNull(new ConnectionClass(DATABASE, TABLENAME,
                SELECT + DATABASE + "." + TABLENAME + " limit 1"));
    }
}