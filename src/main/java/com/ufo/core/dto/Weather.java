package com.ufo.core.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ufo.core.utils.DateUtils;

/** 
* 类名称：Weather 
* 类描述：天气实体 
* 
* 
* 创建人：Duzj
* 创建时间：2012-3-27 下午2:57:18 
* @version 
* 
*/
public class Weather {
    private static String imgPath = "http://www.weather.com.cn/m/i/icon_weather/42x30/n%s.gif";
    /**
     * 高温
     */
    private String highTemp;
    /**
     * 低温
     */
    private String lowTemp;
    /**
     * 天气
     */
    private String weather;
    /**
     * 天气图片1
     */
    private String weatherPic1;
    /**
     * 天气图片2
     */
    private String weatherPic2;

    /**
     * 天气图片名称
     */
    private String weatherPicName1;
    /**
     * 天气图片名称
     */
    private String weatherPicName2;

    /**
     * 风向
     */
    private String wind;

    /**
     * 星期
     */
    private String week;

    /** 
     * 日期
    *date 
    */
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSimpleDate() {
        if (StringUtils.isNotBlank(date)) {
            Date d = DateUtils.parseToDate(date);
            return DateUtils.format(d, "MM月dd日");
        } else {
            return "";
        }
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherPic1() {
        return weatherPic1;
    }

    public String getWeatherPic1Url() {
        return String.format(imgPath, weatherPic1);
    }

    public void setWeatherPic1(String weatherPic1) {
        this.weatherPic1 = weatherPic1;
    }

    public String getWeatherPic2() {
        return weatherPic2;
    }

    public String getWeatherPic2Url() {
        return String.format(imgPath, weatherPic2);
    }

    public void setWeatherPic2(String weatherPic2) {
        this.weatherPic2 = weatherPic2;
    }

    public String getWeatherPicName1() {
        return weatherPicName1;
    }

    public void setWeatherPicName1(String weatherPicName1) {
        this.weatherPicName1 = weatherPicName1;
    }

    public String getWeatherPicName2() {
        return weatherPicName2;
    }

    public void setWeatherPicName2(String weatherPicName2) {
        this.weatherPicName2 = weatherPicName2;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

}
