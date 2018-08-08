package com.tamic.statsdkdemo;

import android.app.Application;

import com.tamic.statinterface.stats.core.TcStatInterface;
import com.tamic.statinterface.stats.db.DbManager;


/**
 * Created by LIUYONGKUI726 on 2016-04-13.
 */
public class StatAppliation extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DbManager.getInstance().init(this);

        // you app id
        int appId = 21212;
        // assets
//        String fileName = "";
        String fileName = "stat_id.json";
        // init statSdk
        TcStatInterface.initialize(this, appId, "zuber", fileName);
        // set upload url
        TcStatInterface.setUrl("/uploadAction");
        // Set loadPolicy
        TcStatInterface.setUploadPolicy(TcStatInterface.UploadPolicy.UPLOAD_POLICY_REALTIME, TcStatInterface.UPLOAD_TIME_ONE);

        TcStatInterface.recordAppStart();
    }

}
