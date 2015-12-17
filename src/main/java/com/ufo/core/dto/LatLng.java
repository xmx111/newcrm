/**
 * 
 */
package com.ufo.core.dto;

/**
 * 经纬度DTO
 * 
 * @author jimmy
 * 
 */
public class LatLng {
    //经度
    private Double lng;
    //纬度
    private Double lat;

    public LatLng() {
    }

    public LatLng(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}
