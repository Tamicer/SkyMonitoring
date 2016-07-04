package com.tamic.statInterface.statsdk.http;

import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

/**
 * Created by LIUYONGKUI726 on 2016-05-26.
 */
public class TcHttpClient {

    private static final String BASE_URL = "";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void get(Context context,String url, Header[] headers, RequestParams params,  AsyncHttpResponseHandler responseHandler) {
        client.get(context,getAbsoluteUrl(url),headers, params, responseHandler);
    }

    public static void post(Context context,String url, Header[] headers, RequestParams params, String contentType,
                            AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), headers, params, contentType, responseHandler);
    }

    public static void cancle(String tag, boolean isRunning) {
        client.cancelRequestsByTAG(tag, isRunning);
    }

    public static void cancleAll(boolean isRunning) {
        client.cancelAllRequests(isRunning);
    }

    /**setTimeOut
     * @param time 秒数
     */
    public static void setTimeOut(int time) {
        client.setTimeout(time);
    }
}
