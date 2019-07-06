package com.eat.dataplatform.analysis.filter;

import com.alibaba.fastjson.JSON;
import com.eat.dataplatform.analysis.entity.BizAreaGeo;
import com.eat.dataplatform.analysis.entity.LookforStoreEntity;
import com.eat.dataplatform.analysis.utils.IsInCircle;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.configuration.Configuration;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName StoreFilter
 * @Description 过滤掉不在范围之内的数据
 * @Author KANGJIAN
 * @Date 2019/4/1 11:14
 **/
public class StoreFilter extends RichFilterFunction<LookforStoreEntity> {
    Double byLatitude;
    Double byLongitude;
    Double byDistance;
    Byte shape = -1; //0代表是圆形，1代表是多边形
    ArrayList<Point2D.Double> byPoint2ds;


    @Override
    public boolean filter(LookforStoreEntity value) throws Exception {
        boolean flag = false;
        Double latitude = value.getLatitude();
        Double longitude = value.getLongitude();
        if(shape == 0){
            flag = IsInCircle.isIntraArea(byLatitude, byLongitude, byDistance, latitude, longitude);
        }else if(shape == 1){
            flag = IsInCircle.isPtInPoly(new Point2D.Double(latitude,longitude),byPoint2ds);
        }
        return flag;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        String bizAreaGeoInfo = parameters.getString("bizAreaGeoInfo", "");
        List<BizAreaGeo> bizArea = (List<BizAreaGeo>) JSON.parse(bizAreaGeoInfo);
        if (bizArea.size() == 1 && bizArea.get(0).getDistance() != -1) {
            byLatitude = bizArea.get(0).getX();
            byLongitude = bizArea.get(0).getY();
            byDistance = bizArea.get(0).getDistance();
            shape = 0;
        } else if (bizArea.size() >= 2) {
             List<Point2D.Double> collect = bizArea.stream()
                    .map(bizarea -> new Point2D.Double(bizarea.getX(), bizarea.getY()))
                    .collect(Collectors.toList());
            byPoint2ds.addAll(collect);
            shape = 1;
        }
        /*byLatitude = parameters.getDouble("byLatitude", 0D);
        byLongitude = parameters.getDouble("byLongitude", 0D);
        byDistance = parameters.getDouble("byDistance", 0D);*/
    }
}