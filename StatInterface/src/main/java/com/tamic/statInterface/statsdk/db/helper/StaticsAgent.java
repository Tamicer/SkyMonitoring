package com.tamic.statInterface.statsdk.db.helper;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.tamic.statInterface.statsdk.db.TcNote;
import com.tamic.statInterface.statsdk.db.TcNoteDao;
import com.tamic.statInterface.statsdk.model.AppAction;
import com.tamic.statInterface.statsdk.model.DataBlock;
import com.tamic.statInterface.statsdk.model.Event;
import com.tamic.statInterface.statsdk.model.Page;
import com.tamic.statInterface.statsdk.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tamic on 2016-03-17.
 */
public class StaticsAgent {
    private static NoteDaoHelper helper;
    private static TcNoteDao noteDao;
    private static TcNote note;

    /**
     * @param context
     */
    public static void init(Context context) {
        helper = new NoteDaoHelper();
        noteDao = helper.getPaNoteDao(context);
    }

    /**
     * 存储appAction相关信息
     *
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

    public static DataBlock getDataBlock() {
        DataBlock dataBlock = new DataBlock();
        List<TcNote> list = noteDao.loadAll();
        AppAction appAction = new AppAction();
        Page page = new Page();
        Event event = new Event();
        List<AppAction> actionList = new ArrayList<AppAction>();
        List<Page> pageList = new ArrayList<Page>();
        List<Event> eventList = new ArrayList<Event>();
        for (int i = 0; i < list.size(); i++) {
            if (!TextUtils.isEmpty(list.get(i).getFirstcloumn())) {
                appAction = JsonUtil.parseObject(list.get(i).getFirstcloumn(), AppAction.class);
                actionList.add(appAction);
            }
            if (!TextUtils.isEmpty(list.get(i).getSecondcloumn())) {
                page = JsonUtil.parseObject(list.get(i).getSecondcloumn(), Page.class);
                pageList.add(page);
            }
            if (!TextUtils.isEmpty(list.get(i).getThirdcloumn())) {
                event = JsonUtil.parseObject(list.get(i).getThirdcloumn(), Event.class);
                eventList.add(event);
            }
        }
        dataBlock.setApp_action(actionList);
        dataBlock.setPage(pageList);
        dataBlock.setEvent(eventList);
        return dataBlock;
    }


    public static void storeData(String appActionInfo, String pageInfo, String eventInfo) {
        note = new TcNote(null, appActionInfo, pageInfo, eventInfo);
        if (null != helper) {
            noteDao.insert(note);
        }
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
        }
    }

    public static void deleteData() {
        noteDao.deleteAll();
    }

}

