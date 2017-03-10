package com.tamic.statInterface.statsdk.db.helper;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.tamic.statInterface.statsdk.db.TcNote;
import com.tamic.statInterface.statsdk.db.database.DataAccess;
import com.tamic.statInterface.statsdk.db.database.ReadDataBaseAccess;
import com.tamic.statInterface.statsdk.db.database.WriteDataBaseAccess;
import com.tamic.statInterface.statsdk.model.AppAction;
import com.tamic.statInterface.statsdk.model.DataBlock;
import com.tamic.statInterface.statsdk.model.Event;
import com.tamic.statInterface.statsdk.model.ExceptionInfo;
import com.tamic.statInterface.statsdk.model.Page;
import com.tamic.statInterface.statsdk.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tamic on 2016-03-17.
 */
public class StaticsAgent {
    private static Context mContext;
    private static TcNote note;

    /**
     * @param context
     */
    public static void init(Context context) {

        mContext = context;

        DataAccess.shareInstance(context).createAllTables();

    }

    /**
     * storeAppAction
     * @param appAction
     */
    public static void storeAppAction(String appAction) {
        if (TextUtils.isEmpty(appAction))
            throw new NullPointerException("appAction is null");
        storeData(appAction, "", "");
    }

    /**
     * storePage
     *
     * @param pageString
     */
    public static void storePage(String pageString) {
        if (TextUtils.isEmpty(pageString))
            throw new NullPointerException("pageString is null");
        storeData("", pageString, "");
    }

    /**
     * storeEvent
     *
     * @param eventString
     */
    public static void storeEvent(String eventString) {
        if (TextUtils.isEmpty(eventString))
            throw new NullPointerException("eventString is null");
        storeData("", "", eventString);
    }

    /**
     * storePage
     *
     * @param exceptionInfo
     */
    public static void storeException(String exceptionInfo) {
        if (TextUtils.isEmpty(exceptionInfo))
            throw new NullPointerException("exceptionInfo is null");
        storeData("", "", "", exceptionInfo);
    }

    public static DataBlock getDataBlock() {
        DataBlock dataBlock = new DataBlock();
        List<TcNote> list = ReadDataBaseAccess.shareInstance(mContext).loadAll();
        AppAction appAction = new AppAction();
        Page page = new Page();
        Event event = new Event();
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        List<AppAction> actionList = new ArrayList<>();
        List<Page> pageList = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();
        List<ExceptionInfo> exceptionInfos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!TextUtils.isEmpty(list.get(i).getFirstCloumn())) {
                appAction = JsonUtil.parseObject(list.get(i).getFirstCloumn(), AppAction.class);
                actionList.add(appAction);
            }
            if (!TextUtils.isEmpty(list.get(i).getSecondCloumn())) {
                page = JsonUtil.parseObject(list.get(i).getSecondCloumn(), Page.class);
                pageList.add(page);
            }
            if (!TextUtils.isEmpty(list.get(i).getThirdCloumn())) {
                event = JsonUtil.parseObject(list.get(i).getThirdCloumn(), Event.class);
                eventList.add(event);
            }if (!TextUtils.isEmpty(list.get(i).getForthCloumn())) {
                exceptionInfo = JsonUtil.parseObject(list.get(i).getForthCloumn(), ExceptionInfo.class);
                exceptionInfos.add(exceptionInfo);
            }
        }
        dataBlock.setApp_action(actionList);
        dataBlock.setPage(pageList);
        dataBlock.setExceptionInfos(exceptionInfos);
        dataBlock.setEvent(eventList);
        return dataBlock;
    }

    public static void storeData(String firstcloumn, String secondcloumn, String thirdcloumn) {
       storeData(firstcloumn, secondcloumn, thirdcloumn, null);
    }


    public static void storeData(String firstcloumn, String secondcloumn, String thirdcloumn, String forthCloumn) {
        note = new TcNote(null, firstcloumn, secondcloumn, thirdcloumn, forthCloumn);
        WriteDataBaseAccess.shareInstance(mContext).insertData(note);
    }


    /**
     * storeObject
     *
     * @param o
     */
    public static void storeObject(Object o) {
        if (o instanceof Event) {
            storeEvent(JSONObject.toJSONString(o));
        } else if (o instanceof AppAction) {
            storeAppAction(JSONObject.toJSONString(o));
        } else if (o instanceof Page) {
            storePage(JSONObject.toJSONString(o));
        } else if (o instanceof ExceptionInfo) {
            storeException(JSONObject.toJSONString(o));
        }

    }

    public static synchronized void  deleteData() {
        WriteDataBaseAccess.shareInstance(mContext).deleteAllNote();
    }


}

