package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.MultiAutoCompleteTextView;

import com.example.myapp.R;
import com.example.myapp.adapter.AutoTextAdapater;
import com.example.myapp.adapter.CommonAdapter;
import com.example.myapp.view.AutoCompleteEmailViewWithClearBtn;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-1-8
 * Time: 下午5:40
 * Email: yanru.zhang@renren-inc.com
 */
public class AutoCompleteEdittextActivity extends Activity implements TextWatcher{
    private AutoCompleteTextView autoCompleteTextView;
    private AutoCompleteEmailViewWithClearBtn emailViewWithClearBtn;
    private ArrayAdapter<String> arrayAdapter;
    private AutoTextAdapater autoTextAdapater;
    private CommonAdapter commonAdapter;
    private ArrayList<String> arrayList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auto_complete_layout);
        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.auto_complete_text);
        emailViewWithClearBtn = (AutoCompleteEmailViewWithClearBtn)findViewById(R.id.email);
        autoCompleteTextView.addTextChangedListener(this);
        emailViewWithClearBtn.addTextChangedListener(this);
        final String [] arr={"@qq.com","@gmail.com","@163.com"};
        arrayList.add("@qq.com");
        arrayList.add("@gmail.com");
        arrayList.add("@163.com");
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
//        autoTextAdapater = new AutoTextAdapater(arr,AutoCompleteEdittextActivity.this);
        commonAdapter = new CommonAdapter(this);

        autoCompleteTextView.setAdapter(commonAdapter);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("zyr", "onTextChanged::::" + s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("zyr", "afterTextChanged::::" + s.toString());
                String input = s.toString();
                //时时动态的跟新适配器中的数据
                commonAdapter.arrayList.clear();
                if (input.length() > 0) {
                    for (int i = 0; i < arrayList.size(); ++i) {
                        commonAdapter.arrayList.add(input + arrayList.get(i));
                    }
                }
                commonAdapter.notifyDataSetChanged();
                autoCompleteTextView.showDropDown();
            }
        });
        autoCompleteTextView.setThreshold(1); //使得在输入n个字符之后便开启自动完成
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(emailViewWithClearBtn.getText().length() > 0 && autoCompleteTextView.getText().length()>0){
            Log.d("zyr","true" + "  " + emailViewWithClearBtn.getText());
        }else{
            Log.d("zyr","false");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
