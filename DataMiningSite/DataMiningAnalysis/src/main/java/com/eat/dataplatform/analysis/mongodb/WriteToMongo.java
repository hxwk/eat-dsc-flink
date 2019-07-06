package com.eat.dataplatform.analysis.mongodb;

import com.eat.dataplatform.analysis.entity.ResInfo;
import com.eat.dataplatform.analysis.utils.MongoOperation;
import com.google.gson.Gson;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义MongoDB连接器，读取flink数据流
 * @author KANGJIAN
 */
public class WriteToMongo extends RichSinkFunction<ResInfo>{
    static final Logger logger = LoggerFactory.getLogger(WriteToMongo.class);
    private String host;
    private int port;
    private String name_db;
    private String collName;
    private String username;
    private String pwd;
    private MongoOperation op;

    public WriteToMongo(String host, int port, String name_db, String collName,String username,String pwd){
        this.host = host;
        this.port = port;
        this.name_db = name_db;
        this.username = username;
        this.pwd = pwd;
        this.collName = collName;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        this.op = new MongoOperation(host, port, name_db,username,pwd);
    }

    public void close(){
        op.closeConnection();
    }

    @Override
    public void invoke(ResInfo record, Context context) throws Exception {
        Document doc;
        //MongoCollection<Document> collection = op.getDb().getCollection(collName);
        Gson gson = new Gson();
        String resInfoString = gson.toJson(record);
        doc = new Document().parse(resInfoString);
        op.createDocument(doc,collName);
        logger.debug("insert into mongodb doc: {}",doc);
    }
}