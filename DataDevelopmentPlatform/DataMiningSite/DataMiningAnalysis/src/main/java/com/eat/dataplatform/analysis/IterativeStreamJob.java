package com.eat.dataplatform.analysis;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.DataStreamUtils;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class IterativeStreamJob {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Long> input = env.generateSequence(0, 10);
        DataStreamSource<Integer> integerDataStreamSource = env.fromElements(1, 2, 3, 4, 5, 6);
        //integerDataStreamSource.print();
        IterativeStream<Long> iterate = input.iterate();
        //DataStreamUtils.collect(integerDataStreamSource);
        SingleOutputStreamOperator<Long> map = iterate.map(new RichMapFunction<Long, Long>() {
            @Override
            public Long map(Long value) throws Exception {
                return value - 1;
            }
        });
        SingleOutputStreamOperator<Long> filter = map.filter(new RichFilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                return value > 0;
            }
        });
        iterate.closeWith(filter);
        SingleOutputStreamOperator<Long> lessThanZero = map.filter(new RichFilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                return value <= 0;
            }
        });
        lessThanZero.print();
        env.execute();

    }
}
