package com.eat.dataplatform.analysis.utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static com.eat.dataplatform.analysis.utils.SphericalDistance.getDistance;

/**
 * @ClassName IsInCircle
 * @Description 判断当前的点是否在区域范围内，这里考虑两种场景，圆形区域和多边形区域
 * @Author KANGJIAN
 * @Date 2019/4/3 16:11
 **/
public class IsInCircle {

    /**
     * 根据给定的经度纬度和半径画圆，在商圈范围内的店铺信息抓取出来，并将指定半径内的经纬度返回true
     *
     * @param byLatitude
     * @param byLongitude
     * @param byDistance
     * @param latitude
     * @param longitude
     * @return 对指定经纬度在指定范围内的返回True，否则返回False
     */
    public static boolean isIntraArea(Double byLatitude, Double byLongitude, Double byDistance, Double latitude, Double longitude) {
        boolean flag = false;
        //当前指定的点经纬度
        Point2D.Double pointDD = new Point2D.Double(byLatitude, byLongitude);
        //当前店铺的经纬度
        Point2D pointXD = new Point2D.Double(latitude, longitude);
        Double distanceXD = getDistance(pointDD, pointXD);
        if (distanceXD <= byDistance) {
            flag = true;
        }
        return flag;
    }

    /**
     * 判断点是否在多边形内
     *
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return 点在多边形内返回true, 否则返回false
     */
    public static boolean isPtInPoly(Point2D.Double point, ArrayList<Point2D.Double> pts) {
        int N = pts.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        Point2D.Double p1, p2;//neighbour bound vertices
        Point2D.Double p = point; //当前点

        p1 = pts.get(0);//left vertex
        for (int i = 1; i <= N; ++i) {//check all rays
            if (p.equals(p1)) {
                return boundOrVertex;//p is an vertex
            }

            p2 = pts.get(i % N);//right vertex
            if (p.getX() < Math.min(p1.getX(), p2.getX()) || p.getX() > Math.max(p1.getX(), p2.getX())) {//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if (p.getX() > Math.min(p1.getX(), p2.getX()) && p.getX() < Math.max(p1.getX(), p2.getX())) {//ray is crossing over by the algorithm (common part of)
                if (p.getY() <= Math.max(p1.getY(), p2.getY())) {//x is before of ray
                    if (p1.getX() == p2.getX() && p.getY() >= Math.min(p1.getY(), p2.getY())) {//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if (p1.getY() == p2.getY()) {//ray is vertical
                        if (p1.getY() == p.getY()) {//overlies on a vertical ray
                            return boundOrVertex;
                        } else {//before ray
                            ++intersectCount;
                        }
                    } else {//cross point on the left side
                        double xinters = (p.getX() - p1.getX()) * (p2.getY() - p1.getY()) / (p2.getX() - p1.getX()) + p1.getY();//cross point of y
                        if (Math.abs(p.getY() - xinters) < precision) {//overlies on a ray
                            return boundOrVertex;
                        }

                        if (p.getY() < xinters) {//before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {//special case when ray is crossing through the vertex
                if (p.getX() == p2.getX() && p.getY() <= p2.getY()) {//p crossing over p2
                    Point2D p3 = pts.get((i + 1) % N); //next vertex
                    if (p.getX() >= Math.min(p1.getX(), p3.getX()) && p.getX() <= Math.max(p1.getX(), p3.getX())) {//p.getX() lies between p1.getX() & p3.getX()
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        if (intersectCount % 2 == 0) {//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }
    }

    public static void main(String[] args) {
        Point2D.Double point = new Point.Double(116.566298, 40.024899);
        //Point point =new Point(116.404072, 39.916605);

        // 测试一个点是否在多边形内
        ArrayList<Point2D.Double> pts = new ArrayList<>();
        pts.add(new Point.Double(116.16, 39.9));
        pts.add(new Point.Double(117.16, 39.9));
        pts.add(new Point.Double(117.16, 41.9));
        pts.add(new Point.Double(116.16, 41.9));
        // [116.16,39.9],
        //        [117.16,39.9],
        //        [117.16,41.9],
        //        [116.16,41.9]

        if (isPtInPoly(point, pts)) {
            System.out.println("点在多边形内");
        } else {
            System.out.println("点在多边形外");
        }
    }
}
