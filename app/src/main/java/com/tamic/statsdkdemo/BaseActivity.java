package com.tamic.statsdkdemo;

import android.app.Activity;

import com.tamic.statinterface.stats.core.TcStatInterface;


/**
 * Created by Tamic on 2016-03-17.
 */
public abstract class BaseActivity extends TamicActivity {

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
