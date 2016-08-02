package com.example.myapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 */
public class NoteBDManager {
    private NotesDataBaseHelper helper;
    private SQLiteDatabase db;

    public NoteBDManager(Context context) {
        helper = new NotesDataBaseHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add persons
     * @param notes
     */
    public void add(List<NoteBean> notes) {
        db.beginTransaction();	//开始事务
        try {
            for (NoteBean note : notes) {
                db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{note.note, note.date});
            }
            db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
            db.endTransaction();	//结束事务
        }
    }

    public void add(NoteBean note) {
        db.beginTransaction();	//开始事务
        try {
            db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{note.id,note.note, note.date});
            db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
            db.endTransaction();	//结束事务
        }
    }

    /**
     * update person's age
     * @param note
     */
    public void updateAge(NoteBean note) {
        ContentValues cv = new ContentValues();
        cv.put("id", note.id);
        db.update("person", cv, "id = ?", new String[]{note.id + ""});
    }

    /**
     * query all persons, return list
     * @return List<Person>
     */
    public List<NoteBean> query() {
        ArrayList<NoteBean> notes = new ArrayList<NoteBean>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            NoteBean noteBean = new NoteBean();
            noteBean.id = c.getInt(c.getColumnIndex("_id"));
            noteBean.note = c.getString(c.getColumnIndex("note"));
            noteBean.date = c.getInt(c.getColumnIndex("date"));
            notes.add(noteBean);
        }
        c.close();
        return notes;
    }

    /**
     * query all persons, return cursor
     * @return	Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM " + NotesDataBaseHelper.DB_NAME, null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
