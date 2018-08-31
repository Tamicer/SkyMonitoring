package com.tamic.statinterface.stats.core;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.tamic.statinterface.stats.bean.header.AppInfo;
import com.tamic.statinterface.stats.bean.header.DeviceInfo;
import com.tamic.statinterface.stats.bean.header.HeaderInfo;
import com.tamic.statinterface.stats.bean.header.NetworkInfo;
import com.tamic.statinterface.stats.util.DeviceUtil;
import com.tamic.statinterface.stats.util.NetworkUtil;

import java.util.List;
import java.util.Locale;

/**
 * 头部句柄  初始化Header信息
 * Created by LIUYONGKUI726 on 2016-04-05.
 */
public class TcHeadrHandle {

    private static AppInfo appinfo;

    private static DeviceInfo deviceinfo;

    private static NetworkInfo networkinfo;

    private static TelephonyManager mTelephonyMgr;

    private static HeaderInfo headerInfo;

    private static boolean isInit;

    private static int appId;

    private static String mChannel;


    protected static boolean initHeader(Context context, int appid, String channel) {


        if (headerInfo == null) {
            appId = appid;
            mChannel = channel;
            networkinfo = new NetworkInfo();
            headerInfo = new HeaderInfo(getAppInfo(context), getDeviceInfo(context), getNetWorkInfo(context));
            isInit = true;
        }

        return isInit;

    }

    public static boolean isInit() {
        return isInit;
    }


    protected static HeaderInfo getHeader(Context context) {


        if (headerInfo == null) {
            return new HeaderInfo(getAppInfo(context), getDeviceInfo(context), getNetWorkInfo(context));
        }

        return headerInfo;

    }

    /**
     * get AppInfo
     *
     * @param context
     */
    private static AppInfo getAppInfo(Context context) {

        if (appinfo != null) {
            return appinfo;
        }
        appinfo = new AppInfo();
        appinfo.setAppId(String.valueOf(appId));
        appinfo.setAppChannel(mChannel);
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            if (info != null) {
                appinfo.setAppVersion(info.versionName);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return appinfo;
    }

    /**
     * get Device Info
     *
     * @param context
     */
    private static DeviceInfo getDeviceInfo(Context context) {
        if (deviceinfo != null) {
            return deviceinfo;
        }
        deviceinfo = new DeviceInfo();
        // AndroidId
        deviceinfo.setDeviceId(DeviceUtil.getDeviceId(context));
        deviceinfo.setDeviceMacAddr(DeviceUtil.getMacAddress(context));
        deviceinfo.setDeviceModel(Build.MODEL);
        deviceinfo.setDevicePlatform("Android");
        deviceinfo.setDeviceOsVersion(Build.VERSION.RELEASE);
        deviceinfo.setDeviceScreen(DeviceUtil.getScreenWidth(context) + "*" + DeviceUtil.getScreenHeight(context));
        deviceinfo.setDeviceDensity(String.valueOf(DeviceUtil.getScreenDensity(context)));
        deviceinfo.setDeviceLocale(Locale.getDefault().getLanguage());
        return deviceinfo;

    }

    /**
     * get NetWork Info
     *
     * @param context
     */
    protected static NetworkInfo getNetWorkInfo(Context context) {

        if (networkinfo == null) {

            networkinfo = new NetworkInfo();
        }
        networkinfo.setIpAddr(NetworkUtil.getLocalIpAddress());

        networkinfo.setWifi(NetworkUtil.isWifi(context));

        return networkinfo;
    }


}
