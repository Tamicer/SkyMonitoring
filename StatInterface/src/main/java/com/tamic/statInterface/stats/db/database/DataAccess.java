/*
 * DataAccess.java
 *
 *  Created by: NuLL on 2014-1-9
 *  Copyright (c) 2014年 SAIC. All rights reserved.
 */
package com.tamic.statinterface.stats.db.database;

import android.content.Context;

import com.tamic.statinterface.stats.db.TcNote;

import java.util.ArrayList;

/*
 * 对外接口，数据库访问层，所有的数据库操作都只能通过该类访问
 */
public class DataAccess {
    private static DataBaseHandler handler = null;
    private static DataAccess access = null;

    private static ReadDataBaseAccess readAccess = null;
    private static WriteDataBaseAccess writeAccess = null;
    private static Context appContext;

    public static synchronized DataAccess shareInstance(Context context) {
        appContext = context;
        readAccess = ReadDataBaseAccess.shareInstance(appContext);
        if (access == null) {
            access = new DataAccess();
            handler = DataBaseHandler.writeInstance(appContext);
            writeAccess = WriteDataBaseAccess.shareInstance(appContext);
        }
        return access;
    }

    public void closeDataBase() {
        handler.close();
    }

    //创建所有表
    public void createAllTables() {
        createNoteTable();
        createCustomerTable();
    }


    private void createNoteTable() {
        boolean isCreatedSec = handler.createTableWithSQL("create table if not exists T_Note(_id integer primary key autoincrement,firstcloumn text,secondcloumn text," +
                "thirdcloumn text,forthcloumn text,STATUS text)");
        if (!isCreatedSec) {
            System.out.println("create table T_Note failure!");
        }
    }

    private void createCustomerTable() {
        boolean isCreatedSec = handler.createTableWithSQL("create table if not exists T_Customer(_id integer primary key autoincrement,name text)");
        if (!isCreatedSec) {
            System.out.println("create table T_Customer failure!");
        }
    }

    //添加接口
    public boolean insertData(Object obj) {
        return writeAccess.insertData(obj);
    }

    //批量插入事件
    public boolean insertNotes(ArrayList<?> notes) {
        return writeAccess.insertDatas(notes);
    }

    //插入事件
    public boolean insertNote(TcNote note) {
        return writeAccess.insertData(note);
    }
}
