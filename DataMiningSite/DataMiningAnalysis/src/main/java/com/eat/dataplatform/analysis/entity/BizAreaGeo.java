package com.eat.dataplatform.analysis.entity;

import java.awt.geom.Point2D;

/**
 * @ClassName BizAreaGeo
 * @Description TODO
 * @Author KANGJIAN
 * @Date 2019/4/4 14:12
 **/
public class BizAreaGeo extends Point2D.Double {
    private double x,y,distance;
    private String bizAreaId;

    public String getBizAreaId() {
        return bizAreaId;
    }

    public void setBizAreaId(String bizAreaId) {
        this.bizAreaId = bizAreaId;
    }

    @Override
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x: "+x+" y: "+y+" distance: "+distance +" bizAreaId "+bizAreaId;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public BizAreaGeo(String bizAreaId,double x, double y,double distance) {
        super(x,y);
        this.x = x;
        this.y = y;
        this.bizAreaId = bizAreaId;
        this.distance = distance;
    }

    @Override
    public void setLocation(Point2D p) {
        super.setLocation(p);
    }
}
