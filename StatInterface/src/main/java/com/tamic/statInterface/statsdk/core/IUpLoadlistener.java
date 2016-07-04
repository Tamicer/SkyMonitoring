package com.tamic.statInterface.statsdk.core;

/**
 * 上报状态接口
 * Created by LIUYONGKUI726 on 2016-03-25.
 */
public interface IUpLoadlistener {

    void onStart();

    void onUpLoad();

    void onSucess();

    void onFailure();

    void onCancell();
}
