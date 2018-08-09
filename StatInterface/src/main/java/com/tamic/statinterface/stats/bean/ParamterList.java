package com.tamic.statinterface.stats.bean;

import java.util.List;

/**
 * 业务对应的key-value集合
 * Created by ZHANGLIANG098 on 2016-03-24.
 */
public class ParamterList {
    private List<KeyValueBean> list ;

    public List<KeyValueBean> getList() {
        return list;
    }

    public void setList(List<KeyValueBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ParamterList{" +
                "list=" + list +
                '}';
    }
}
