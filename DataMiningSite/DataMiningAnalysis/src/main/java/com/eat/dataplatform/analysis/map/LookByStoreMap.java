package com.eat.dataplatform.analysis.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eat.dataplatform.analysis.entity.BizAreaGeo;
import com.eat.dataplatform.analysis.entity.LookforStoreEntity;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName LookByStoreMap
 * @Description 处理文本文件，处理成对象
 * @Author KANGJIAN
 * @Date 2019/4/1 10:56
 **/
public class LookByStoreMap extends RichMapFunction<String, LookforStoreEntity> {
    private String bizAreaId;

    @Override
    public LookforStoreEntity map(String value) throws Exception {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        String[] storeInfo = value.split(";", -1);
        String id = storeInfo[0];
        String area = storeInfo[1];
        String avgPrice = storeInfo[2];
        String cate = storeInfo[3];
        String createTime = storeInfo[4];
        String createTimestamp = storeInfo[5];
        String distance = storeInfo[6];
        String latitude = storeInfo[7];
        String longitude = storeInfo[8];
        String ratingText = storeInfo[9];
        String resHashCode = storeInfo[10];
        String resName = storeInfo[11];
        String timestamp = storeInfo[12];
        String voucherInfo = storeInfo[13];

        LookforStoreEntity lookforStoreEntity = new LookforStoreEntity();
        //lookforStoreEntity.setId(UUID.randomUUID().toString());
        lookforStoreEntity.setResName(resName);
        lookforStoreEntity.setArea(area);
        //lookforStoreEntity.setDistance(distance);
        lookforStoreEntity.setCreateTime(createTime);
        long createTimestampn = 0L;
        Double latituden = 0D;
        Double longituden = 0D;
        if (StringUtils.isNotEmpty(createTimestamp)) {
            createTimestampn = Long.parseLong(createTimestamp);
        }
        lookforStoreEntity.setCreateTimestamp(createTimestampn);
        if (StringUtils.isNotEmpty(latitude)) {
            latituden = Double.parseDouble(latitude);
        }
        if (StringUtils.isNotEmpty(longitude)) {
            longituden = Double.parseDouble(longitude);
        }
        lookforStoreEntity.setLatitude(latituden);
        lookforStoreEntity.setLongitude(longituden);

/*        lookforStoreEntity.setByLatitude(byLatitude);
        lookforStoreEntity.setByLongitude(byLongitude);
        lookforStoreEntity.setByDistance(byDistance);*/
        return lookforStoreEntity;
    }

    //1*22.5,123.5@1000
    //2*22.5,123.5#22.5,123.5#22.5,123.5#22.5,123.5
    @Override
    public void open(Configuration parameters) throws Exception {
        String bizAreaGeoInfo = parameters.getString("bizAreaGeoInfo", "");
        //List<BizAreaGeo> bizArea = JSON.parseObject(bizAreaGeoInfo, (Type) BizAreaGeo.class);
        //List<BizAreaGeo> bizArea = JSON.parseArray(JSON.parseObject(bizAreaGeoInfo).getString("bizAreaGeoList"), BizAreaGeo.class);
        List<BizAreaGeo> bizArea = JSONObject.parseArray(bizAreaGeoInfo,BizAreaGeo.class);
        if (bizArea.size() == 1) {
            bizAreaId = bizArea.get(0).getBizAreaId();
        } else if (bizArea.size() >= 2) {
            List<BizAreaGeo> geo = bizArea.stream()
                    .filter(biz -> Double.parseDouble(biz.getBizAreaId()) != -1)
                    .collect(Collectors.toList());
            bizAreaId = geo.get(0).getBizAreaId();
        }
        /*byLatitude = parameters.getDouble("byLatitude",0d);
        byLongitude = parameters.getDouble("byLongitude",0d);
        byDistance = parameters.getDouble("byDistance",0d);*/
    }
}
