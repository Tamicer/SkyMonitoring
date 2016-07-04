package com.tamic.statInterface.statsdk.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.tamic.statInterface.statsdk.model.header.AppInfo;
import com.tamic.statInterface.statsdk.model.header.DeviceInfo;
import com.tamic.statInterface.statsdk.model.header.HeaderInfo;
import com.tamic.statInterface.statsdk.model.header.NetworkInfo;
import com.tamic.statInterface.statsdk.util.DeviceUtil;
import com.tamic.statInterface.statsdk.util.NetworkUtil;

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

    private static boolean isInit ;

    private static int appId;

    private static String mChannel;


    protected static boolean initHeader(Context context, int AppId, String channel) {


        if (headerInfo == null) {
            appId = AppId;
            mChannel= channel;
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

    /** get AppInfo
     * @param context
     */
    private static AppInfo getAppInfo(Context context) {

        if (appinfo != null) {
            return appinfo;
        }

        appinfo = new AppInfo();
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        String appLabel = "";

        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            appinfo.setApp_id(String.valueOf(appId));


            if (info != null) {
                appinfo.setApp_version(info.versionName);
            }
            appinfo.setApp_id(String.valueOf(appId));
            appinfo.setChannel(mChannel);
            appinfo.setSdk_version(DeviceUtil.getSdkCode());

            return appinfo;
        }catch (PackageManager.NameNotFoundException e1) {
                e1.printStackTrace();
            return null;
        }
    }

    /** get Device Info
     * @param context
     */
    private static DeviceInfo getDeviceInfo(Context context) {

        if (deviceinfo != null) {
            return deviceinfo;
        }
        deviceinfo = new DeviceInfo();

        // 设备ID，

        mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyMgr != null) {

            deviceinfo.setDevice_id(mTelephonyMgr.getDeviceId());
            // android Imei
            deviceinfo.setImei(mTelephonyMgr.getDeviceId());
        }

        // AndroidId
        try {
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            deviceinfo.setAndroid_id(androidId);
            if (TextUtils.isEmpty(deviceinfo.getImei())) {
                deviceinfo.setImei(androidId);
            }
        } catch (Exception e) {
            // do nothing. not use the data
        }

        deviceinfo.setMac(DeviceUtil.getMacAddress(context));

        deviceinfo.setModel(android.os.Build.MODEL);

        deviceinfo.setOs("Android");

        deviceinfo.setOs_version(android.os.Build.VERSION.RELEASE);

        // UniqueId
        String openId = deviceinfo.getDevice_id();
        if (openId == null || openId.trim().length() == 0) {
            openId = deviceinfo.getAndroid_id();
        }
        if (openId == null || openId.trim().length() == 0) {
            openId = deviceinfo.getMac();
        }

        deviceinfo.setOpenudid(openId);
        deviceinfo.setResolution(DeviceUtil.getScreenWidth(context) + "*" + DeviceUtil.getScreenHeight(context));
        deviceinfo.setDensity(String.valueOf(DeviceUtil.getScreenDensity(context)));
        deviceinfo.setLocale(Locale.getDefault().getLanguage());

        return deviceinfo;

    }

    /**  get NetWork Info
     * @param context
     */
    protected static NetworkInfo getNetWorkInfo(Context context) {

        if (networkinfo == null) {

            networkinfo = new NetworkInfo();
        }
        networkinfo.setIp_addr(NetworkUtil.getLocalIpAddress());

        networkinfo.setWifi_ind(NetworkUtil.isWifi(context));

        if(mTelephonyMgr.getSimState() == TelephonyManager.SIM_STATE_READY) {
            networkinfo.setCarrier(mTelephonyMgr.getSimOperatorName());
        }

        Location location = getLocation(context);
        if(location != null){
            networkinfo.setLatitude(String.valueOf(location.getLatitude()));
            networkinfo.setLongitude(String.valueOf(location.getLongitude()));
        }

        return networkinfo;
    }

    /**
     * 获取Location
     * @param context
     * @return
     */
    private static Location getLocation(Context context) {
        //获取地理位置管理器
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);
        String locationProvider;
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {

            locationProvider = LocationManager.GPS_PROVIDER;;
        }
        return locationManager.getLastKnownLocation(locationProvider);
    }

}
