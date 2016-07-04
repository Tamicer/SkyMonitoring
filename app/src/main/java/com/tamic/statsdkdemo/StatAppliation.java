package com.tamic.statsdkdemo;
import android.app.Application;

import com.tamic.statInterface.statsdk.core.TcStatInterface;

/**
 * Created by LIUYONGKUI726 on 2016-04-13.
 */
public class StatAppliation extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // you app id
        int appId = 21212;
        // assets
        String fileName = "stat_id.json";
        String url = "http://www.baidu.com";
        // init statSdk
        TcStatInterface.initialize(this, appId, "you app chanel", fileName);
        TcStatInterface.setUrl(url);
        TcStatInterface.setUploadPolicy(TcStatInterface.UploadPolicy.UPLOAD_POLICY_DEVELOPMENT, TcStatInterface.UPLOAD_TIME_ONE);
    }


}
