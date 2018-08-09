package com.tamic.statinterface.stats.model;

import com.tamic.statinterface.stats.bean.TcNote;
import com.tamic.statinterface.stats.db.DbManager;
import com.tamic.statinterface.stats.db.TcNoteDao;

import java.util.List;

public class TcNoteModel extends BaseModel {

    private TcNoteDao tcNoteDao;

    private static TcNoteModel mTcNoteModel = new TcNoteModel();

    private TcNoteModel() {
        tcNoteDao = DbManager.getInstance().getDaoSession().getTcNoteDao();
    }

    public static TcNoteModel getInstance() {
        return mTcNoteModel;
    }

    public TcNoteDao getTcNoteDao() {
        return tcNoteDao;
    }

    public List<TcNote> getUnSendNotes() {
        return tcNoteDao.queryBuilder()
                .where(TcNoteDao.Properties.Status.eq(0)).list();
    }

    public void updateStatus(int from, int to) {
        String sql = "UPDATE '" + TcNoteDao.TABLENAME + "' SET STATUS=" + to + " WHERE STATUS=" + from;
        tcNoteDao.getDatabase().execSQL(sql);

    }

    public void deleteByStatus(int status) {
        String sql = "DELETE FROM '" + TcNoteDao.TABLENAME + "' WHERE STATUS=" + status;
        tcNoteDao.getDatabase().execSQL(sql);
    }

    public void insertList(List<TcNote> notes) {
        tcNoteDao.insertInTx(notes);
    }
}
