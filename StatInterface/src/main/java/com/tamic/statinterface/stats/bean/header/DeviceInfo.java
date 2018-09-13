package com.tamic.statinterface.stats.bean.header;

/**
 *
 * @author Tamic
 * @date 2016-03-24
 */
public class DeviceInfo {

    private String deviceId;

    private String deviceMacAddr;

    private String deviceLocale;

    private String devicePlatform = "Android";

    private String deviceOsVersion;

    private String deviceScreen;

    private String deviceDensity;

    private String deviceModel;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceMacAddr() {
        return deviceMacAddr;
    }

    public void setDeviceMacAddr(String deviceMacAddr) {
        this.deviceMacAddr = deviceMacAddr;
    }

    public String getDeviceLocale() {
        return deviceLocale;
    }

    public void setDeviceLocale(String deviceLocale) {
        this.deviceLocale = deviceLocale;
    }

    public String getDevicePlatform() {
        return devicePlatform;
    }

    public void setDevicePlatform(String devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public String getDeviceOsVersion() {
        return deviceOsVersion;
    }

    public void setDeviceOsVersion(String deviceOsVersion) {
        this.deviceOsVersion = deviceOsVersion;
    }

    public String getDeviceScreen() {
        return deviceScreen;
    }

    public void setDeviceScreen(String deviceScreen) {
        this.deviceScreen = deviceScreen;
    }

    public String getDeviceDensity() {
        return deviceDensity;
    }

    public void setDeviceDensity(String deviceDensity) {
        this.deviceDensity = deviceDensity;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }
}
