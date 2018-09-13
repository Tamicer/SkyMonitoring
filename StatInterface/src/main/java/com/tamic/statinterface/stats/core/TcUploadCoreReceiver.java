package com.tamic.statinterface.stats.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 *
 * 轮询广播
 * Created by LIUYONGKUI726 on 2016-04-12.
 */
public class TcUploadCoreReceiver extends BroadcastReceiver {

    public static final String REPORT_ACTION = "action.com.pinganfang.base.send_report";

    private static final String TAG = "TcUploadCoreReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "pollSever is started");

        if (context == null || intent == null ) {
            return;
        }

        //发送数据
        TcStatSdk.getInstance(context).send();

    }
}
