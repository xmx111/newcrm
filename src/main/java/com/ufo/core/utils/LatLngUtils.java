/**
 * 
 */
package com.ufo.core.utils;

import com.ufo.core.dto.LatLng;

/**
 * 经纬度实用类
 * 
 * @author jimmy
 * 
 */
public class LatLngUtils {
    private final static double EARTH_RADIUS = 6378137.0;

    /**
     * 计算2点的距离 返回单位KM
     * 
     * @param source
     * @param target
     * @return
     */
    public static double gps2m(LatLng source, LatLng target) {
        if (target.getLat() == null || target.getLng() == null) {
            return Double.MAX_VALUE;
        }
        double radLat1 = source.getLat() == null ? 0d : (source.getLat() * Math.PI / 180.0);
        double radLat2 = target.getLat() == null ? 0d : (target.getLat() * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        Double lng1 = source.getLng() == null ? 0 : source.getLng();
        Double lng = target.getLng() == null ? 0 : target.getLng();
        double b = (lng1 - lng) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
