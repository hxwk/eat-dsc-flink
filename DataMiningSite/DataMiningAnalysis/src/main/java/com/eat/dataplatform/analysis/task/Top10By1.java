package com.eat.dataplatform.analysis.task;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.Csv;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Rowtime;
import org.apache.flink.table.descriptors.Schema;

public class Top10By1 {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnvironment = TableEnvironment.getTableEnvironment(env);
        tableEnvironment
                .connect(
                        new Kafka()
                                .version("0.11")
                                .topic("test-input")
                                .startFromEarliest()
                                .property("zookeeper.connect", "localhost:2181")
                                .property("bootstrap.servers", "localhost:9092")

                )
        .withFormat(
                new Json().failOnMissingField(false).deriveSchema())
                // declare the schema of the table
                .withSchema(
                        new Schema()
                                .field("appName", Types.STRING())
                                .field("clientIp", Types.STRING())
                                .field("rowtime", Types.SQL_TIMESTAMP())
                                .rowtime(new Rowtime()
                                        .timestampsFromField("uploadTime")
                                        .watermarksPeriodicBounded(60000)
                                )

                )
        .withSchema(
                new Schema()
                .field("rowtime", Types.SQL_TIMESTAMP)
                .rowtime(new Rowtime()
                        .timestampsFromField("timestamp")
                        .watermarksPeriodicBounded(60000))
                        .field("user", Types.LONG)
                        .field("message", Types.STRING)
        )
        .inAppendMode()
                .registerTableSource("MyTable");
    }
}
