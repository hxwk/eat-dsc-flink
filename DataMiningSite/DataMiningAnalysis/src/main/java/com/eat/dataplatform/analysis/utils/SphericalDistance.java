package com.eat.dataplatform.analysis.utils;

import java.awt.geom.Point2D;

/**
 * @ClassName SphericalDistance
 * @Description 根据经度、纬度计算球面距离 #cwz_茶仔
 * @Author KANGJIAN
 * @Date 2019/4/1 10:35
 **/
public class SphericalDistance {
    private static final double EARTH_RADIUS = 6371393; // 平均半径,单位：m
    /**
     * 通过AB点经纬度获取距离
     * @param pointA A点(经，纬)
     * @param pointB B点(经，纬)
     * @return 距离(单位：米)
     */
    public static double getDistance(Point2D pointA, Point2D pointB) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(pointA.getX()); // A经弧度
        double radiansAY = Math.toRadians(pointA.getY()); // A纬弧度
        double radiansBX = Math.toRadians(pointB.getX()); // B经弧度
        double radiansBY = Math.toRadians(pointB.getY()); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
        double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
        return EARTH_RADIUS * acos; // 最终结果
    }

    public static void main(String[] args) {
        // 国人通信大厦
        Point2D pointDD = new Point2D.Double(113.947646, 22.549036);
        // 西丽火车站 两者之间距离大约2.44公里
        // 通过算法计算出来的距离为2448.2706334835266米 2.44827公里
        Point2D pointXD = new Point2D.Double(113.949921, 22.570952);
        System.out.println(getDistance(pointDD, pointXD));
        System.out.println();
    }
}
