/*
 * WriteDataBaseAccess.java
 *
 *  Created by: NULL on 2014-1-9
 *  Copyright (c) 2014年 SAIC. All rights reserved.
 */
package com.tamic.statinterface.stats.db.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.tamic.statinterface.stats.db.TcNote;

import java.util.List;

public class WriteDataBaseAccess {
    private DataBaseHandler handler = null;
    private static WriteDataBaseAccess writeAccess = null;
    private Context context;

    public WriteDataBaseAccess(Context context) {
        handler = DataBaseHandler.writeInstance(context);
        this.context = context;
    }

    public static WriteDataBaseAccess shareInstance(Context context) {
        if (writeAccess == null) {
            writeAccess = new WriteDataBaseAccess(context);
        }
        return writeAccess;
    }

    /**
     * execSQL
     */
    public void execSQL(String sql) {
        SQLiteDatabase connection = handler.getWriteConnection(Thread.currentThread().getStackTrace()[2].getMethodName());
        connection.execSQL(sql);
        connection.close();
    }

    /**
     * 数据库插入相关接口
     */
    //添加接口
    public boolean insertData(TcNote obj) {
        return insertNote(obj);
    }


    private boolean insertNote(TcNote note) {
        ContentValues values = new ContentValues();
        values.put("firstcloumn", note.getFirstCloumn());
        values.put("secondCloumn", note.getSecondCloumn());
        values.put("thirdCloumn", note.getThirdCloumn());
        values.put("forthCloumn", note.getFourCloumn());
        values.put("STATUS", note.getStatus());
        SQLiteDatabase connection = handler.getWriteConnection(Thread.currentThread().getStackTrace()[2].getMethodName());
        long result = connection.insert("T_Note", null, values);
        if (!connection.inTransaction()) {
            handler.closeConnection(connection, Thread.currentThread().getStackTrace()[2].getMethodName());
        }
        return (result != -1);
    }

    //批量添加接口
    public boolean insertDatas(List<? extends TcNote> list) {
        if (list.isEmpty()) {
            System.out.println("no datas");
            return false;
        } else {
            return insertTcNotes(list);
        }
    }

    public boolean insertTcNotes(List<? extends TcNote> notes) {
        boolean result = false;
        SQLiteDatabase connection = handler.getWriteConnection(Thread.currentThread().getStackTrace()[2].getMethodName());
        String sql = "insert into T_Note (firstCloumn,secondCloumn,thirdCloumn,forthCloumn,STATUS) values (?,?,?,?,?)";
        connection.beginTransaction();
        try {
            SQLiteStatement stmt = connection.compileStatement(sql);
            for (int i = 0; i < notes.size(); i++) {
                TcNote note = (TcNote) notes.get(i);
                String firstCloumn = note.getFirstCloumn();
                if (firstCloumn != null) {
                    stmt.bindString(1, firstCloumn);
                }
                String secondCloumn = note.getSecondCloumn();
                if (secondCloumn != null) {
                    stmt.bindString(2, secondCloumn);
                }
                String thirdCloumn = note.getThirdCloumn();
                if (thirdCloumn != null) {
                    stmt.bindString(3, thirdCloumn);
                }
                String forthCloumn = note.getFourCloumn();
                if (forthCloumn != null) {
                    stmt.bindString(4, forthCloumn);
                }
                Integer status = note.getStatus();
                if (status != null) {
                    stmt.bindLong(5, status);
                }
                stmt.execute();
                stmt.clearBindings();
            }
            connection.setTransactionSuccessful();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            connection.endTransaction();
            handler.closeConnection(connection, Thread.currentThread().getStackTrace()[2].getMethodName());
        }
        return result;
    }

    public int deleteAllNote() {
        SQLiteDatabase connection = handler.getWriteConnection(Thread.currentThread().getStackTrace()[2].getMethodName());
        int result = connection.delete("T_Note", "STATUS=?", new String[]{"1"});
        if (!connection.inTransaction()) {
            handler.closeConnection(connection, Thread.currentThread().getStackTrace()[2].getMethodName());
        }
        return result;
    }
}
