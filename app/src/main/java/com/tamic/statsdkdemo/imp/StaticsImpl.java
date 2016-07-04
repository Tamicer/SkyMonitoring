package com.tamic.statsdkdemo.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.tamic.statInterface.statsdk.core.StaticsListener;

import org.apache.http.util.EncodingUtils;

import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by Tmaic on 2016-04-13.
 */
public class StaticsImpl implements StaticsListener {



    String json;

    private Context context;


    public StaticsImpl(Context context) {
        this.context = context;
    }

    @Override
    public HashMap<String, String> getStatIdMaps() {


        HashMap<String, String> map = null;
        if (getFromAsset("stat_id.json") != null) {
            map = (HashMap<String, String>) JSON.parseObject(getFromAsset("stat_id.json"), HashMap.class);
        }
        return map;
    }

    public String getFromAsset(String fileName){
        String result="";
        try{
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte [] buffer = new byte[length];
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
