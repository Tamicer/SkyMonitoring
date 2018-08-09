package com.tamic.statinterface.stats.bean;

import java.util.List;

/**
 * 对应的event事件集合
 * Created by ZHANGLIANG098 on 2016-03-24.
 */
public class EventList {
    private List<Event> list ;

    public List<Event> getList() {
        return list;
    }

    public void setList(List<Event> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "EventList{" +
                "list=" + list +
                '}';
    }
}
