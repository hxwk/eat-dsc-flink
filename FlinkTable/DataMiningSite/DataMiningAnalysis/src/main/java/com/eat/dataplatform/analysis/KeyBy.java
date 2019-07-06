package com.eat.dataplatform.analysis;

import org.apache.flink.api.common.functions.RichReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KeyBy {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple4<String,String,String,Integer>> input = env.fromElements(TRANSCRIPT);
        KeyedStream<Tuple4<String, String, String, Integer>, Tuple> keyedStream = input.keyBy(0);
        keyedStream.max(3);

       /* keyedStream.reduce(new RichReduceFunction<Tuple4<String, String, String, Integer>>() {
            @Override
            public Tuple4<String, String, String, Integer> reduce(Tuple4<String, String, String, Integer> value1, Tuple4<String, String, String, Integer> value2) throws Exception {
                value1.f3+=value2.f3;
                return value1;
            }
        });*/
        keyedStream.print();
        env.execute();
    }

    public static final Tuple4[] TRANSCRIPT = new Tuple4[]{
            Tuple4.of("class1","zhangsan","Chinese",100),
            Tuple4.of("class2","lisi","English",32),
            Tuple4.of("class1","wangwu","Chinese",54),
            Tuple4.of("class2","zhangsan","Chemichy",87),
            Tuple4.of("class1","zhangsan","Chinese",58)
    };
}
