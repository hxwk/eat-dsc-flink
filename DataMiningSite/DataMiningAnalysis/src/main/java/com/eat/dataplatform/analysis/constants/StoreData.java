package com.eat.dataplatform.analysis.constants;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * @ClassName StoreData
 * @Description TODO
 * @Author KANGJIAN
 * @Date 2019/3/28 13:49
 **/
public class StoreData {
    private static final String[] WORDS = new String[]{
        "5c99e4172f5c3e5c339a5b23;御蝶坊生日蛋糕(前海店);3.1km;人均￥15;南山中心区;4.0分;蛋糕;8.8代10元，90代100元;22.54904;113.94756;852847615;1553589271036"
    };

    public static DataSet<String> getDefaultTextLineDataSet(ExecutionEnvironment env) {
        return env.fromElements(WORDS);
    }
}
