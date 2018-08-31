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

import com.tamic.statinterface.stats.bean.body.DataBlock;
import com.tamic.statinterface.stats.constants.NetConfig;
import com.tamic.statinterface.stats.db.helper.StaticsAgent;
import com.tamic.statinterface.stats.http.TcHttpClient;
import com.tamic.statinterface.stats.service.Platform;
import com.tamic.statinterface.stats.util.LogUtil;
import com.tamic.statinterface.stats.util.NetworkUtil;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Stat UpLoadManager
 * Created by Tamic on 2016-03-24.
 */
public class TcUpLoadManager implements IUpLoadlistener {


    /**
     * context
     */
    private Context mContext;
    /**
     * http client
     */
    private TcHttpClient mHttpClient;
    /**
     * UpLoadManager
     */
    private static TcUpLoadManager sInstance;

    private Boolean isRunning = false;

    private AtomicReference<TcNetEngine> atomic;

    private TcNetEngine netEngine;
    /**
     * Log TAG
     */
    private static final String TAG = TcUpLoadManager.class.getSimpleName();
    private static final boolean debug = true;

    /**
     * getInstance
     *
     * @param aContext context
     * @return UpLoadManager
     */
    public static synchronized TcUpLoadManager getInstance(Context aContext) {
        if (sInstance == null) {
            sInstance = new TcUpLoadManager(aContext);
        }
        return sInstance;
    }

    /**
     * constructor
     *
     * @param aContext context
     */
    private TcUpLoadManager(Context aContext) {
        mContext = aContext;
        init();
    }

    /**
     * init
     */
    private void init() {
        mHttpClient = getHttpclient();
        atomic = new AtomicReference<>();
        netEngine = new TcNetEngine(mContext, this);
    }


    /**
     * report
     */
    public void report(DataBlock dataBlock) {
        LogUtil.i(debug, TAG, "【TcUpLoadManager.report()】【dataBlock=" + dataBlock + "】");
        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            return;
        }

        if (dataBlock == null) {
            return;
        }
        //netEngine.setHttpClient(getHttpclient());
        atomic.set(netEngine);
        atomic.getAndSet(netEngine).start(dataBlock);
    }

    /**
     * cancle
     */
    public void cancle() {

        if (atomic.get() != null) {
            atomic.get().cancel();

        }

    }


    /**
     * get http client
     *
     * @return http client
     */
    public TcHttpClient getHttpclient() {
        if (mHttpClient == null) {
            // HttpClient
            mHttpClient = new TcHttpClient();
            mHttpClient.setTimeOut(NetConfig.TIME_OUT);
        }
        return mHttpClient;

    }


    @Override
    public void onStart() {

        isRunning = true;
    }

    @Override
    public void onUpLoad() {

        isRunning = true;
    }

    @Override
    public void onSucess() {

        isRunning = false;
        // delete data
        Platform.get().execute(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(debug, TAG, "【TcUpLoadManager.run()】【start】");
                try {
                    StaticsAgent.deleteData();
                    LogUtil.i(debug, TAG, "【TcUpLoadManager.run()】【end】");
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.d(TAG, "【TcUpLoadManager.run(Exception)】【e=" + e + "】");
                }
            }
        });

    }

    @Override
    public void onFailure() {

        isRunning = false;

    }

    @Override
    public void onCancell() {

        isRunning = false;
    }
}
