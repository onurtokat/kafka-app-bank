package com.onurtokat.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class StreamsConfigCreator {

    private Properties config = new Properties();

    public StreamsConfigCreator() {
        config.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "Streams-app");
        config.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                "fbtstcld03.fibabanka.local:9092,fbtstcld04.fibabanka.local:9092,fbtstcld05.fibabanka.local:9092");
        config.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        config.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
    }

    public Properties getConfig() {
        return config;
    }
}
