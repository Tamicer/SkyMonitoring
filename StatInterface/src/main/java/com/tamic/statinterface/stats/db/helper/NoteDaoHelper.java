package com.tamic.statinterface.stats.db.helper;

import android.content.Context;


import com.tamic.statinterface.stats.db.TcNote;
import com.tamic.statinterface.stats.db.database.DataAccess;
import com.tamic.statinterface.stats.db.database.ReadDataBaseAccess;
import com.tamic.statinterface.stats.db.database.WriteDataBaseAccess;

import java.util.List;


public class NoteDaoHelper {

    String TABLENAME = "T_Note";

    private TcNote paNoteDao;
    private Context context;

    public NoteDaoHelper(Context context) {
        this.context = context;
        DataAccess.shareInstance(context).createAllTables();
        /*if (paNoteDao == null) {
            paNoteDao = getDaoSession(context).getPaNoteDao();
        }*/
    }

    public List<TcNote> getUnSendNotes() {
        //return paNoteDao.queryRaw("where STATUS = 0");
        return ReadDataBaseAccess.shareInstance(context).loadAllByWhere("STATUS = 0");
    }

    public void updateStatus(int from, int to) {
        String sql = "UPDATE '" + TABLENAME + "' SET STATUS=" + to + " WHERE STATUS=" + from;
        WriteDataBaseAccess.shareInstance(context).execSQL(sql);

    }

    public void deleteByStatus(int status) {
        String sql = "DELETE FROM '" + TABLENAME + "' WHERE STATUS=" + status;
        WriteDataBaseAccess.shareInstance(context).execSQL(sql);
    }

    public void insert(TcNote note) {
        //paNoteDao.insert(note);
        WriteDataBaseAccess.shareInstance(context).insertData(note);
    }

    public void insertList(List<TcNote> notes) {
        WriteDataBaseAccess.shareInstance(context).insertDatas(notes);
        //paNoteDao.insertInTx(notes);
    }


}
