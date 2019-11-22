package com.onurtokat.producer;

import com.onurtokat.dao.HiveAccess;
import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TargetEnv("kafka_servers/kafka_test_server.properties")
@RunWith(ZeroCodeUnitRunner.class)
class GenericProducerTest {

    private Properties config = null;
    private static final String DATABASENAME = "ods";
    private static final String TABLENAME = "cash_loan";
    private ResultSet resultSet = null;
    private ResultSetMetaData resultSetMetaData = null;
    private static int counter = 0;

    @BeforeEach
    void setUp() {
        config = new ProducerConfigCreator().getConfig();
        resultSet = new HiveAccess().getTableRecords(DATABASENAME, TABLENAME, "select * from " +
                DATABASENAME + "." + TABLENAME);
        try {
            resultSetMetaData = resultSet.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void loadTopicResultSetNotNull() {
        assertNotNull(resultSet);
    }

    @Test
    void loadTopicResultSetMetaDataNotNull() {
        assertNotNull(resultSetMetaData);
    }

    @Test
    void loadTopicResultCounterLessThan10000() {
        try {
            while (resultSet.next()) {
                if (counter == 10000) {
                    break;
                }
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
        assertTrue(counter == 10000);
        counter = 0;
    }

    @Test
    void loadTopicResultSetMetaDataCount() {
        try {
            assertEquals(resultSetMetaData.getColumnCount(), 29);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @JsonTestCase("kafka/produce/test_kafka_produce_ack_metadata.json")
    void loadTopicProduceCheck() {
    }
}