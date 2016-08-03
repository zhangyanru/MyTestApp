package com.example.myapp.db;

import java.io.Serializable;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 */
public class NoteBean implements Serializable{
    public long id;
    public String note;
    public long date;
    public int status;//1-new,2-complete,3-deleted

    public NoteBean(String note, long date) {
        this.note = note;
        this.date = date;
        status = 1;
    }


    public NoteBean() {
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n" + "id:" + id + "\n");
        stringBuffer.append("note:" + note + "\n");
        stringBuffer.append("date:" + date + "\n");
        stringBuffer.append("status:" + status + "\n");
        return stringBuffer.toString();
    }
}
