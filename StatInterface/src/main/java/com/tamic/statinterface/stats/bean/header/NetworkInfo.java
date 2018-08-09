package com.tamic.statinterface.stats.bean.header;

/**
 * NetworkInfo
 * Created by Tamic on 2016-03-24.
 */
public class NetworkInfo {

    //is wifi
    private Boolean wifi;
    //IP
    private String ip_addr;
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

    public String getIp_addr() {
        return ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
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
