package com.tamic.statinterface.stats.db.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tamic.statinterface.stats.db.TcNote;
import com.tamic.statinterface.stats.db.database.ReadDataBaseAccess;
import com.tamic.statinterface.stats.db.database.WriteDataBaseAccess;
import com.tamic.statinterface.stats.model.AppAction;
import com.tamic.statinterface.stats.model.DataBlock;
import com.tamic.statinterface.stats.model.Event;
import com.tamic.statinterface.stats.model.ExceptionInfo;
import com.tamic.statinterface.stats.model.Page;
import com.tamic.statinterface.stats.util.JsonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Tamic on 2016-03-17.
 */
public class StaticsAgent {
    private static final String TAG = StaticsAgent.class.getSimpleName();
    private static final boolean debug = true;

    private static Context mContext;
    private static TcNote note;
    private static NoteDaoHelper helper;
    private static DaoThread daoThread;

    private static final int MSG_TYPE_LOAD = 1;
    private static final int MSG_TYPE_INSERT = 2;
    private static final int MSG_TYPE_ROLL_BACK = 3;
    private static final int MSG_TYPE_DELETE = 4;
    private static final int MSG_TYPE_INSERT_NOW = 5;
    /* */

    /**
     * @param context
     *//*
    public static void init(Context context) {

        mContext = context;

        DataAccess.shareInstance(context).createAllTables();

    }*/
    public static void init(Context context) {
        mContext = context;
        if (helper == null) {
            helper = new NoteDaoHelper(context);
        }

        if (daoThread == null) {
            daoThread = new DaoThread();
            daoThread.start();
        }
    }

    private static void storeAppAction(String appAction) {
        if (!TextUtils.isEmpty(appAction)) {
            storePaData(appAction, "", "");
        }
    }

    private static void storePage(String pageString) {
        if (!TextUtils.isEmpty(pageString)) {
            storePaData("", pageString, "");
        }
    }

    private static void storeEvent(String eventString) {
        if (!TextUtils.isEmpty(eventString)) {
            storePaData("", "", eventString);
        }
    }

    public static void getDataBlock(Handler dataHandler) {
        Message msg = new Message();
        msg.what = MSG_TYPE_LOAD;
        msg.obj = dataHandler;
        daoThread.mHandler.sendMessage(msg);
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
            }
            if (!TextUtils.isEmpty(list.get(i).getFourCloumn())) {
                exceptionInfo = JsonUtil.parseObject(list.get(i).getFourCloumn(), ExceptionInfo.class);
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
        note = new TcNote(null, firstcloumn, secondcloumn, thirdcloumn, forthCloumn, TcNote.DATA_STATUS_SAVED);
        WriteDataBaseAccess.shareInstance(mContext).insertData(note);
    }

    private static void storePaData(String appAction, String pageInfo, String eventInfo) {
        Log.d(TAG, "【StaticsAgent.storePaData()】" + "【appAction = " + appAction + ", pageInfo = " + pageInfo + ", eventInfo = " + eventInfo + "】");
        Message msg = new Message();
        msg.what = appAction == null ? MSG_TYPE_INSERT : MSG_TYPE_INSERT_NOW;
        msg.obj = new TcNote(null, appAction, pageInfo, eventInfo, null, TcNote.DATA_STATUS_SAVED);
        daoThread.mHandler.sendMessage(msg);
    }


    /**
     * storePage
     *
     * @param exceptionInfo
     */
    public static void storeException(String exceptionInfo) {
        if (TextUtils.isEmpty(exceptionInfo)) {
            throw new NullPointerException("exceptionInfo is null");
        }
        storeData("", "", "", exceptionInfo);
    }


