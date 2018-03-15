package com.tamic.statinterface.stats.model;

import java.util.List;

/**
 * 对应的event事件
 * Created by ZHANGLIANG098 on 2016-03-24.
 */
public class Event {
    private String page_id ;
    private String referer_page_id ;
    private String uid ;
    private String city_id ;
    private String event_name ;
    private String action_time ;
    private List<KeyValueBean> parameter ;

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public String getReferer_page_id() {
        return referer_page_id;
    }

    public void setReferer_page_id(String referer_page_id) {
        this.referer_page_id = referer_page_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getAction_time() {
        return action_time;
    }

    public void setAction_time(String action_time) {
        this.action_time = action_time;
    }

    public List<KeyValueBean> getParameter() {
        return parameter;
    }

    public void setParameter(List<KeyValueBean> parameter) {
        this.parameter = parameter;
    }
}
