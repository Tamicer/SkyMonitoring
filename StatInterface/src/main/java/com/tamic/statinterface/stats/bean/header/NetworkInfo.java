package com.tamic.statinterface.stats.bean.header;

/**
 * NetworkInfo
 *
 * @author Tamic
 * @date 2016-03-24
 */
public class NetworkInfo {

    //is wifi
    private Boolean wifi;
    //IP
    private String ipAddr;
    //latitude
    private String latitude;
    //longitude
    private String longitude;


    public NetworkInfo() {
    }


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


}
