package com.example.myapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.myapp.activity.MyApplication;

import java.io.File;

/**
 * Created by yanru.zhang on 16/6/20.
 * Email:yanru.zhang@renren-inc.com
 */
public class PrefrenceManager {

    private static PrefrenceManager instance;

    public static synchronized PrefrenceManager getInstance(){
        if(instance == null){
            instance = new PrefrenceManager();
        }
        return instance;
    }

    private Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    public PrefrenceManager(){
        if(mContext == null){
            mContext = MyApplication.getContext();
        }
        sharedPreferences = mContext.getSharedPreferences("shared_prefs",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setAccount(String account){
        editor.putString("account",account);
        editor.commit();
    }
    public String getAccount(){
        return sharedPreferences.getString("account","");
    }

    public void deleteAll(){
        File file = new File("/data/data/"+mContext.getPackageName().toString()+"/shared_prefs","Activity.xml");
        if(file.exists()){
            file.delete();
        }
        Toast.makeText(mContext,"delete share prefrence success",Toast.LENGTH_SHORT).show();
    }
}
