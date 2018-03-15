/*
 * DataBaseHandler.java
 *
 *  Created by: NULL on 2014-1-9
 *  Copyright (c) 2014年 SAIC. All rights reserved.
 */
package com.tamic.statinterface.stats.db.database;


import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/*
 * 负责和数据库建立连接的类
 * 负责打开关闭数据库连接
 * 通过getWritableDatabase()或getReadableDatabase()方法获取和数据库的连接后对数据库进行操作
 */
public class DataBaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "statlog.db";
    private static final int DB_VERSION = 1;

    private static DataBaseHandler mDataBaseHandler = null;
    private ReentrantLock lock = null;

    /*
     * 构造函数
     *
     * @param context上下文
     * @param dbName数据库名
     * @param dbVersion数据库版本号
     */
    protected DataBaseHandler(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
    }

    protected DataBaseHandler(Context context) {
        this(context, DB_NAME, DB_VERSION);
    }

    protected DataBaseHandler(Context context, int dbVersion) {
        this(context, DB_NAME, dbVersion);
    }

    /*
     * 读接口
     */
    public static DataBaseHandler readInstance(Context context) {
        return new DataBaseHandler(context);
    }

    /*
     * 写接口
     */
    public static synchronized DataBaseHandler writeInstance(Context context) {
        if (mDataBaseHandler == null) {
            mDataBaseHandler = new DataBaseHandler(context);
        }
        return mDataBaseHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        /*db.execSQL();*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    // 获得和数据库的连接对象
    // getWritableDatabase和getReadableDatabase会自动创建数据库打开并获取其引用
    public SQLiteDatabase getReadConnection(String methodName) {
        Log.e("dblog", methodName + " read begin");
        SQLiteDatabase connection = null;
        try {
            connection = getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("dblog", methodName + " read end");
        return connection;
    }

    public synchronized SQLiteDatabase getWriteConnection(String methodName) {
        Log.e("dblog", methodName + " write begin");
        if (lock == null)
            lock = new ReentrantLock();
        lock.lock();
        SQLiteDatabase connection = null;
        try {
            connection = getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("dblog", methodName + " write end");
        return connection;
    }

    // 关闭用完的数据库连接
    public void closeConnection(SQLiteDatabase connection, String methodName) {
        Log.e("dblog", methodName + " close begin");
        if (connection.isOpen()) {
            connection.close();
        }
        if (lock != null)
            lock.unlock();
        Log.e("dblog", methodName + " close end");
    }

    // 关闭数据库
    public void close() {
        super.close();
    }

    public boolean createTableWithSQL(String sql) {
        boolean result = false;
        SQLiteDatabase db = getWriteConnection("createTableWithSQL");
        if (db != null) {
            db.execSQL(sql);
            result = true;
        } else {
            result = false;
        }
        closeConnection(db, "createTableWithSQL");
        return result;
    }

}
