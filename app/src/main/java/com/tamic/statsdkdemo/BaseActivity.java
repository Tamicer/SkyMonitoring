package com.tamic.statsdkdemo;

import android.app.Activity;

import com.tamic.statinterface.stats.core.TcStatInterface;


/**
 * Created by Tamic on 2016-03-17.
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();

        TcStatInterface.recordPageStart(BaseActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TcStatInterface.recordPageEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // APP退出
        TcStatInterface.recordAppEnd();

    }


}
