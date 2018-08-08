package com.tamic.statinterface.stats.db;

import android.content.Context;


/**
 * 版权:上海屋聚 版权所有
 * author: yandeqing
 * version: 3.0.0
 * date:2018/8/8 18:10
 * 备注:
 */
public class DbManager {
    private static DbManager mDbManager = new DbManager();

    private DbManager() {
    }

    public static DbManager getInstance() {
        return mDbManager;
    }


    public void init(Context context){
        //        使用自定义配置
    }


}
