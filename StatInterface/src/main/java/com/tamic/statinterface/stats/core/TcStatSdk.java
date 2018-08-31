/*
 *    Copyright (C) 2016 Tamic
 *
 *    link :https://github.com/Tamicer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.tamic.statinterface.stats.core;

import android.content.Context;

import com.tamic.statinterface.stats.bean.body.ViewPath;

import java.util.HashMap;


/**
 * StatSdk
 * Created by Tamic on 2016-04-05.
 * {https://github.com/Tamicer/SkyMonitoring}
 */
public class TcStatSdk {

    /** context */
    private Context mContext;
    /** Instance */
    private static TcStatSdk sInstance;

    private static final String TAG = "TcStaInterface::StatSdk";

    private TcStaticsManager staticsManager;

    /**
     * getInstance
     *
     * @param aContext
     *            context
     * @return 返回 TcStaticsManager
     */
    protected static synchronized TcStatSdk getInstance(Context aContext) {
        if (sInstance == null) {
            sInstance = new TcStatSdk(aContext,  new TcStaticsManagerImpl(aContext));
        }
        return sInstance;
    }

    /**
     * constructor
     *
     * @param aContext
     *            context
     */
    private TcStatSdk(Context aContext, TcStaticsManager aStaticsManager) {
        mContext = aContext;
        staticsManager = aStaticsManager;

    }

    protected void init(int appId, String channel, String fileName) {

        staticsManager.onInit(appId, channel, fileName);

    }

    protected void send() {

        staticsManager.onSend();
    }

    protected void store() {

        staticsManager.onStore();

    }

    protected void upLoad() {

        staticsManager.onSend();
    }

    /**
     * release
     */
    protected void release() {

        staticsManager.onRelease();

    }

    protected void recordPageEnd() {

        staticsManager.onRrecordPageEnd();

    }

    protected void recordAppStart() {

        staticsManager.onRecordAppStart();

    }

    protected void recordAppEnd() {

        staticsManager.onRrecordAppEnd();

    }

    protected void recordPageStart(Context context) {

        staticsManager.onRecordPageStart(context);

    }

    protected void setPageParameter(String k, String v) {

        staticsManager.onPageParameter(k, v);

    }

    protected void initEvent(String envntName, String value) {

        staticsManager.onInitEvent(envntName,value);

    }
    protected void initEvent(ViewPath viewPath) {

        staticsManager.onInitEvent(viewPath);

    }

    protected void setEventParameter(String k, String v) {

        staticsManager.onEventParameter(k, v);

    }

    protected void onEvent(String eventName, HashMap<String, String> parameters) {
        this.staticsManager.onEvent(eventName, parameters);
    }

    protected void initPage(String pageId, String referPageId) {

        staticsManager.onInitPage(pageId, referPageId);

    }

}
