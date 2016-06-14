package com.example.myapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Model.ListData;
import com.example.myapp.R;
import com.example.myapp.thread.ClientReadThread;
import com.example.myapp.thread.ClientWriteThread;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanru.zhang on 16/6/4.
 * Email:yanru.zhang@renren-inc.com
 */
public class SocketTestActivity2 extends BaseActivity {

    private TextView clientSendTv;

    private EditText editText;

    private ListView listView;

    private Handler myHandler;

    private ClientReadThread clientReadThread;

    private ClientWriteThread clientWriteThread;

    private List<ListData> datas = new ArrayList<>();

    private MsgAdapter msgAdapter;

    private Socket clientSocket;

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        clientSendTv = (TextView) findViewById(R.id.client_send_tv);
        editText = (EditText) findViewById(R.id.edit_tv);

        msgAdapter = new MsgAdapter(datas);
        listView.setAdapter(msgAdapter);

        myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                switch (msg.what){
                    case ListData.SERVER_MSG:
                        String serverMsg = bundle.getString("serverMsg");
                        Log.d("zyr","handler server:" + serverMsg);
                        datas.add(new ListData(ListData.SERVER_MSG,serverMsg));
                        msgAdapter.setData(datas);
                        break;
                    case ListData.CLIENT_MSG:
                        String clientMsg = bundle.getString("clientMsg");
                        Log.d("zyr","handler client:" + clientMsg);
                        datas.add(new ListData(ListData.CLIENT_MSG,clientMsg));
                        msgAdapter.setData(datas);
                        break;
                    case ListData.TIP_MSG:
                        String tipMsg = bundle.getString("tipMsg");
                        Log.d("zyr","handler tip:" + tipMsg);
                        datas.add(new ListData(ListData.TIP_MSG,tipMsg));
                        msgAdapter.setData(datas);
                        break;
                    case -1:
                        Toast.makeText(SocketTestActivity2.this,"connect failed",Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket("10.2.52.54",8000);
                    clientSocket.setKeepAlive(true);
                    clientSocket.setOOBInline(true);
                    clientSocket.setSoTimeout(5000); //定义心跳时间
                    Log.d("zyr","new clientThread success");
                    clientReadThread = new ClientReadThread(clientSocket,myHandler);
                    clientReadThread.start();
                    clientWriteThread = new ClientWriteThread(clientSocket,myHandler);
                    clientWriteThread.start();
                    clientWriteThread.startHeartBeatTimerTask();
                    clientWriteThread.getFriendList();
                } catch (Exception e) {
                    myHandler.sendEmptyMessage(-1);
                    e.printStackTrace();
                    Log.d("zyr",e.toString());
                }
            }
        }).start();


    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_socket_test2;
    }

    @Override
    public void initListener() {
        clientSendTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.client_send_tv:
                Log.d("zyr","click sendMsg ..." );
                if(clientWriteThread!=null && !TextUtils.isEmpty(editText.getText().toString())){
                    clientWriteThread.sendMsg(editText.getText().toString());
                }else{
                    Toast.makeText(SocketTestActivity2.this,"cannot send message",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.server_send_tv:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(clientReadThread!=null){
            clientReadThread.setConnect(false);
        }
        if(clientWriteThread!=null){
            clientWriteThread.setConnect(false);
        }
    }

    class MsgAdapter extends BaseAdapter{

        private List<ListData> listDatas = new ArrayList<>();

        public MsgAdapter(List<ListData> listDatas){
            this.listDatas.clear();
            this.listDatas.addAll(listDatas);
        }

        public void setData(List<ListData> listDatas){
            this.listDatas.clear();
            this.listDatas.addAll(listDatas);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return listDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return listDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(SocketTestActivity2.this).inflate(R.layout.adapter_socket_test2,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if(listDatas.get(position).type == ListData.SERVER_MSG){
                viewHolder.receiveTv.setVisibility(View.VISIBLE);
                viewHolder.sendTv.setVisibility(View.GONE);
                viewHolder.tipMsgTv.setVisibility(View.GONE);
                viewHolder.receiveTv.setText(listDatas.get(position).message);
            }else if(listDatas.get(position).type == ListData.CLIENT_MSG){
                viewHolder.receiveTv.setVisibility(View.GONE);
                viewHolder.sendTv.setVisibility(View.VISIBLE);
                viewHolder.tipMsgTv.setVisibility(View.GONE);
                viewHolder.sendTv.setText(listDatas.get(position).message);
            } else if(listDatas.get(position).type == ListData.TIP_MSG){
                viewHolder.receiveTv.setVisibility(View.GONE);
                viewHolder.sendTv.setVisibility(View.GONE);
                viewHolder.tipMsgTv.setVisibility(View.VISIBLE);
                viewHolder.tipMsgTv.setText(listDatas.get(position).message);
            }
            return convertView;
        }

        class ViewHolder{
            private TextView receiveTv;
            private TextView sendTv;
            private TextView tipMsgTv;
            public ViewHolder(View rootView){
                receiveTv = (TextView) rootView.findViewById(R.id.receive_msg_tv);
                sendTv = (TextView) rootView.findViewById(R.id.send_msg_tv);
                tipMsgTv = (TextView) rootView.findViewById(R.id.tip_msg_tv);
            }
        }
    }


}
