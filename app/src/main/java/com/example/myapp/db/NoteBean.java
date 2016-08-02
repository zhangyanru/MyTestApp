package com.example.myapp.db;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 */
public class NoteBean {
    public long id;
    public String note;
    public int date;

    public NoteBean(long id,String note, int date) {
        this.id = id;
        this.note = note;
        this.date = date;
    }

    public NoteBean() {
    }
}
