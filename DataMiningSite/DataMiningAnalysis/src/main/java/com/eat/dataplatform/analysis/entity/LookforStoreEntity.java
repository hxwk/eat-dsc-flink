package com.eat.dataplatform.analysis.entity;

import java.io.Serializable;

/**
 * @ClassName LookforStoreEntity
 * @Description 筛选出来的店铺
 * @Author KANGJIAN
 * @Date 2019/4/1 10:53
 **/
public class LookforStoreEntity implements Serializable {
    private String id;
    private String resName;
    //private String distance;
    private String area;
    private String createTime;
    private Double latitude;
    private Double longitude;
    private Long createTimestamp;
/*    private Double byLatitude;
    private Double byLongitude;
    private Double byDistance;*/
    private String bizAreaId;

    @Override
    public String toString() {
        return "LookforStoreEntity{" +
                "id='" + id + '\'' +
                ", resName='" + resName + '\'' +
                ", area='" + area + '\'' +
                ", createTime='" + createTime + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", createTimestamp=" + createTimestamp +
                ", bizAreaId='" + bizAreaId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getBizAreaId() {
        return bizAreaId;
    }

    public void setBizAreaId(String bizAreaId) {
        this.bizAreaId = bizAreaId;
    }
}
