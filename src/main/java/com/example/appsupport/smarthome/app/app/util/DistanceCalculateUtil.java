package com.example.appsupport.smarthome.app.app.util;

/**
 * 距离计算工具类
 * Created by lixiaoxiao on 17-8-14.
 */
public class DistanceCalculateUtil {

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为M）
     * 参数为String类型
     * @param latFromStr 初始纬度
     * @param lngFromStr 初始经度
     * @param latEndStr 结束纬度
     * @param lngEndStr 结束经度
     * @return
     */
    public static double getDistance(String latFromStr, String lngFromStr, String latEndStr, String lngEndStr) {
        Double latFrom = Double.parseDouble(latFromStr);
        Double lngFrom = Double.parseDouble(lngFromStr);
        Double latEnd = Double.parseDouble(latEndStr);
        Double lngEnd = Double.parseDouble(lngEndStr);

        double radLat1 = rad(latFrom);
        double radLat2 = rad(latEnd);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lngFrom) - rad(lngEnd);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000d) / 10000d;
        return distance*1000;
    }

}
