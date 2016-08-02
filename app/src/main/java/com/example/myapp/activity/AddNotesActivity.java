package com.example.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapp.R;
import com.example.myapp.db.NotesDataBaseHelper;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 */
public class AddNotesActivity extends Activity {
    private EditText editText;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_notes);
        editText = (EditText) findViewById(R.id.ev);
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
        NotesDataBaseHelper noteDataBaseHelper = new NotesDataBaseHelper(AddNotesActivity.this);
        Intent i = new Intent();
        i.putExtra("note",editText.getText().toString());
        setResult(Activity.RESULT_OK,i);
        finish();
    }
}
