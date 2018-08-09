package com.tamic.statinterface.stats.db;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;


/**
 * 版权:上海屋聚 版权所有
 * author: yandeqing
 * version: 3.0.0
 * date:2018/8/8 18:10
 * 备注:
 */
public class DbManager {

    // 数据库加密KEY
    private static final String DB_KEY = "db_encry_key";

    // DB_NAME
    public static final String DB_NAME = "data_monitor";

    private static DbManager mDbManager = new DbManager();
    private DaoMaster.DevOpenHelper helper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DbManager() {
    }

    public static DbManager getInstance() {
        return mDbManager;
    }


    public void init(Context context) {
        //        使用自定义配置
        helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null) {
            @Override
            public void onUpgrade(Database db, int oldVersion, int newVersion) {
                onDbUpgrade(db, oldVersion, newVersion);
            }
        };
        SQLiteDatabase.loadLibs(context.getApplicationContext());
        daoMaster = new DaoMaster(helper.getEncryptedWritableDb(DB_KEY));
        daoSession = daoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }

    public Database getDb() {
        return daoMaster.getDatabase();
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public void destroy() {
        try {
            if (daoMaster != null) {
                daoMaster.getDatabase().close();
                daoMaster = null;
            }

            if (helper != null) {
                helper.close();
                helper = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 数据库升级处理
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    private void onDbUpgrade(Database db, int oldVersion, int newVersion) {
        //把需要管理的数据库表DAO作为最后一个参数传入到方法中
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, TcNoteDao.class, DeviceUserDao.class);
    }




}
