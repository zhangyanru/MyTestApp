package com.example.myapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 */
public class NotesDataBaseHelper extends SQLiteOpenHelper {
    private final static String TAG = "NotesDataBaseHelper";
    public final static String DB_NAME = "note_db";
    public final static int DB_VERSION = 1;

    public NotesDataBaseHelper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Creates database the first time we try to open it.
     */
    @Override
    public void onCreate(final SQLiteDatabase db) {
        Log.v(TAG, "populating new database");
        onUpgrade(db, 1, getReadableDatabase().getVersion());
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

        for (int version = oldV; version <= newV; version++) {
            upgradeTo(db, version);
        }
    }

    /**
     * Upgrade database from (version - 1) to version.
     */
    private void upgradeTo(SQLiteDatabase db, int version) {
        switch (version) {
            case 1:
                createTable(db);
                break;
        }
    }

    private void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE IF NOT EXISTS " + DB_NAME + "(" +
                "_id VARCHAR PRIMARY KEY AUTOINCREMENT, " +
                "note TEXT, " +
                "createDate VARCHAR " +
                ")";
        db.execSQL(sql);
    }
}
