package com.tamic.statinterface.stats.constants;

/**
 * Created by Tc on 2016-03-25.
 */
public class NetConfig {

    /**
     * constructor
     */
    private NetConfig() {

    }

    /**
     * You Url
     */
    public static String ONLINE_URL ="/uploadAction";

    /**
     * 数据上报Debug Url
     */
    public static final String URL =ONLINE_URL;

    /**
     * 请求超时时间
     */
    public static final int TIME_OUT = 1000 * 50 * 1;

    /** 重新请求时间 */
    public static final int RETRY_TIMES = 3;

    /** HEADERS_KEY */
    public static final String HEADERS_KEY = "data_head";

    /** key*/
    public static final String PARAMS_KEY = "data_body";


}
