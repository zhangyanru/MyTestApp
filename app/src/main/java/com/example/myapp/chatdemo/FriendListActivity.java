package com.example.myapp.chatdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.myapp.Model.ChatMessage;
import com.example.myapp.R;
import com.example.myapp.activity.BaseActivity;
import com.example.myapp.util.PrefrenceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanru.zhang on 16/6/16.
 * Email:yanru.zhang@renren-inc.com
 */
public class FriendListActivity extends BaseActivity {
    private ListView listView;
    private FriendListAdapter adapter;
    private List<String> friends = new ArrayList<>();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            JSONObject jsonObject = JSONObject.parseObject(data);
            if(jsonObject.getIntValue("type") == ChatMessage.FRIEND_LIST){
                Toast.makeText(FriendListActivity.this,jsonObject.getString("status_msg"),Toast.LENGTH_SHORT).show();
                if(jsonObject.getIntValue("status") == ChatMessage.STATUS_SUCCESS){
                    JSONArray array = jsonObject.getJSONArray("friends");
                    Log.d("friend list:",array.toJSONString());
                    for(int i=0;i<array.size();i++){
                        JSONObject jsonObject1 = array.getJSONObject(i);
                        String account = jsonObject1.getString("account");
                        String ip = jsonObject1.getString("ip");
                        friends.add(account + " " + ip);
                    }
                    adapter.setData(friends);
                }
            }
        }
    };
    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.lv);
        adapter = new FriendListAdapter(FriendListActivity.this,friends);
        listView.setAdapter(adapter);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_friend_list;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(receiver,new IntentFilter(SocketService.RECEIVE_ACTION));
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    protected void loadData() {
        JSONObject chatMessage = new JSONObject();
        chatMessage.put("account", PrefrenceManager.getInstance().getAccount());
        chatMessage.put("type", ChatMessage.FRIEND_LIST);

        Intent intent = new Intent(SocketService.SEND_ACTION);
        intent.putExtra("data",chatMessage.toJSONString());
        sendBroadcast(intent);
    }
}
