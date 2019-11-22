package com.onurtokat.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@TargetEnv("kafka_servers/kafka_test_server.properties")
@RunWith(ZeroCodeUnitRunner.class)
class FileProducerTest {

    @Test
    void loadTopicConfigCheck() {
        assertEquals("fbtstcld03.fibabanka.local:9092,fbtstcld04.fibabanka.local:9092,fbtstcld05.fibabanka.local:9092",
                new ProducerConfigCreator().getConfig().getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals(StringSerializer.class.getName(),
                new ProducerConfigCreator().getConfig().getProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
        assertEquals(StringSerializer.class.getName(),
                new ProducerConfigCreator().getConfig().getProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
    }

    @Test
    @JsonTestCase("kafka/produce/test_kafka_produce_ack_metadata.json")
    void loadTopicProduceCheck() {
    }
}