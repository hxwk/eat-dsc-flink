package com.eat.dataplatform.analysis.entity;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ResInfo {
    final static Logger logger = LoggerFactory.getLogger(ResInfo.class);
    private String Id;
    private Integer resHashCode;
    private String resName;
    private String distance;
    private String tag;
    private String smartTag;
    private String avgPrice;
    private String area;
    private String discountTxt;
    private String discountPromotion;
    private String labelContent;
    private String ratingText;
    private String cate;
    private String voucherInfo;
    private Date createTime;
    private long createTimestamp;
    private Double latitude;
    private Double longitude;

    public ResInfo(String id, Integer resHashCode, String resName, String distance, String tag, String smartTag, String avgPrice, String area, String discountTxt, String discountPromotion, String labelContent, String ratingText, String cate, String voucherInfo, Date createTime, long timestamp, Double latitude, Double longitude) {
        Id = id;
        this.resHashCode = resHashCode;
        this.resName = resName;
        this.distance = distance;
        this.tag = tag;
        this.smartTag = smartTag;
        this.avgPrice = avgPrice;
        this.area = area;
        this.discountTxt = discountTxt;
        this.discountPromotion = discountPromotion;
        this.labelContent = labelContent;
        this.ratingText = ratingText;
        this.cate = cate;
        this.voucherInfo = voucherInfo;
        this.createTime = createTime;
        this.createTimestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static ResInfo fromString(String eventStr) {
        logger.info("eventStr: {}", eventStr);
        Gson gson = new Gson();
        ResInfo resInfo = gson.fromJson(eventStr,ResInfo.class);
        return resInfo;
    }

    @Override
    public String toString() {
        return "ResInfo{" +
                "Id='" + Id + '\'' +
                ", resHashCode=" + resHashCode +
                ", resName='" + resName + '\'' +
                ", distance='" + distance + '\'' +
                ", tag='" + tag + '\'' +
                ", smartTag='" + smartTag + '\'' +
                ", avgPrice='" + avgPrice + '\'' +
                ", area='" + area + '\'' +
                ", discountTxt='" + discountTxt + '\'' +
                ", discountPromotion='" + discountPromotion + '\'' +
                ", labelContent='" + labelContent + '\'' +
                ", ratingText='" + ratingText + '\'' +
                ", cate='" + cate + '\'' +
                ", voucherInfo='" + voucherInfo + '\'' +
                ", createTime=" + createTime +
                ", createTimestamp=" + createTimestamp +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Integer getResHashCode() {
        return resHashCode;
    }

    public void setResHashCode(Integer resHashCode) {
        this.resHashCode = resHashCode;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSmartTag() {
        return smartTag;
    }

    public void setSmartTag(String smartTag) {
        this.smartTag = smartTag;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDiscountTxt() {
        return discountTxt;
    }

    public void setDiscountTxt(String discountTxt) {
        this.discountTxt = discountTxt;
    }

    public String getDiscountPromotion() {
        return discountPromotion;
    }

    public void setDiscountPromotion(String discountPromotion) {
        this.discountPromotion = discountPromotion;
    }

    public String getLabelContent() {
        return labelContent;
    }

    public void setLabelContent(String labelContent) {
        this.labelContent = labelContent;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getVoucherInfo() {
        return voucherInfo;
    }

    public void setVoucherInfo(String voucherInfo) {
        this.voucherInfo = voucherInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
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
}
