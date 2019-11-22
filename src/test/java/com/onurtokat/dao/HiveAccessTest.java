package com.onurtokat.dao;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class HiveAccessTest {

    private static final String DATABASENAME = "ods";
    private static final String TABLENAME = "cash_loan";

    @Test
    void getTableRecordsNotNull() {
        ResultSet resultSet = new HiveAccess().getTableRecords(DATABASENAME, TABLENAME, "select * from " +
                DATABASENAME + "." + TABLENAME + " limit 1");
        assertNotNull(resultSet);
    }
}