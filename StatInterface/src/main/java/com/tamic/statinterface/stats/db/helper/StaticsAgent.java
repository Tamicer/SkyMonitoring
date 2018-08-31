package com.tamic.statinterface.stats.db.helper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.tamic.statinterface.stats.bean.body.AppAction;
import com.tamic.statinterface.stats.bean.body.DataBlock;
import com.tamic.statinterface.stats.bean.body.Event;
import com.tamic.statinterface.stats.bean.body.ExceptionInfo;
import com.tamic.statinterface.stats.bean.body.Page;
import com.tamic.statinterface.stats.bean.db.TcNote;
import com.tamic.statinterface.stats.db.DbManager;
import com.tamic.statinterface.stats.model.TcNoteModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tamic on 2016-03-17.
 */
public class StaticsAgent {
    private static final String TAG = StaticsAgent.class.getSimpleName();
    private static final boolean debug = true;

    private static TcNoteModel tcNoteDao;
    private static DaoThread daoThread;

    private static final int MSG_TYPE_LOAD = 1;
    private static final int MSG_TYPE_INSERT = 2;
    private static final int MSG_TYPE_ROLL_BACK = 3;
    private static final int MSG_TYPE_DELETE = 4;
    private static final int MSG_TYPE_INSERT_NOW = 5;
    /* */

    /**
     *//*
    public static void init(Context context) {

        mContext = context;

        DataAccess.shareInstance(context).createAllTables();

    }*/
    public static void init() {

        tcNoteDao = TcNoteModel.getInstance();

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
        List<TcNote> list = tcNoteDao.getTcNoteDao().loadAll();
        return new DataBlock.Builder(list).build();
    }

    public static void storeData(String firstcloumn, String secondcloumn, String thirdcloumn) {
        storeData(firstcloumn, secondcloumn, thirdcloumn, null);
    }


    public static void storeData(String firstcloumn, String secondcloumn, String thirdcloumn, String forthCloumn) {
        TcNote note = new TcNote(null, firstcloumn, secondcloumn, thirdcloumn, forthCloumn, TcNote.DATA_STATUS_SAVED);
        DbManager.getInstance().getDaoSession().getTcNoteDao().insert(note);
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

    public static synchronized void deleteData() {
        tcNoteDao.deleteByStatus(TcNote.DATA_STATUS_SENDING);
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
                            tcNoteDao.deleteByStatus(TcNote.DATA_STATUS_SENDING);
                        }
                        break;

                        case MSG_TYPE_ROLL_BACK: {
                            tcNoteDao.updateStatus(TcNote.DATA_STATUS_SENDING, TcNote.DATA_STATUS_SAVED);
                        }
                        break;
                        case MSG_TYPE_INSERT: {
                            if (msg.obj != null && msg.obj instanceof TcNote) {
                                TcNote note = (TcNote) msg.obj;
                                notesBuffer.add(note);
                                if (notesBuffer.size() >= BUFFER_MAX_SIZE) {
                                    tcNoteDao.insertList(notesBuffer);
                                    notesBuffer.clear();
                                }
                            }
                        }
                        break;

                        case MSG_TYPE_INSERT_NOW: {
                            if (msg.obj != null && msg.obj instanceof TcNote) {
                                TcNote note = (TcNote) msg.obj;
                                notesBuffer.add(note);
                                tcNoteDao.insertList(notesBuffer);
                                notesBuffer.clear();
                            }
                        }
                        break;

                        case MSG_TYPE_LOAD: {
                            if (notesBuffer.size() > 0) {
                                tcNoteDao.insertList(notesBuffer);
                                notesBuffer.clear();
                            }
                            List<TcNote> list = tcNoteDao.getUnSendNotes();
                            if (list != null && list.size() > 0) {
                                tcNoteDao.updateStatus(TcNote.DATA_STATUS_SAVED, TcNote.DATA_STATUS_SENDING);
                                DataBlock dataBlock = new DataBlock.Builder(list).build();
                                if (msg.obj != null && msg.obj instanceof Handler) {
                                    Handler handler = (Handler) msg.obj;
                                    Message msgBack = new Message();
                                    msgBack.what = 0;
                                    msgBack.obj = dataBlock;
                                    handler.sendMessage(msgBack);
                                }
                            }

                        }
                        break;
                        default:
                            break;
                    }
                    super.handleMessage(msg);
                }
            };
            Looper.loop();
        }
    }
}

