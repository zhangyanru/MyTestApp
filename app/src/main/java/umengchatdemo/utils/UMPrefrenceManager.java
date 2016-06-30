package umengchatdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.myapp.activity.MyApplication;

import java.io.File;

/**
 * Created by yanru.zhang on 16/6/20.
 * Email:yanru.zhang@renren-inc.com
 */
public class UMPrefrenceManager {

    private final static String NAME = "um_demo_shared_prefs";
    private String PATH = null;

    private static UMPrefrenceManager instance;

    public static synchronized UMPrefrenceManager getInstance(){
        if(instance == null){
            instance = new UMPrefrenceManager();
        }
        return instance;
    }

    private Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    public UMPrefrenceManager(){
        if(mContext == null){
            mContext = MyApplication.getContext();
        }
        PATH = "/data/data/"+mContext.getPackageName().toString()+"/" + NAME;
        sharedPreferences = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
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
        File file = new File(PATH,"Activity.xml");
        if(file.exists()){
            file.delete();
        }
        Toast.makeText(mContext,"delete share prefrence success",Toast.LENGTH_SHORT).show();
    }
}
