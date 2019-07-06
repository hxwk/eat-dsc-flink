package com.eat.dataplatform.analysis.task;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.table.api.Types;
import org.apache.flink.util.Collector;


public class FlatMapDemo {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<Integer> input = env.fromElements(1, 2, 3, 4, 5);
        DataSet<String> result = input.flatMap((Integer number, Collector<String> collector) -> {
            StringBuilder builder = new StringBuilder();
            for (int i=0;i<number;i++) {
                builder.append("a");
                collector.collect(builder.toString());
            }
        }).returns(Types.STRING());

        result.print();
    }
}
