package com.eat.dataplatform.analysis.task;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;

public class WordCountSQL {
    public static void main(String[] args) throws Exception {
        // set up execution environment
        //1、设置执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        //2、批处理表环境
        BatchTableEnvironment tEnv = BatchTableEnvironment.getTableEnvironment(env);
        //3、导入数据
        DataSet<WC> input = env.fromElements(
                new WC("Hello", 1),
                new WC("Ciao", 2),
                new WC("Hello", 1));

        // register the DataSet as table "WordCount"
        //4、注册数据集
        tEnv.registerDataSet("WordCount", input, "word, frequency");

        // run a SQL query on the Table and retrieve the result as a new Table
        //5、生成查询语句生成Table
        Table table = tEnv.sqlQuery(
                "SELECT word, SUM(frequency) as frequency FROM WordCount GROUP BY word");
        //6、Table转换成数据集
        DataSet<WC> result = tEnv.toDataSet(table, WC.class);
        //7、打印数据集
        result.print();
    }

    // *************************************************************************
    //     USER DATA TYPES
    // *************************************************************************

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
