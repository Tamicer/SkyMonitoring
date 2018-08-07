package com.tamic.statinterface.stats.model;

import java.util.List;

/**
 * 页面数据统计集合
 * Created by ZHANGLIANG098 on 2016-03-24.
 */
public class PageList {
    private List<Page> list ;

    public List<Page> getList() {
        return list;
    }

    public void setList(List<Page> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageList{" +
                "list=" + list +
                '}';
    }
}
