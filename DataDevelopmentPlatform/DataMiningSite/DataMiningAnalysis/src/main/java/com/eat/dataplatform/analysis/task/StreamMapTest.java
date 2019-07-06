package com.eat.dataplatform.analysis.task;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.File;

public class StreamMapTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Integer> data = env.fromElements(1, 2, 3, 4, 5);
        DataStream<Person> map = (DataStream<Person>) data.map((String a)-> {
            Person p = new Person();
            String[] b= a.split(";");
            p.setAge(Integer.parseInt(b[0]));
        }).returns(Person.class);
        map.print();
        env.execute();
        File[] hiddenFile = new File(".").listFiles(File::isHidden);
    }
}
