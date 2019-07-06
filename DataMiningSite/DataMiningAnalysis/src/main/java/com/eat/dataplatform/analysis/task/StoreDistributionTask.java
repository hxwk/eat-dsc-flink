package com.eat.dataplatform.analysis.task;

import com.eat.dataplatform.analysis.constants.StoreData;
import com.eat.dataplatform.analysis.entity.StoreInfo;
import com.eat.dataplatform.analysis.map.StoreMap;
import com.eat.dataplatform.analysis.reduce.StoreReduce;
import com.eat.dataplatform.analysis.utils.MongoOperation;
import com.eat.dataplatform.analysis.utils.ReadProperties;
import com.google.common.base.Charsets;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @ClassName StoreDistributionTask
 * @Description 店铺分布统计任务，批处理任务，将根据品类计算当前店铺数据，存入mongodb
 * 需要传递文件路径， --input file:///user/path/txt
 * @Author KANGJIAN
 * @Date 2019/3/28 9:36
 **/
public class StoreDistributionTask {
    final static Logger logger = LoggerFactory.getLogger(StoreDistributionTask.class);
    public static void main(String[] args) throws Exception {
        //args = new String[]{"--input","C:\\Users\\admin\\Desktop\\CaptureResInfoFromKafka.txt"};
        ParameterTool params = ParameterTool.fromArgs(args);
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);
        //get input data
        DataSet<String> text;
        if(params.has("input")){
            text = env.readTextFile(params.get("input"), Charsets.UTF_8.name());
        }else{
            text = StoreData.getDefaultTextLineDataSet(env);
        }
        DataSet<StoreInfo> mapresult = text.map(new StoreMap());
        DataSet<StoreInfo> reduceresutl = mapresult.groupBy("groupfield").reduce(new StoreReduce());
        try {
            saveOrUpdate(reduceresutl.collect());
            env.execute("StoreDistributionTask job");
        } catch (Exception e) {
            logger.warn("StoreDistributionTask error:{}",e.getMessage());
        }
    }

    private static void saveOrUpdate(List<StoreInfo> storeInfoList){
        for(StoreInfo storeInfo:storeInfoList){
            String groupfield = storeInfo.getGroupfield();
            String store = storeInfo.getStore();
            Long count = storeInfo.getCount();
            MongoOperation operation = new MongoOperation(ReadProperties.getKey("mongodb.host"),
                    Integer.parseInt(ReadProperties.getKey("mongodb.port")),
                    ReadProperties.getKey("mongodb.database"),
                    ReadProperties.getKey("mongodb.username"),
                    ReadProperties.getKey("mongodb.pwd"));
            Document doc = operation.findoneby(ReadProperties.getKey("mongodb.tb.CaptureResSummary"),
                    ReadProperties.getKey("mongodb.database"),store);
            if(doc == null){
                doc = new Document();
                doc.put("groupfield",groupfield);
                doc.put("latitude","22.54904");
                doc.put("longitude","113.94756");
                //doc.put("storeName",store);
                doc.put("count",count);
                doc.put("curTime",System.currentTimeMillis());
            }else{
                Long countpre = doc.getLong("count");
                Long total = countpre+count;
                doc.put("count",total);
            }
            MongoOperation.saveOrUpdateMongo(ReadProperties.getKey("mongodb.tb.CaptureResSummary"),
                    ReadProperties.getKey("mongodb.database"),doc);
        }
    }
}
