package com.eat.dataplatform.analysis.utils;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 提供操作mongodb的类
 *
 * @author KANGJIAN
 */
public class MongoOperation {
    static final Logger logger = LoggerFactory.getLogger(MongoOperation.class);
    private MongoDatabase db;
    private static MongoClient mongoClient;

    public MongoOperation(String host, int port, String dbName, String username, String pwd) {
        ServerAddress seed = new ServerAddress(host, port);
        MongoCredential mongoCredential = MongoCredential.createCredential(username, dbName, pwd.toCharArray());
        mongoClient = new MongoClient(seed, mongoCredential, new MongoClientOptions.Builder().build());
        this.db = connectToDB(host, port, dbName, username, pwd);
    }

    private MongoDatabase connectToDB(String host, int port, String dbName, String username, String pwd) {
        //mongoClient = new MongoClient(host, port);
        MongoDatabase db = mongoClient.getDatabase(dbName);
        return db;
    }

    public void createDocument(Document doc, String collName) {
        try {
            MongoCollection<Document> coll = db.getCollection(collName);
            coll.insertOne(doc);
        } catch (Exception e) {
        }
    }

    public Document findoneby(String tablename, String database, String storeBaseType) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        MongoCollection mongoCollection = mongoDatabase.getCollection(tablename);
        Document doc = new Document();
        doc.put("info", storeBaseType);
        FindIterable<Document> itrer = mongoCollection.find(doc);
        MongoCursor<Document> mongoCursor = itrer.iterator();
        if (mongoCursor.hasNext()) {
            return mongoCursor.next();
        } else {
            return null;
        }
    }

    //通过店铺名称，指定的经纬度和半径判断是否有文档，如果有取第一条
    public Document findoneby(String tablename, String database, Map<String, Object> keys) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        MongoCollection mongoCollection = mongoDatabase.getCollection(tablename);
        Document doc = new Document();
        doc.put("resName", keys.get("resName"));
        doc.put("byLatitude", keys.get("byLatitude"));
        doc.put("byLongitude", keys.get("byLongitude"));
        doc.put("byDistance", keys.get("byDistance"));
        MongoCursor<Document> mongoCursor = mongoCollection.find(doc).iterator();
        if (mongoCursor.hasNext()) {
            return mongoCursor.next();
        } else {
            return null;
        }
    }

    public static void saveOrUpdateMongo(String tablename, String database, Document doc) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        MongoCollection<Document> mongocollection = mongoDatabase.getCollection(tablename);
        if (!doc.containsKey("_id")) {
            ObjectId objectid = new ObjectId();
            doc.put("_id", objectid);
            mongocollection.insertOne(doc);
            return;
        }
        Document matchDocument = new Document();
        String objectid = doc.get("_id").toString();
        matchDocument.put("_id", new ObjectId(objectid));
        FindIterable<Document> findIterable = mongocollection.find(matchDocument);
        if (findIterable.iterator().hasNext()) {
            mongocollection.updateOne(matchDocument, new Document("$set", doc));
            try {
                logger.info("come into saveOrUpdateMongo ---- update---" + JSONObject.toJSONString(doc));
            } catch (Exception e) {
                logger.warn("MongoUtils:saveOrUpdateMongo:Exception:{}", e.getMessage());
            }
        } else {
            mongocollection.insertOne(doc);
            try {
                System.out.println("come into saveOrUpdateMongo ---- insert---" + JSONObject.toJSONString(doc));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        mongoClient.close();
    }

    public MongoDatabase getDb() {
        return db;
    }
}
