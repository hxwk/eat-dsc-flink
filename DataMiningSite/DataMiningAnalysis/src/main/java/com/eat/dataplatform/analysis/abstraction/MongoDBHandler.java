package com.eat.dataplatform.analysis.abstraction;

import com.eat.dataplatform.analysis.entity.LookforStoreEntity;
import com.eat.dataplatform.analysis.utils.MongoOperation;
import com.eat.dataplatform.analysis.utils.ReadProperties;
import com.google.common.collect.Maps;
import org.bson.Document;

import java.util.List;
import java.util.Map;

/**
 * @ClassName MongoDBHandler
 * @Description TODO
 * @Author KANGJIAN
 * @Date 2019/4/3 11:24
 **/
public abstract class MongoDBHandler {
    protected static void saveOrUpdate(List<LookforStoreEntity> storeInfoList) {
        for (LookforStoreEntity storeInfo : storeInfoList) {
            String bizAreaId = storeInfo.getBizAreaId();
            String resName = storeInfo.getResName();
            String area = storeInfo.getArea();
            String createTime = storeInfo.getCreateTime();
            Double latitude = storeInfo.getLatitude();
            Double longitude = storeInfo.getLongitude();
            Long createTimestamp = storeInfo.getCreateTimestamp();
/*            Double byLatitude = storeInfo.getByLatitude();
            Double byLongitude = storeInfo.getByLongitude();
            Double byDistance = storeInfo.getByDistance();*/
            Map<String,Object> filterKey = Maps.newConcurrentMap();
            filterKey.put("resName",resName);
/*            filterKey.put("byLatitude",byLatitude);
            filterKey.put("byLongitude",byLongitude);
            filterKey.put("byDistance",byDistance);*/
            MongoOperation operation = new MongoOperation(ReadProperties.getKey("mongodb.host"),
                    Integer.parseInt(ReadProperties.getKey("mongodb.port")),
                    ReadProperties.getKey("mongodb.database"),
                    ReadProperties.getKey("mongodb.username"),
                    ReadProperties.getKey("mongodb.pwd"));

            Document doc = operation.findoneby(ReadProperties.getKey("mongodb.tb.LookforStoreByArea"),
                    ReadProperties.getKey("mongodb.database"), filterKey);
            if (doc == null) {
                doc = new Document();
                doc.put("resName", resName);
                doc.put("area", area);
                doc.put("createTime", createTime);
                doc.put("latitude", latitude);
                doc.put("longitude", longitude);
                doc.put("createTimestamp", createTimestamp);
                doc.put("bizAreaId",bizAreaId);
/*                doc.put("byLatitude", byLatitude);
                doc.put("byLongitude", byLongitude);
                doc.put("byDistance", byDistance);*/
            } else {
                doc.put("latitude", latitude);
                doc.put("longitude", longitude);
            }
            MongoOperation.saveOrUpdateMongo(ReadProperties.getKey("mongodb.tb.LookforStoreByArea"),
                    ReadProperties.getKey("mongodb.database"), doc);
        }
    }
}
