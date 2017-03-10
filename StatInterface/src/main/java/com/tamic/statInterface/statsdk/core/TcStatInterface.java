package com.tamic.statInterface.statsdk.core;

import android.content.Context;

import com.tamic.statInterface.statsdk.constants.NetConfig;

import java.util.HashMap;


/**
 * StatInterface
 * Created by tamic on 2016-04-06.
 */
public final class TcStatInterface {

    /**实时发送*/
    protected static final int UPLOAD_POLICY_REALTIME  = 0;
    /**只在wifi下*/
    protected static final int UPLOAD_POLICY_WIFI_ONLY = 2;
    /**批量上报 达到一定次数*/
    protected static final int UPLOAD_POLICY_BATCH = 3;
    /**时间间隔*/
    protected static final int UPLOAD_POLICY_INTERVAL = 4;
    /**开发者debug模式 调用就可以发送*/
    protected static final int UPLOAD_POLICY_DEVELOPMENT = 5;
    /**每次启动 发送上次产生的数据*/
    protected static final int UPLOAD_POLICY_WHILE_INITIALIZE = 6;

    /**
     * 上报策略模式
     */
    public enum UploadPolicy {
        /**实时发送*/
        UPLOAD_POLICY_REALTIME,
        /**只在wifi下*/
        UPLOAD_POLICY_WIFI_ONLY,
        /**批量上报 达到一定次数*/
        UPLOAD_POLICY_BATCH,
        /**时间间隔*/
        UPLOAD_POLICY_INTERVA,
        /**开发者debug模式 调用就可以发送*/
        UPLOAD_POLICY_DEVELOPMENT,
        /**每次启动 发送上次产生的数据*/
        UPLOAD_POLICY_WHILE_INITIALIZE
    }

    /**实时发送*/
    public static final int UPLOAD_INTERVAL_REALTIME  = 0;
    /**1分钟*/
    public static final int UPLOAD_TIME_ONE = 1;
    /**5分钟*/
    public static final int UPLOAD_TIME_THREAD = 3;
    /**10分钟*/
    public static final int UPLOAD_TIME_TEN = 10;
    /**20分钟*/
    public static final int UPLOAD_TIME_TWENTY = 20;
    /**30分钟发送*/
    public static final int UPLOAD_TIME_THIRTY = 30;
    /**
     * 上报策略
     */
    protected static UploadPolicy uploadPolicy;

    private static int intervalRealtime = UPLOAD_TIME_THREAD;

    private static Context context;
    /**
     * private constructor
     */
    private TcStatInterface() {

    }

    /**
     * initialize
     * @param aContext
     * @param appId
     * @param channel
     */
    public static void initialize(Context aContext, int appId, String channel, String fileName) {

        context = aContext;

        TcStatSdk.getInstance(aContext).init(appId, channel, fileName);

    }

    /**
     * 设置策略模式
     * @param policy
     *     策略模式（实时模式下间隔时间无效）
     *     目前默认为UPLOAD_POLICY_INTERVA模式
     * @param time
     *     时间间隔（1 5 10 20 30分钟）
     */
    public static void setUploadPolicy(UploadPolicy policy, int time) {

        if (policy == null) {
            uploadPolicy = UploadPolicy.UPLOAD_POLICY_INTERVA;
            return;
        }

        if (time > 0 || time <= 60) {
            intervalRealtime = time;
        }
        uploadPolicy = policy;

    }

    /**
     * getIntervalRealtime
     * @return  intervalRealtime
     */
    public static int getIntervalRealtime() {
        return intervalRealtime;
    }

    public static void setIntervalRealtime(int intervalRealtime) {
        TcStatInterface.intervalRealtime = intervalRealtime;
    }

    /** setUrl
     * @param url
     */
    public static void setUrl(String url) {
        NetConfig.ONLINE_URL = url;
    }

    /**
     * record Page Start
     * */
    public static void recordPageStart(Context context) {

        TcStatSdk.getInstance(context).recordPageStart(context);

    }
    /**
     * record Page End
     */
    public static void recordPageEnd() {

        TcStatSdk.getInstance(context).recordPageEnd();

    }

    /**
     * record App Start
     */
    public static void recordAppStart() {

        TcStatSdk.getInstance(context).recordAppStart();


    }

    /**
     * 关闭APP
     */
    public static void recordAppEnd() {

        TcStatSdk.getInstance(context).recordAppEnd();


        TcStatSdk.getInstance(context).release();
    }

    /**
     * 上报数据
     * 非Debug模式无法直接调用，请先设置为UPLOAD_POLICY_DEVELOPMENT
     */
    protected static void report() {


       TcStatSdk.getInstance(context).send();

    }

    /**
     * 上报数据
     * 非Debug模式无法直接调用，请先设置为UPLOAD_POLICY_DEVELOPMENT
     */
    public static void reportData() {


        if (uploadPolicy != UploadPolicy.UPLOAD_POLICY_DEVELOPMENT) {

            throw new RuntimeException("call reportData(), you must will UploadPolicy set : UPLOAD_POLICY_DEVELOPMENT!");
        }

        report();

    }


    /**
     * 加入page参数
     * @param k 业务名字
     * @param v 对应值
     */
    public static void onPageParameter(String k,String v) {

        TcStatSdk.getInstance(context).setPageParameter(k, v);

    }

    /**
     * 初始化Event
     */
    public static void initEvent(String eventName) {

        TcStatSdk.getInstance(context).initEvent(eventName);

    }


    /**
     * 加入自定义envent
     * @param k 业务名字
     * @param v 对应值
     */
    public static void onEventParameter(String k,String v) {

        TcStatSdk.getInstance(context).setEventParameter(k, v);

    }

    /**
     * onEvent
     */
    public static void onEvent(String eventName, String k,String v) {
        initEvent(eventName);
        onEventParameter(k, v);

    }

    public static void onEvent(String eventName, HashMap<String, String> parameters) {
        TcStatSdk.getInstance(context).onEvent(eventName, parameters);
    }

}
