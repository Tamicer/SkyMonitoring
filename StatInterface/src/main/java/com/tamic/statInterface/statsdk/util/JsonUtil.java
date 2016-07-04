package com.tamic.statInterface.statsdk.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

import java.lang.reflect.Type;
import java.util.List;

/**
 * JsonUtil
 * Created by Tamic
 */
public class JsonUtil {

    /**parseObject
     * @param jsonStr
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonStr, Class<T> entityClass) {
        T ret = null;

        try {
            ret = JSON.parseObject(jsonStr, entityClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static <T> T parseObject(String jsonStr, Type type) {
        T obj = null;
        try {
            obj = JSON.parseObject(jsonStr, type, Feature.AutoCloseSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    public static <T> T parseObject(String jsonStr, TypeReference<T> tf) {
        T obj = null;
        try {
            obj = JSON.parseObject(jsonStr, tf, Feature.AutoCloseSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * parseList
     * @param jsonStr
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(String jsonStr, Class<T> entityClass) {
        List<T> ret = null;

        try {
            ret = JSON.parseArray(jsonStr, entityClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static String toJSONString(Object obj) {
        String ret = null;

        try {
            ret = JSON.toJSONString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
