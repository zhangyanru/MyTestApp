package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.util.UploadImage;


import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by yanru.zhang on 16/7/7.
 * Email:yanru.zhang@renren-inc.com
 */
public class HttpMultiPartTestActivity extends Activity {
    private EditText editText;
    private Button button;
    private String urlString = null;
    private AsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_http_multi_part_test);
        editText =(EditText) findViewById(R.id.edit_tv);
        editText.setSelection(editText.getText().toString().length());
        button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传图片
                initTask();
                asyncTask.execute();

            }
        });
    }

    private void initTask(){
        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String response = UploadImage.uploadFile(new File("/storage/emulated/0/GoStore/icon/2137923823.jpg"), urlString);
                return response;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                urlString = editText.getText().toString();
                Toast.makeText(HttpMultiPartTestActivity.this, "开始上传", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Toast.makeText(HttpMultiPartTestActivity.this, (o == null ? "" : o.toString()), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
