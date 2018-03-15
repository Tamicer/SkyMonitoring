package com.tamic.statinterface.stats.db.helper;

import android.content.Context;
import android.text.TextUtils;

import com.tamic.statinterface.stats.core.StaticsListener;
import com.tamic.statinterface.stats.model.AppAction;
import com.tamic.statinterface.stats.model.Event;
import com.tamic.statinterface.stats.model.KeyValueBean;
import com.tamic.statinterface.stats.model.Page;
import com.tamic.statinterface.stats.sp.SharedPreferencesHelper;
import com.tamic.statinterface.stats.util.DateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Tamic on 2016-04-11.
 */
public class DataConstruct {

    private static Event event = null;
    private static CopyOnWriteArrayList<KeyValueBean> parameter = new CopyOnWriteArrayList<>();
    private static ConcurrentSkipListMap<String, Event> events = new ConcurrentSkipListMap<>();
    private static Page page = null;
    private static ArrayList<KeyValueBean> pageParameter = new ArrayList<>();
    private static AppAction appAction = null;
    private static StaticsListener staticsListener;
    private static String pageId;
    private static String referPageId;
    private static String REFERPAGE_ID = "referPage_Id";

    private DataConstruct() {
    }

    /**
     * initEvent
     *
     * @param event_name
     */
    public static void initEvent(String event_name) {
        initEvent(event_name, null);
    }

    /**
     * initEvent 带参数的事件
     * @param eventName      事件ID
     * @param parameters     事件参数（k-v）
     */
    public static synchronized void initEvent(String eventName, Map<String, String> parameters) {

        if (TextUtils.isEmpty(eventName)) {
            throw new RuntimeException("you must set eventName!");
        }

        Event event = new Event();
        event.setPage_id(pageId);
        event.setReferer_page_id(referPageId);
        event.setEvent_name(eventName);
        event.setAction_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));

        if (parameters !=null && !parameters.isEmpty() && parameters.size() > 0) {
            CopyOnWriteArrayList<KeyValueBean> parameter = new CopyOnWriteArrayList<>();

            Iterator<String> keys = parameters.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                if (parameters.get(key) != null) {
                    parameter.add(new KeyValueBean(key, parameters.get(key)));
                }
            }
            if (!parameter.isEmpty()) {
                event.setParameter(parameter);
            }
        }
        storeEvent(event);
    }

    /**
     * onEvent parameter 自定义业务类型统计 k-v
     *
     * @param businessName
     * @param businessValue
     */
    public static void onEvent(String businessName, String businessValue) {
        if (TextUtils.isEmpty(businessName) || TextUtils.isEmpty(businessValue)) {
            return;
        }
        parameter.add(new KeyValueBean(businessName, businessValue));
        if (event == null || event.getEvent_name().isEmpty()) {
            throw new RuntimeException("you must call initEvent before onEvent!");
        }
        event.setParameter(parameter);
        events.put(event.getEvent_name(), event);
        parameter.clear();
    }



    /**
     * initPage 
     *
     * @param eventInterface
     */
    public static void initPage(Context context, StaticsListener eventInterface, String page_Id, String referPage_Id) {
        staticsListener = eventInterface;
        pageId = page_Id;
        if (TextUtils.isEmpty(referPage_Id)) {
            referPageId = getReferPageId(context);
        } else {
            referPageId = referPage_Id;
        }
        recardPageId(context, page_Id);
        page = new Page();
        page.setPage_start_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        page.setReferer_page_id(referPageId);
        page.setPage_id(pageId);
        pageParameter.clear();
    }


    public static void initPageParameter(String name, String value) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
            return;
        }
        if (page == null) {
            return;
        }
        pageParameter.add(new KeyValueBean(name, value));
        page.setParameter(pageParameter);
    }


    /**
     * storePage
     */
    public static void storePage() {
        if (page == null) {
            throw new RuntimeException("you must init before storePage");
        }
        page.setPage_end_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        StaticsAgent.storeObject(page);
    }


    /**
     * storeAppAction
     *
     * @param type 1 app打开  2app关闭 3唤醒
     */
    public static void storeAppAction(String type) {
        appAction = new AppAction();
        appAction.setAction_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        appAction.setApp_action_type(type);
        StaticsAgent.storeObject(appAction);
        appAction = null;
    }

    /**
     * storeEvent
     *you need store  to call this
     */
    private static synchronized void storeEvent(Event event) {
        if (event == null) {
            return;
        }
        StaticsAgent.storeObject(event);
    }

    /**
     * storeEvent
     * Activity destory call
     */
    public static synchronized void storeEvents() {
        if (events == null || events.size() == 0) {
            return;
        }
        if (events.size() > 0) {
            Iterator<String> keys = events.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                StaticsAgent.storeObject(events.get(key));
            }
        }
        event = null;
        events.clear();

    }



    /**
     deleteData
     */
    public static void deleteData() {
        StaticsAgent.deleteData();
    }

    /**
     * recardPageId
     *
     * @param context
     * @param page_Id
     */
    private static void recardPageId(Context context, String page_Id) {
        SharedPreferencesHelper.getInstance(context).putString(REFERPAGE_ID, page_Id);
    }

    /**
     * get ReferPageId
     *
     * @param context
     * @return
     */
    private static String getReferPageId(Context context) {
        return SharedPreferencesHelper.getInstance(context).getString(REFERPAGE_ID);
    }

}
