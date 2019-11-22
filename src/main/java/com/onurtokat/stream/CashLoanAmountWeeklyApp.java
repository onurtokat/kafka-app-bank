package com.onurtokat.stream;

import com.onurtokat.model.CashLoan;
import com.onurtokat.model.DovizKuru;
import com.onurtokat.serde.JsonDeserializer;
import com.onurtokat.serde.JsonSerializer;
import com.onurtokat.util.WeeklyDateCalculator;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class CashLoanAmountWeeklyApp {

    public static void main(String[] args) {

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, CashLoan> cashLoanStream = builder.stream("cashLoanFormattedStream-topic");
        KStream<String, DovizKuru> dovizStream = builder.stream("dovizFormatted-topic");

        Topology topology = builder.build();
        KafkaStreams kafkaStreams = new KafkaStreams(topology, new StreamsConfigCreator().getConfig());

        KStream<String, CashLoan> cashLoanFilteredStream = cashLoanStream.filter((key, value) ->
                value.getStatus() == 1 &&
                        value.getValuedate() != value.getMaturitydate() &&
                        value.getValuedate() <= WeeklyDateCalculator.getCurrentDate() &&
                        value.getValuedate() >= WeeklyDateCalculator.getPreviousWeekDate());

        KStream<String, String> joinedStream = cashLoanFilteredStream.join(dovizStream,
                (leftValue, rightValue) -> leftValue.getBranchcode() + "," + leftValue.getValuedate() + "," +
                        rightValue.getDovizalis() + "," + leftValue.getLoanamount(),
                JoinWindows.of(Duration.ofMinutes(1)),
                Joined.with(Serdes.String(), new CashLoanSerde(), new DovizKuruSerde())
        );

        joinedStream.to("joined-topic", Produced.with(Serdes.String(), Serdes.String()));

        //gracefully shutdown
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread("CashLoanAmountWeeklyApp-streamApp") {
            @Override
            public void run() {
                kafkaStreams.close();
                countDownLatch.countDown();
            }
        });

        try {
            kafkaStreams.start();
            System.out.println(topology.toString());
            countDownLatch.await();
        } catch (InterruptedException e) {
            System.exit(1);
            e.printStackTrace();
        }
        System.exit(0
        );
    }

    public static final class CashLoanSerde extends Serdes.WrapperSerde<CashLoan> {
        public CashLoanSerde() {
            super(new JsonSerializer<CashLoan>(), new JsonDeserializer<CashLoan>(CashLoan.class));
        }
    }

    public static final class DovizKuruSerde extends Serdes.WrapperSerde<DovizKuru> {
        public DovizKuruSerde() {
            super(new JsonSerializer<DovizKuru>(), new JsonDeserializer<DovizKuru>(DovizKuru.class));
        }
    }
}
