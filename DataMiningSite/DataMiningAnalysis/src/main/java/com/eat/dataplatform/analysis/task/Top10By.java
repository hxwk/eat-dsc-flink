package com.eat.dataplatform.analysis.task;

import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;

import java.text.SimpleDateFormat;

public class Top10By {
    public static void main(String[] args) {
        String path = "/home/cosmin/Projects/flink_projects/flink-java-project/data/";
        StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        TextInputFormat format = new TextInputFormat(
                new org.apache.flink.core.fs.Path(path));
        DataStream<String> inputStream = streamEnv.readFile(format, path, FileProcessingMode.PROCESS_CONTINUOUSLY, 100);

        DataStream<MyEvent> parsedStream = inputStream
                .map((line) -> {
                    String[] cells = line.split(",");
                    MyEvent event = new MyEvent(cells[1], cells[2], cells[3]);
                    return event;
                });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd")

        parsedStream.writeAsCsv("path_"+)
    }
}
