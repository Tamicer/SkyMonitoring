package com.tamic.statinterface.stats.bean.body;

import java.util.List;

/**
 * Page
 * Created by Tamic on 2016-03-24.
 */
public class Page {
    private String page_id;
    private String referer_page_id ;
    private String page_start_time ;
    private String page_end_time ;
    private String city_id ;
    private String uid ;
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

    public String getPage_start_time() {
        return page_start_time;
    }

    public void setPage_start_time(String page_start_time) {
        this.page_start_time = page_start_time;
    }

    public String getPage_end_time() {
        return page_end_time;
    }

    public void setPage_end_time(String page_end_time) {
        this.page_end_time = page_end_time;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public List<KeyValueBean> getParameter() {
        return parameter;
    }

    public void setParameter(List<KeyValueBean> parameter) {
        this.parameter = parameter;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page_id='" + page_id + '\'' +
                ", referer_page_id='" + referer_page_id + '\'' +
                ", page_start_time='" + page_start_time + '\'' +
                ", page_end_time='" + page_end_time + '\'' +
                ", city_id='" + city_id + '\'' +
                ", uid='" + uid + '\'' +
                ", parameter=" + parameter +
                '}';
    }
}
