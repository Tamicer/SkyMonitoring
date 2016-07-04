package com.tamic.statInterface.statsdk.model.header;

/**
 * Created by Tamic on 2016-03-24.
 * AppInfo
 */
public class AppInfo {

    /**
     * App ID
     */
    private String app_id;

    /**
     * App版本
     */
    private String  app_version;

    /**
     * 统计sdk版本号
     */
    private int sdk_version;

    /**
     * app channel
     */
    private String channel;

    public AppInfo() {
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public int getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(int sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


}
