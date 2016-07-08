package com.example.myapp.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.util.UploadImage;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by yanru.zhang on 16/7/7.
 * Email:yanru.zhang@renren-inc.com
 */
public class HttpMultiPartTestActivity extends Activity {
    private ImageView imageView;
    private EditText editText;
    private Button button;
    private ProgressBar progressBar;
    private String urlString = null;
    private byte[] imageBytes;
    private String multipart_form_data = "multipart/form-data";
    private String twoHyphens = "--";
    private String boundary = "****************fD4fH3gL0hK7aI6";    // 数据分隔符
    private String lineEnd = System.getProperty("line.separator");    // The value is "\r\n" in Windows.
    private AsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_http_multi_part_test);
        imageView = (ImageView) findViewById(R.id.image_view);
        editText =(EditText) findViewById(R.id.edit_tv);
        editText.setSelection(editText.getText().toString().length());
        button = (Button) findViewById(R.id.btn);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传图片
                initTask();
                asyncTask.execute(null);

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
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gou);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                imageBytes = baos.toByteArray();
                Toast.makeText(HttpMultiPartTestActivity.this, "开始上传", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Toast.makeText(HttpMultiPartTestActivity.this, "恭喜你！上传成功！" + (o == null ? "" : o.toString()), Toast.LENGTH_SHORT).show();

            }
        };
    }
}
