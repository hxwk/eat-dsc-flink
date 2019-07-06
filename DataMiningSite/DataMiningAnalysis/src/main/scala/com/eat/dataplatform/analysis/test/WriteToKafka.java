package com.eat.dataplatform.analysis.test;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Properties;

public class WriteToKafka {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");

        DataStream<String> stream = env.addSource(new SimpleStringGenerator());
        stream.addSink(new FlinkKafkaProducer011<>("flink-demo", new SimpleStringSchema(), properties));

        env.execute();
    }

    /**
     * Simple Class to generate data
     */
    public static class SimpleStringGenerator implements SourceFunction<String> {
        private static final long serialVersionUID = 119007289730474249L;
        boolean running = true;
        long i = 0;
        @Override
        public void run(SourceContext<String> ctx) throws Exception {
            while(running) {
                ctx.collect("FLINK-"+ (i++));
                Thread.sleep(10);
            }
        }
        @Override
        public void cancel() {
            running = false;
        }
    }
}
