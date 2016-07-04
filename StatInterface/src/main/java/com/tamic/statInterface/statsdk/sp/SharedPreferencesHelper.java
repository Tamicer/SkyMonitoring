package com.tamic.statInterface.statsdk.sp;

import android.content.Context;
import android.content.SharedPreferences;


import com.tamic.statInterface.statsdk.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Tamic on 2016-03-24.
 */
public class SharedPreferencesHelper {
    private static SharedPreferencesHelper sInstance;
    private static SharedPreferences sSettings;


    public static synchronized SharedPreferencesHelper getInstance(Context outContext) {
        if (sInstance == null) {
            sInstance = new SharedPreferencesHelper(outContext);
        }
        return sInstance;
    }

    private SharedPreferencesHelper(Context outContext) {
        String OUT_APP_PACKAGENAME = outContext.getPackageName();
        sSettings = outContext.getSharedPreferences(OUT_APP_PACKAGENAME, 0);
    }


    public void putString(String key, String value) {
        synchronized (sSettings) {
            sSettings.edit().putString(key, value).commit();
        }
    }


    public String getString(String key) {
        synchronized (sSettings) {
            return sSettings.getString(key, "");
        }
    }


    public String getString(String key, String defValue) {
        synchronized (sSettings) {
            return sSettings.getString(key, defValue);
        }
    }

    public Double getDouble(String key) {
        String retStr = getString(key, null);
        Double ret = null;
        try {
            ret = Double.parseDouble(retStr);
        } catch (Exception e) {
        }
        return ret;
    }

    public void putBoolean(String key, boolean bool) {
        synchronized (sSettings) {
            sSettings.edit().putBoolean(key, bool).commit();
        }
    }

    public void putInteger(String key, int integer) {
        synchronized (sSettings) {
            sSettings.edit().putInt(key, integer).commit();
        }
    }

    public void putLong(String key, long lon) {
        synchronized (sSettings) {
            sSettings.edit().putLong(key, lon).commit();
        }
    }

    public Boolean getBoolean(String key, boolean defValue) {
        synchronized (sSettings) {
            return sSettings.getBoolean(key, defValue);
        }

    }

    public int getInteger(String key, int defValue) {
        synchronized (sSettings) {
            return sSettings.getInt(key, defValue);
        }
    }

    public long getLong(String key, long defValue) {
        synchronized (sSettings) {
            return sSettings.getLong(key, defValue);
        }
    }


    public void putHashMap(String key, HashMap<String, String> map) {
        JSONObject ret = new JSONObject(map);
        synchronized (sSettings) {
            sSettings.edit().putString(key, ret.toString()).commit();
        }
    }

    public HashMap<String, String> getHashMap(String key) {
        return getHashMapByKey(key);
    }

    public HashMap<String, String> getHashMapByKey(String key) {
        HashMap<String, String> ret = new HashMap<String, String>();

        String mapStr = getString(key, "{}");
        JSONObject mapJson = null;
        try {
            mapJson = new JSONObject(mapStr);
        } catch (Exception e) {
            return ret;
        }

        if (mapJson != null) {
            @SuppressWarnings("unchecked")
            Iterator<String> it = mapJson.keys();
            while (it.hasNext()) {
                String theKey = it.next();
                String theValue = mapJson.optString(theKey);
                ret.put(theKey, theValue);
            }
        }

        return ret;
    }

    public void putArrayList(String key, ArrayList<String> list) {
        JSONArray ret = new JSONArray(list);
        putString(key, ret.toString());
    }

    public ArrayList<String> getArrayList(String key) {
        ArrayList<String> ret = new ArrayList<String>();

        String listStr = getString(key, "{}");
        JSONArray listJson = null;
        try {
            listJson = new JSONArray(listStr);
        } catch (Exception e) {
            return ret;
        }

        if (listJson != null) {
            for (int i = 0; i < listJson.length(); i++) {
                String temp = listJson.optString(i);
                ret.add(temp);
            }
        }

        return ret;
    }


    public void removeByKey(String key) {
        synchronized (sSettings) {
            sSettings.edit().remove(key).commit();
        }
    }

    public void putJsonArray(String key, JSONArray value) {
        putString(key, value.toString());
    }

    public JSONArray getJsonArray(String key) {
        JSONArray ret = null;
        String jsonArrayStr = getString(key);
        try {
            ret = new JSONArray(jsonArrayStr);
        } catch (JSONException e) {
            ret = null;
        }
        return ret;
    }

    public void putObject(String key, Object obj) {
        String toSave = JsonUtil.toJSONString(obj);
        putString(key, toSave);
    }

    public <T> T getObject(String key, Class<T> cla) {
        String temp = getString(key);

        if (temp == null || temp.trim().length() == 0) {
            return null;
        }

        return JsonUtil.parseObject(temp, cla);
    }
}