    /**
     * storeObject
     *
     * @param o
     */
    public static void storeObject(Object o) {
        Log.d(TAG, "【StaticsAgent.storeObject()】" + "【o = " + o + "】");
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

    public static synchronized int deleteData() {
        return WriteDataBaseAccess.shareInstance(mContext).deleteAllNote();
    }

    private static class DaoThread extends Thread {

        private Handler mHandler = null;
        private List<TcNote> notesBuffer = new ArrayList<>();
        private static final int BUFFER_MAX_SIZE = 10;

        @Override
        public void run() {
            Looper.prepare();

            mHandler = new Handler(Looper.myLooper()) {

                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case MSG_TYPE_DELETE: {
                            helper.deleteByStatus(TcNote.DATA_STATUS_SENDING);
                        }
                        break;

                        case MSG_TYPE_ROLL_BACK: {
                            helper.updateStatus(TcNote.DATA_STATUS_SENDING, TcNote.DATA_STATUS_SAVED);
                        }
                        break;
                        case MSG_TYPE_INSERT: {
                            if (msg.obj != null && msg.obj instanceof TcNote) {
                                TcNote note = (TcNote) msg.obj;
                                notesBuffer.add(note);
                                if (notesBuffer.size() >= BUFFER_MAX_SIZE) {
                                    helper.insertList(notesBuffer);
                                    notesBuffer.clear();
                                }
                            }
                        }
                        break;

                        case MSG_TYPE_INSERT_NOW: {
                            if (msg.obj != null && msg.obj instanceof TcNote) {
                                TcNote note = (TcNote) msg.obj;
                                notesBuffer.add(note);
                                helper.insertList(notesBuffer);
                                notesBuffer.clear();
                            }
                        }
                        break;

                        case MSG_TYPE_LOAD: {
                            if (notesBuffer.size() > 0) {
                                helper.insertList(notesBuffer);
                                notesBuffer.clear();
                            }
                            DataBlock dataBlock = new DataBlock();
                            List<TcNote> list = helper.getUnSendNotes();
                            if (list != null) {
                                helper.updateStatus(TcNote.DATA_STATUS_SAVED, TcNote.DATA_STATUS_SENDING);
                            }

                            AppAction action;
                            Page page;
                            Event event;
                            ExceptionInfo exceptionInfo;
                            List<AppAction> actionList = new ArrayList<>();
                            List<Page> pageList = new ArrayList<>();
                            List<Event> eventList = new ArrayList<>();
                            List<ExceptionInfo> exceptionInfos = new ArrayList<>();
                            for (int i = 0; list != null && i < list.size(); i++) {
                                if (!TextUtils.isEmpty(list.get(i).getFirstCloumn())) {
                                    action = JSON.parseObject(list.get(i).getFirstCloumn(), AppAction.class);
                                    actionList.add(action);
                                }
                                if (!TextUtils.isEmpty(list.get(i).getSecondCloumn())) {
                                    page = JSON.parseObject(list.get(i).getSecondCloumn(), Page.class);
                                    pageList.add(page);
                                }
                                if (!TextUtils.isEmpty(list.get(i).getThirdCloumn())) {
                                    event = JSON.parseObject(list.get(i).getThirdCloumn(), Event.class);
                                    eventList.add(event);
                                }
                                if (!TextUtils.isEmpty(list.get(i).getFourCloumn())) {
                                    exceptionInfo = JSON.parseObject(list.get(i).getFourCloumn(), ExceptionInfo.class);
                                    exceptionInfos.add(exceptionInfo);
                                }
                            }
                            Collections.sort(pageList, new Comparator<Page>() {

                                @Override
                                public int compare(Page lhs, Page rhs) {
                                    return lhs.getPage_start_time().compareTo(rhs.getPage_start_time());
                                }
                            });
                            Collections.sort(eventList, new Comparator<Event>() {

                                @Override
                                public int compare(Event lhs, Event rhs) {
                                    return lhs.getAction_time().compareTo(rhs.getAction_time());
                                }
                            });
                            dataBlock.setApp_action(actionList);
                            dataBlock.setPage(pageList);
                            dataBlock.setEvent(eventList);
                            dataBlock.setExceptionInfos(exceptionInfos);

                            if (msg.obj != null && msg.obj instanceof Handler) {
                                Handler handler = (Handler) msg.obj;
                                Message msgBack = new Message();
                                msgBack.what = 0;
                                msgBack.obj = dataBlock;
                                handler.sendMessage(msgBack);
                            }

                        }
                        break;
                    }
                    super.handleMessage(msg);
                }
            };
            Looper.loop();
        }
    }
}

