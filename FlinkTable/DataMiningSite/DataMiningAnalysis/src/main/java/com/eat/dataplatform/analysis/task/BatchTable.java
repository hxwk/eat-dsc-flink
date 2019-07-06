package com.eat.dataplatform.analysis.task;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Types;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.sinks.CsvTableSink;
import org.apache.flink.table.sinks.TableSink;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BatchTable {
    public static void main(String[] args) throws Exception {
        //1、注册环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //2、注册批处理表环境
        BatchTableEnvironment tenv = BatchTableEnvironment.getTableEnvironment(env);
        //3、导入数据集

        /*DataSet<WC> dataSet = env.fromElements(
                new WC("hello",1),
                new WC("world",2),
                new WC("word",1),
                new WC("word",3)
        );*/

        DataSource<Tuple7<String, String, String, String, String, String, String>> csvReader =
                env.readCsvFile("D:\\300001.SZ.CSV").fieldDelimiter(",").ignoreFirstLine().types(String.class,String.class
                ,String.class,String.class,String.class,String.class,String.class);
        //DataSource<Entity> dataSet = csvReader.pojoType(Entity.class);
        //tenv.fromDataSet(dataSet);
        csvReader.map(new RichMapFunction<Tuple7<String, String, String, String, String, String, String>, Tuple7<String, String, Date, String, Integer, Integer, Integer>>() {
            @Override
            public Tuple7<String, String, Date, String, Integer, Integer, Integer> map(Tuple7<String, String, String, String, String, String, String> value) throws Exception {
                Tuple7<String, String, Date, String, Integer, Integer, Integer> dest = new Tuple7<>();
                String date = value.f2;
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                Date parse = dateFormat.parse(date);
                dest.f2 = parse;
                dest.f0 = value.f0;
                dest.f1 = value.f1;
                dest.f3 = value.f3;
                dest.f4 = Integer.parseInt(value.f4);
                dest.f5 = Integer.parseInt(value.f5);
                dest.f6 = Integer.parseInt(value.f6);
                return dest;
            }
        });
        //4、注册数据集
        tenv.registerDataSet("wordcount",csvReader,"code,simpleName,dateTime,qianshoupanjia,kaipanjia,hignestPrice,lowestPrice");
        //5、写sql转换
        //Table table = tenv.sqlQuery("select * from wordcount order by dateTime");
        Table table = tenv.sqlQuery("select code,simpleName,dateTime,qianshoupanjia,kaipanjia,hignestPrice,lowestPrice from wordcount");
        //6、Table转换成DatSet
        DataSet<Entity> result = tenv.toDataSet(table, Entity.class);
        //7、打印
        String[] fieldNames = new String[]{"code","simpleName","dateTime","dateTime","qianshoupanjia","kaipanjia","hignestPrice"};
        TypeInformation[] fieldTypes = new TypeInformation[]{Types.STRING(),Types.STRING(),Types.STRING(),Types.STRING(),
                Types.INT(),Types.INT(),Types.INT()};
        TableSink tableSink = new CsvTableSink("D:\\1.csv",",");
        tenv.registerTableSink("csvSinkTable",fieldNames,fieldTypes,tableSink);
        result.print();
    }

    /**
     * Simple POJO containing a word and its respective count.
     */
    public static class WC {
        public String word;
        public long frequency;

        // public constructor to make it a Flink POJO
        public WC() {}

        public WC(String word, long frequency) {
            this.word = word;
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return "WC " + word + " " + frequency;
        }
    }
}
