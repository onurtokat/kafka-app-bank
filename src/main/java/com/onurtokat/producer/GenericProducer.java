package com.onurtokat.producer;

import com.onurtokat.dao.HiveAccess;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

public class GenericProducer {

    private static final String SELECT = "select * from ";
    private HiveAccess hiveAccess = new HiveAccess();
    private ProducerRecord<String, String> producerRecord = null;
    private StringBuilder stringBuilder = new StringBuilder();

    public void loadTopic(String topicName, String databaseName, String tableName) {
        try (ResultSet resultSet = hiveAccess.getTableRecords(databaseName, tableName, SELECT);
             KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(new ProducerConfigCreator().getConfig())) {
            int counter = 0;
            String str = "";
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                if (counter == 10000) {
                    System.out.println("Count is greater than 10000. Program is being terminated");
                    break;
                }
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    str = i != rsmd.getColumnCount() ? "," : "";
                    stringBuilder.append(resultSet.getString(i) + str);
                }
                System.out.println(stringBuilder.toString());
                producerRecord = new ProducerRecord<>(topicName, null, stringBuilder.toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                kafkaProducer.send(producerRecord);
                kafkaProducer.flush();
                counter++;
                stringBuilder.setLength(0);//clean
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
