package com.onurtokat.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetReader {

    //private static Logger logger = LoggerFactory.getLogger(ResultSetReader.class);

    public void getRecords(ResultSet resultSet, String tableName) {
        try (ResultSet resultSet1 = resultSet) {
            //Field[] fields = Class.forName("com.onurtokat.model."+tableName).getClass().getDeclaredFields();
            //int counter = fields.length;
            //System.out.println("calculated count: "+counter);
            ResultSetMetaData rsmd = resultSet1.getMetaData();
            System.out.println("Column Count: " + rsmd.getColumnCount());
            while (resultSet1.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print(resultSet1.getString(i)+",");
                    //logger.info(resultSet1.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //logger.error("Error occured when record read from resultset: ", e);
        }
    }
}
