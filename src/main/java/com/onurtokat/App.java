package com.onurtokat;

import com.onurtokat.dao.HiveAccess;
import com.onurtokat.producer.FileProducer;
import com.onurtokat.producer.GenericProducer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    private static final String SELECT = "select * from ";
    public static String topicName = null;
    public static String filePath = null;

    public static void main(String[] args) {

        GenericProducer genericProducer = new GenericProducer();
        genericProducer.loadTopic(args[0], args[1], args[2]);
        //genericProducer.loadTopic("kurlar-doviz-kuru-topic","ods","kurlar_doviz_kuru");
        //FileProducer fileProducer = new FileProducer();
        //fileProducer.loadTopic("cash-loan-topic","C:/sandbox/part-00000");

        //fileProducer.loadTopic(args[0], args[1]);
    }
}
