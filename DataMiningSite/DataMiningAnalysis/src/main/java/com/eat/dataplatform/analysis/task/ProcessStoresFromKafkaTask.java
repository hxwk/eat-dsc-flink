package com.eat.dataplatform.analysis.task;

import com.eat.dataplatform.analysis.entity.ResInfo;
import com.eat.dataplatform.analysis.mongodb.KafkaStoreInfoSchema;
import com.eat.dataplatform.analysis.mongodb.RemoveInvalidStore;
import com.eat.dataplatform.analysis.mongodb.WriteToMongo;
import com.eat.dataplatform.analysis.utils.PropertiesUtil;
import com.eat.dataplatform.analysis.utils.ReadProperties;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
/**
 * @ClassName ProcessStoresFromKafkaTask
 *
 * @Description 从kafka topic中获取到指定数据存储到mongodb中，
 * 需要传递文件路径， --input file:///user/path/txt
 * @Author KANGJIAN
 * @Date 2019/3/28 9:36
 **/
public class ProcessStoresFromKafkaTask {
    final static Logger logger = LoggerFactory.getLogger(ProcessStoresFromKafkaTask.class);
    public static void main(String[] args) throws Exception{
        final ParameterTool parameterTool = ParameterTool.fromArgs(args);
        if (parameterTool.getNumberOfParameters() < 4) {
            logger.info("Missing parameters!\n" +
                    "Usage: Kafka --input-topic <topic> " +
                    "--bootstrap.servers <kafka brokers> " +
                    "--zookeeper.connect <zk quorum> " +
                    "--group.id <some id> ");
            return;
        }

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().disableSysoutLogging();
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4,10000));
        env.enableCheckpointing(6000);
        env.getConfig().setGlobalJobParameters(parameterTool);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStream<ResInfo> input = env
                .addSource(
                        new FlinkKafkaConsumer011<>(
                                parameterTool.getRequired("input-topic"),
                                new KafkaStoreInfoSchema(),
                                parameterTool.getProperties())
                                .assignTimestampsAndWatermarks(new CustomWatermarkExtractor()))
                .filter(new RemoveInvalidStore());

        input.addSink(new WriteToMongo(ReadProperties.getKey("mongodb.host")
                ,Integer.parseInt(ReadProperties.getKey("mongodb.port"))
                ,ReadProperties.getKey("mongodb.database")
                ,ReadProperties.getKey("mongodb.tb.CaptureResInfoFromKafka")
                ,ReadProperties.getKey("mongodb.username")
                ,ReadProperties.getKey("mongodb.pwd")));
        logger.info("mongodb.username:{},mongodb.pwd:{}",PropertiesUtil.getStringValue("mongodb.username"),
                PropertiesUtil.getStringValue("mongodb.pwd"));

        env.execute("Store info save into mongodb from kafka");
    }

    /**
     * A custom {@link AssignerWithPeriodicWatermarks}, that simply assumes that the input stream
     * records are strictly ascending.
     *
     * <p>Flink also ships some built-in convenience assigners, such as the
     * {@link BoundedOutOfOrdernessTimestampExtractor} and {@link AscendingTimestampExtractor}
     */
    private static class CustomWatermarkExtractor implements AssignerWithPeriodicWatermarks<ResInfo> {

        private static final long serialVersionUID = -742759155861320822L;

        private long currentTimestamp = Long.MIN_VALUE;

        @Override
        public long extractTimestamp(ResInfo event, long previousElementTimestamp) {
            // the inputs are assumed to be of format (message,timestamp)
            this.currentTimestamp = event.getCreateTimestamp();
            return event.getCreateTimestamp();
        }

        @Nullable
        @Override
        public Watermark getCurrentWatermark() {
            return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
        }
    }
}
