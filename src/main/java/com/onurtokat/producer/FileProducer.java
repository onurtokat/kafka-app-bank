package com.onurtokat.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FileProducer {

    private ProducerRecord<String, String> producerRecord = null;

    public void loadTopic(String topicName, String filePath) {

        try (FileInputStream inputStream = new FileInputStream(filePath);
             Scanner sc = new Scanner(inputStream, "UTF-8");
             KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(new ProducerConfigCreator().getConfig())) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                producerRecord = new ProducerRecord<>(topicName, null, line);
                kafkaProducer.send(producerRecord);
                kafkaProducer.flush();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
