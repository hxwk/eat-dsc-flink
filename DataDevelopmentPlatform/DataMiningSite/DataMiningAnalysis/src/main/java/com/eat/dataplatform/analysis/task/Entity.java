package com.eat.dataplatform.analysis.task;

import java.io.Serializable;

public class Entity implements Serializable {
    private String code;
    private String simpleName;
    private String dateTime;
    private String qianshoupanjia;
    private String kaipanjia;
    private String hignestPrice;
    private String lowestPrice;

    @Override
    public String toString() {
        return "Entity{" +
                "code='" + code + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", qianshoupanjia='" + qianshoupanjia + '\'' +
                ", kaipanjia='" + kaipanjia + '\'' +
                ", hignestPrice='" + hignestPrice + '\'' +
                ", lowestPrice='" + lowestPrice + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getQianshoupanjia() {
        return qianshoupanjia;
    }

    public void setQianshoupanjia(String qianshoupanjia) {
        this.qianshoupanjia = qianshoupanjia;
    }

    public String getKaipanjia() {
        return kaipanjia;
    }

    public void setKaipanjia(String kaipanjia) {
        this.kaipanjia = kaipanjia;
    }

    public String getHignestPrice() {
        return hignestPrice;
    }

    public void setHignestPrice(String hignestPrice) {
        this.hignestPrice = hignestPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }
}
