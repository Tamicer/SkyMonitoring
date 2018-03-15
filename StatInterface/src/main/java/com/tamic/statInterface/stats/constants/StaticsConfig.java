package com.tamic.statinterface.stats.constants;
/**
 * StaticsConfig
 * Created by Tamic on 2016-03-30.
 */
public class StaticsConfig {

    /**
     * constructor
     */
    private StaticsConfig() {

    }

    public static final String SDK_TABLE_NAME = "TcStats_NOTE";

    public final static String APPACTION_INFO = "pageInfo";
    public final static String PAGE_INFO = "appActionInfo";
    public final static String ENVENT_INFO = "enventInfo";
    public final static String CRASH_INFO = "crashInfo";

    

   /* 1分钟，5分钟，10分钟，20分钟，30分钟发送


    /** 统计sdk版本号 */
    public static final int SDK_VERSION_CODE = 2;

    /** 统计sdk版本名称 */
    public static final String SDK_VERSION_NAME = "1.0.3";
    /** 是否是debug版本 */
    public static boolean DEBUG = true;
}
