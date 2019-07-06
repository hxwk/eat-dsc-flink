package com.eat.dataplatform.analysis.task;

import com.alibaba.fastjson.JSON;
import com.eat.dataplatform.analysis.abstraction.MongoDBHandler;
import com.eat.dataplatform.analysis.entity.BizAreaGeo;
import com.eat.dataplatform.analysis.entity.LookforStoreEntity;
import com.eat.dataplatform.analysis.filter.StoreFilter;
import com.eat.dataplatform.analysis.map.LookByStoreMap;
import com.google.common.collect.Lists;
import org.apache.commons.io.Charsets;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.api.java.BatchTableEnvironment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.eat.dataplatform.analysis.constants.Constant.*;

/**
 * @ClassName LookforStoreByAreaTask
 * @Description To obtain the area of the food shop，according to
 * the given latitude and longitude and radius.
 * @Author KANGJIAN
 * @Date 2019/4/1 9:58
 **/
public class LookforStoreByAreaTask extends MongoDBHandler {
    public static void main(String[] args) throws Exception {
        //args = new String[]{"--input", "D:\\CaptureResInfoFromKafka.txt", "--bizAreaGeo", "2*22.5,123.5#22.5,123.5#22.5,123.5#22.5,123.5"};
        args = new String[]{"--input", "D:\\CaptureResInfoFromKafka.txt", "--bizAreaGeo", "1*22.54904,113.94756@1000"};
        ParameterTool params = ParameterTool.fromArgs(args);
        if (!params.has("input") || !params.has("bizAreaGeo")) {
            System.out.println("商铺文件和对应商圈经纬度信息不能为空，请检查 --input [value] --bizAreaGeo [value]");
            return;
        }

        //1、获得环境变量
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //2、获取表环境
        BatchTableEnvironment tenv = BatchTableEnvironment.getTableEnvironment(env);
        env.getConfig().setGlobalJobParameters(params);
        //3、导入数据
        DataSource<String> text = null;
        String bizAreaGeo = null;
        if (params.has("input")) {
            text = env.readTextFile(params.get("input"), Charsets.UTF_8.name());
        }

        List<BizAreaGeo> bizAreaGeos = Lists.newArrayList();
        if(params.has("bizAreaGeo")){
            bizAreaGeo = params.get("bizAreaGeo");
            if(bizAreaGeo.contains(AT)){
                String[] geoAndDistance = bizAreaGeo.split(AT);
                System.out.println("输入的是商圈圆形区域经纬度");
                if(geoAndDistance.length==2){
                    String[] firstString = geoAndDistance[0].split("\\*");
                    String bizAreaId = firstString[0];
                    String[] geo = firstString[1].split(DOUHAO);
                    bizAreaGeos.add(new BizAreaGeo(bizAreaId,Double.parseDouble(geo[0]),Double.parseDouble(geo[1])
                            ,Double.parseDouble(geoAndDistance[1])));
                }
            }else if(bizAreaGeo.contains(NUM)){
                List<String> geoAndDistance = Arrays.asList(bizAreaGeo.split(NUM));
                System.out.println("输入的是商圈多边形区域经纬度列表");
                if(geoAndDistance.size()>=2){
                    List<String[]> collect = geoAndDistance.stream()
                            .map(s -> s.split(DOUHAO))
                            .collect(Collectors.toList());
                    Stream<Tuple3> tuple3Stream = collect
                            .stream()
                            .map(ss -> getTuple(ss));

                    //tuple3Stream.map(t->t.f0.toString()+" "+t.f1.toString()+" "+t.f2.toString()).forEach((x)->System.out.println(x));

                    bizAreaGeos = tuple3Stream.map(t -> new BizAreaGeo(t.f0.toString()
                            ,cd(t.f1)
                            ,cd(t.f2)
                            ,-1))   //无距离，为-1
                            .collect(Collectors.toList());

                    //bizAreaGeos.stream().forEach(x->System.out.println(x));
                }
            }
        }
        String bizAreaGeoInfo = JSON.toJSONString(bizAreaGeos);
        Configuration conf = new Configuration();
        conf.setString("bizAreaGeoInfo",bizAreaGeoInfo);
        DataSet<LookforStoreEntity> dataSet = text.map(new LookByStoreMap()).withParameters(conf)
                .filter(new StoreFilter())
                .withParameters(conf)
                .distinct("resName")
                .setParallelism(2);
        //dataSet.print();
        //4、注册成表
        /*tenv.registerDataSet("storeinfo",dataSet,"");
        //5、查询转换
        Table table = tenv.sqlQuery("select code,simpleName,dateTime,qianshoupanjia,kaipanjia,hignestPrice,lowestPrice from wordcount");
        tenv.toDataSet(table,)
        DataSet<StoreInfo> mapresult = text.map(new StoreMap());
        DataSet<StoreInfo> reduceresutl = mapresult.groupBy("groupfield").reduce(new StoreReduce());*/
        //6、sink到mongodb
        try {
            dataSet.print();
            saveOrUpdate(dataSet.collect());
            env.execute("LookforStoreByAreaTask");
        } catch (Exception e) {
        }
    }

    //2*22.5,123.5#22.5,123.5#22.5,123.5#22.5,123.5
    private static Tuple3 getTuple(String[] geo){
        Tuple3 tuple3 = new Tuple3();
        if(geo[0].contains(STAR)){
            tuple3.f0 = geo[0].split("\\*")[0];
            tuple3.f1 = geo[0].split("\\*")[1];
            tuple3.f2 = geo[1];
        }else{
            tuple3.f0 = -1;   //代表没有商圈ID，无效值
            tuple3.f1 = geo[0];
            tuple3.f2 = geo[1];
        }
        return tuple3;
    }

    private static Double cd(Object str){
        return Double.parseDouble(str.toString());
    }

}
