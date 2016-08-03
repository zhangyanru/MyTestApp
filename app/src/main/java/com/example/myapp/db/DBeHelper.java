package com.example.myapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 *
 * 一个数据库中包含了多个表，my_app_db数据库中现在有note表
 */
public class DBeHelper extends SQLiteOpenHelper {
    private final static String TAG = "DBeHelper";
    public final static String DB_NAME = "my_app_db";
    public final static String NOTE_TABLE = "note";
    public final static int DB_VERSION = 2;

    public DBeHelper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Creates database the first time we try to open it.
     */
    @Override
    public void onCreate(final SQLiteDatabase db) {
        Log.v(TAG, "populating new database");
        //这里有个坑爹的东西 db.getVersion()竟然=0,然后我就被骗了，哎哎哎。
        onUpgrade(db, 1, DB_VERSION);
    }

    /**
     * Updates the database format when a content provider is used
     * with a database that was created with a different format.
     * <p/>
     * Note: to support downgrades, creating a table should always drop it first if it already
     * exists.
     */
    @Override
    public void onUpgrade(final SQLiteDatabase db, int oldV, final int newV) {
        Log.v(TAG, "upgrade a database" + " oldV:" + oldV + " newV:" + newV);

        for (int version = oldV; version <= newV; version++) {
            Log.v(TAG, "do version " + version + "update");

            upgradeTo(db, version);
        }
    }

    /**
     * Upgrade database from (version - 1) to version.
     */
    private void upgradeTo(SQLiteDatabase db, int version) {
        switch (version) {
            case 1:
                //建note表
                createTable(db);
                break;
            case 2:
                //note表添加status字段并且默认值为1
                db.beginTransaction();
                try {
                    String sql = "alter table " + NOTE_TABLE + " add status integer default 1";
                    db.execSQL(sql);
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }

                break;
        }
    }

    private void createTable(SQLiteDatabase db){
        Log.d(TAG,"create table");
        db.beginTransaction();
        try {
            String sql = "create table if not exists " + NOTE_TABLE +
                    "(id integer primary key autoincrement, " +
                    "note TEXT, " +
                    "createDate VARCHAR)";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

    }
}
