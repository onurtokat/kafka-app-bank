package com.onurtokat.producer;

import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerConfigCreator {

    private Properties config = new Properties();

    public ProducerConfigCreator(){
        config.setProperty(org.apache.kafka.clients.producer.ProducerConfig.CLIENT_ID_CONFIG, "producer-app");
        config.setProperty(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "fbtstcld03.fibabanka.local:9092,fbtstcld04.fibabanka.local:9092,fbtstcld05.fibabanka.local:9092");
        config.setProperty(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        config.setProperty(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
    }

    public Properties getConfig() {
        return config;
    }
}
