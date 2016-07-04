package com.tamic.statInterface.statsdk.core;

import android.content.Context;

/**
 * Created by Tamic on 2016-04-05.
 * StaticsManager
 */
public interface TcStaticsManager {

     boolean onInit(int appId, String channel, String fileName);

     void onSend();

     void onStore();

     void onRelease();

     void onRecordAppStart();

     void onRrecordPageEnd();

     void onRecordPageStart(Context context);

     void onRrecordAppEnd();

     void onInitPage(String... strings);

     void onPageParameter(String... strings);

     void onInitEvent(String eventName);

     void onEventParameter(String... strings);


}
