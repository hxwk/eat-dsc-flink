package com.eat.dataplatform.analysis.test;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Properties;

public class ReadFromKafka {
    public static void main(String[] args) throws Exception {
        // create execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.10.25:9092");
        properties.setProperty("group.id", "test");

        DataStream<String> stream = env
                .addSource(new FlinkKafkaConsumer011<>("test", new SimpleStringSchema(), properties));

        stream.map(new MapFunction<String, String>() {
            private static final long serialVersionUID = -6867736771747690202L;

            @Override
            public String map(String value) throws Exception {
                return "Stream Value: " + value;
            }
        }).print();

        stream.addSink(new FlinkKafkaProducer011<String>("192.168.10.25:9092","testd",new SimpleStringSchema()));

        env.execute("Kafka 0.11 Example");
    }

}
