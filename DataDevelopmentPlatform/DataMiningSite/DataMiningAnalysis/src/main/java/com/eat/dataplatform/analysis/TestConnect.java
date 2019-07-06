package com.eat.dataplatform.analysis;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.util.Collector;

public class TestConnect {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Long> someStreams = env.generateSequence(0, 100);
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        DataStream<String> otherStreams = env.fromElements(WORDS());
        ConnectedStreams<Long,String> connectedStreams = someStreams.connect(otherStreams);
        DataStream<String> result = connectedStreams.flatMap(new CoFlatMapFunction<Long, String, String>() {
            @Override
            public void flatMap1(Long value, Collector<String> out) throws Exception {
                out.collect(value.toString());
            }

            @Override
            public void flatMap2(String value, Collector<String> out) throws Exception {
                for(String word:WORDS().split("\\W+")){
                    out.collect(word);

                }
            }
        });

        result.print();
        env.execute();
    }


    private static String WORDS(){
        return "hello world, this is a good job,please trust me, i hope you be careful not to make the mistake " +
                "again next time";
    }
}
