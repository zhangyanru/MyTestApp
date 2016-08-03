package com.example.myapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 */
public class NoteBDManager {
    private DBeHelper helper;
    private SQLiteDatabase db;

    public NoteBDManager(Context context) {
        helper = new DBeHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
        Log.d("zyr","db:" + ((db==null)?"null":db.getPath()));
    }

    /*********
     * 增
     * *******/
    public void add(List<NoteBean> notes) {
        db.beginTransaction();	//开始事务
        try {
            for (NoteBean note : notes) {
                String sql = "insert into " + DBeHelper.NOTE_TABLE + "(note,createDate,status) values(?,?,?)";
                db.execSQL(sql, new Object[]{note.note, note.date,note.status});
            }
            db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
            db.endTransaction();	//结束事务
        }
    }

    public void add(NoteBean note) {
        db.beginTransaction();	//开始事务
        try {
            String sql = "insert into " + DBeHelper.NOTE_TABLE + "(note,createDate,status) values(?,?,?)";
            db.execSQL(sql, new Object[]{note.note, note.date,note.status});
            db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
            db.endTransaction();	//结束事务
        }
    }

    /*********
     * 删
     * *******/
    public void delete(long id){
        db.beginTransaction();
        try {
            String sql = "delete from " + DBeHelper.NOTE_TABLE + " where id=?";
            db.execSQL(sql,new Object[]{id});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    /*********
     * 改
     * *******/
    public void updateNote(String note,long id) {
        db.beginTransaction();
        try {
            String sql = "update " + DBeHelper.NOTE_TABLE + " set note=? where id=?";
            db.execSQL(sql,new Object[]{note,id});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

    }

    /*********
     * 查
     * *******/
    public List<NoteBean> query() {
        ArrayList<NoteBean> notes = new ArrayList<NoteBean>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            NoteBean noteBean = new NoteBean();
            noteBean.id = c.getLong(c.getColumnIndex("id"));
            noteBean.note = c.getString(c.getColumnIndex("note"));
            noteBean.date = c.getInt(c.getColumnIndex("createDate"));
            noteBean.status = c.getInt(c.getColumnIndex("status"));
            notes.add(noteBean);
        }
        c.close();
        return notes;
    }

    /**
     * query all notes, return cursor
     * @return	Cursor
     */
    public Cursor queryTheCursor() {
        String sql = "select * from note";
        Log.d("zyr","sql: " + sql);
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
