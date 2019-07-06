package com.eat.dataplatform.analysis.source;

import com.eat.dataplatform.analysis.utils.ReadProperties;
import org.apache.commons.io.Charsets;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.utils.ParameterTool;

import static com.eat.dataplatform.analysis.utils.ReadProperties.masterPath;

/**
 * @ClassName GetDataSet
 * @Description TODO
 * @Author KANGJIAN
 * @Date 2019/4/2 15:01
 **/
public class GetDataSet {
    private static enum DataSourceType{
        MYSQL,MONGODB,HIVE,HDFS,STATIC,TXT,CSV
    }
    //根据类别获取数据集合
    public DataSet<String> get(ExecutionEnvironment env, ParameterTool params, String dataSourceType) throws ClassNotFoundException {
        DataSource<String> dataSource = null;
        if(dataSourceType.equals(DataSourceType.TXT.name())){
            dataSource = env.readTextFile(params.get("input"), Charsets.UTF_8.name());
        }else if(dataSourceType.equals(DataSourceType.CSV)){
            CsvReader csvReader = env.readCsvFile(params.get("input"))
                    .fieldDelimiter(ReadProperties.getKey("csv.field.delimiter",masterPath));
        }else if(dataSourceType.equals(DataSourceType.STATIC)){
             dataSource = env.fromElements("1","2","3");
        }
        return dataSource;
    }
}
