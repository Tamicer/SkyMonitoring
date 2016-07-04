package com.tamic.statInterface.statsdk.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tamic.statInterface.statsdk.db.DaoMaster;
import com.tamic.statInterface.statsdk.db.DaoSession;
import com.tamic.statInterface.statsdk.db.TcNoteDao;

/**
 * Created by Tamic on 2016-03-16.
 */
public class NoteDaoHelper {
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private TcNoteDao paNoteDao ;

    public TcNoteDao getPaNoteDao(Context context){
        if(paNoteDao == null){
            paNoteDao = getDaoSession(context).getPaNoteDao();
        }
        return paNoteDao ;
    }




    private DaoMaster getDaoMaster(Context context){
        if(daoMaster == null){
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "pa-db", null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
        }
        return daoMaster ;
    }

    private DaoSession getDaoSession(Context context){
        if(daoSession == null){
            daoSession = getDaoMaster(context).newSession();
        }
        return daoSession ;
    }




}
