/*
 * ReadDataBaseAccess.java
 *
 *  Created by: NULL on 2014-1-9
 *  Copyright (c) 2014年 SAIC. All rights reserved.
 */
package com.tamic.statinterface.stats.db.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tamic.statinterface.stats.bean.TcNote;

import java.util.ArrayList;


public class ReadDataBaseAccess {
    private DataBaseHandler handler = null;
    private static ReadDataBaseAccess readAccess = null;
    private static boolean isConnectionBusy = false;

    protected ReadDataBaseAccess(Context context) {
        handler = DataBaseHandler.readInstance(context);
    }

    public static synchronized ReadDataBaseAccess shareInstance(Context context) {
        readAccess = new ReadDataBaseAccess(context);
        return readAccess;
    }


    //查询所有的note
    public ArrayList<TcNote> loadAll() {
        SQLiteDatabase connection = handler.getReadConnection(Thread.currentThread().getStackTrace()[2].getMethodName());
        Cursor cursor = connection.rawQuery("select * from T_Note", null);
        ArrayList<TcNote> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            TcNote note = new TcNote();
            note.setFirstCloumn(cursor.getString(1));
            note.setSecondCloumn(cursor.getString(2));
            note.setThirdCloumn(cursor.getString(3));
            note.setFourCloumn(cursor.getString(4));
            note.setStatus((int) cursor.getLong(5));
            notes.add(note);
        }
        cursor.close();
        handler.closeConnection(connection, Thread.currentThread().getStackTrace()[2].getMethodName());
        return notes;
    }


    //查询所有的note
    public ArrayList<TcNote> loadAllByWhere(String filter) {
        SQLiteDatabase connection = handler.getReadConnection(Thread.currentThread().getStackTrace()[2].getMethodName());
        Cursor cursor = connection.rawQuery("select * from T_Note where " + filter, null);
        ArrayList<TcNote> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            TcNote note = new TcNote();
            note.setFirstCloumn(cursor.getString(1));
            note.setSecondCloumn(cursor.getString(2));
            note.setThirdCloumn(cursor.getString(3));
            note.setFourCloumn(cursor.getString(4));
            note.setStatus((int) cursor.getLong(5));
            notes.add(note);
        }
        cursor.close();
        handler.closeConnection(connection, Thread.currentThread().getStackTrace()[2].getMethodName());
        return notes;
    }
}
