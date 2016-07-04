package com.tamic.statInterface.statsdk.db.helper;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.tamic.statInterface.statsdk.core.StaticsListener;
import com.tamic.statInterface.statsdk.model.AppAction;
import com.tamic.statInterface.statsdk.model.Event;
import com.tamic.statInterface.statsdk.model.KeyValueBean;
import com.tamic.statInterface.statsdk.model.Page;
import com.tamic.statInterface.statsdk.sp.SharedPreferencesHelper;
import com.tamic.statInterface.statsdk.util.DateUtil;

import java.util.ArrayList;

/**
 * DataConstruct
 * Created by tamic on 2016-04-11.
 */
public class DataConstruct {

    private static Event event = null;
    private static ArrayList<KeyValueBean> parameter = new ArrayList<>() ;

    private static Page page = null ;
    private static ArrayList<KeyValueBean> pageParameter = new ArrayList<>() ;

    private static AppAction appAction = null ;

    private static StaticsListener staticsListener;

    private static String pageId;

    private static String referPageId;

    private static String REFERPAGE_ID = "referPage_Id";

    private DataConstruct() {
    }



    /**
     * initEvent
     * @param eventInterface
     * @param event_name
     */
    public static void initEvent(StaticsListener eventInterface, String event_name){
        event = new Event();
        event.setPage_id(pageId);
        event.setReferer_page_id(referPageId);
        event.setEvent_name(event_name);
        event.setAction_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     *  onEvent
     * @param businessName
     * @param businessValue
     */
    public static void onEvent(String businessName,String businessValue){
        parameter.add(new KeyValueBean(businessName, businessValue));
        if(event == null){
            throw new RuntimeException("you must call initEvent before onEvent!");
        }
            event.setParameter(parameter);
    }

    /**
     * storeEvent
     * Activity destory call
     */
    public static void storeEvent(){
        if(event == null){
            return;
        }
        StaticsAgent.storeObject(event);
    }


    /**
     * initPage
     * @param eventInterface
     */
    public static void initPage(Context context, StaticsListener eventInterface, String page_Id, String referPage_Id){
        staticsListener = eventInterface;
        pageId = page_Id;
        recardPageId(context, page_Id);
        if (TextUtils.isEmpty(referPage_Id)){
            referPageId = getReferPageId(context);
        } else {
            referPageId = referPage_Id;
        }
        page = new Page();
        page.setPage_start_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        page.setReferer_page_id(referPageId);
        page.setPage_id(pageId);
    }


    public static void initPageParameter(String name,String value){
        pageParameter.add(new KeyValueBean(name, value));
        page.setParameter(pageParameter);
    }


    /**
     * storePage
     */
    public static void storePage(){
        if(page == null){
            throw new RuntimeException("you must init before storePage");
        }
        page.setPage_end_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        StaticsAgent.storeObject(page);
    }


    /**
     * storeAppAction
     * @param type 1 app打开  2app关闭  3唤醒
     */
    public static void storeAppAction(String type){
        appAction = new AppAction();
        appAction.setAction_time(DateUtil.getDateString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
        appAction.setApp_action_type(type);
        StaticsAgent.storeObject(appAction);
    }


    /**
     * deleteData
     */
    public static void deleteData(){
        StaticsAgent.deleteTable();
    }

    /**
     * recardPageId
     * @param context
     * @param page_Id
     */
    private static void recardPageId (Context context, String page_Id) {
        SharedPreferencesHelper.getInstance(context).putString(REFERPAGE_ID, page_Id);
    }

    /**
     * get ReferPageId
     * @param context
     * @return
     */
    private static String getReferPageId (Context context) {
        return SharedPreferencesHelper.getInstance(context).getString(REFERPAGE_ID);
    }



}
