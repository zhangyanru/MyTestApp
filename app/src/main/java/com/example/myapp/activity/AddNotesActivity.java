package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapp.R;
import com.example.myapp.db.NoteBDManager;
import com.example.myapp.db.NoteBean;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 */
public class AddNotesActivity extends Activity {
    private EditText editText;
    private Button saveBtn;
    private int type;

    private NoteBean noteBean;

    public static final int TYPE_ADD_GOING = 1;
    public static final int TYPE_ADD_COMPLETED = 2;
    public static final int TYPE_EDIT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            type = bundle.getInt("type");
            noteBean = bundle.containsKey("note_bean") ? (NoteBean) bundle.getSerializable("note_bean") : new NoteBean();
        }

        setContentView(R.layout.activity_add_notes);
        editText = (EditText) findViewById(R.id.ev);
        if(!TextUtils.isEmpty(noteBean.note)){
            editText.setText(noteBean.note);
        }
        saveBtn = (Button) findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            save();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void save() {
        if(TextUtils.isEmpty(editText.getText().toString()))return;

        NoteBDManager manager = new NoteBDManager(AddNotesActivity.this);
        noteBean.note = editText.getText().toString();
        noteBean.date = SystemClock.currentThreadTimeMillis();
        if(type == TYPE_ADD_GOING){
            noteBean.status = 1;
            manager.add(noteBean);

        }else if(type == TYPE_ADD_COMPLETED){
            noteBean.status = 2;
            manager.add(noteBean);

        }else if(type ==TYPE_EDIT){
            manager.updateNote(noteBean.note,noteBean.id);
        }
        finish();
    }
}
