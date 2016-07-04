package com.tamic.statInterface.statsdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.tamic.statInterface.statsdk.constants.StaticsConfig;


/**
 * DeviceUtil
 * Created by Tamic.
 */
public class DeviceUtil {


    /**
     * getAppVersionName
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // Get the package info
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     *  getAppVersionCode
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            // Get the package info
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * getSdkCode
     */
    public static int getSdkCode() {

        return StaticsConfig.SDK_VERSION_CODE;
    }

    /**
     *getMacAddress
     *
     * @param context
     * @return MAC
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info != null ? info.getMacAddress() : "";
    }

    /**
     * getScreenDisplay
     * @param activity
     * @return
     */
    public static DisplayMetrics getScreenDisplay(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * getScreenWidth
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * getScreenHeight
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * etScreenDensity
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }



}
