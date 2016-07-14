package com.example.myapp.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.adapter.MyWifiListAdapter;
import com.example.myapp.util.WifiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanru.zhang on 16/7/13.
 * Email:yanru.zhang@renren-inc.com
 */
public class WifiTestActivity extends Activity implements View.OnClickListener{
    private ListView listView;
    private CheckBox checkBox;
    private TextView stateTv;
    private WifiUtils wifiUtils;
    private List<ScanResult> scanResultList = new ArrayList<ScanResult>();
    private WifiInfo wifiInfo;
    private MyWifiListAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        NetworkConnectChangedReceiver networkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkConnectChangedReceiver,filter);
        wifiUtils = new WifiUtils(this);
        listView = (ListView) findViewById(R.id.lv);
        checkBox = (CheckBox) findViewById(R.id.check_box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    wifiUtils.openWifi();
                }else{
                    wifiUtils.closeWifi();
                }
            }
        });
        stateTv =(TextView) findViewById(R.id.state_tv);
        stateTv.setText(wifiUtils.checkWifiOpenState());
        scanResultList = wifiUtils.scan();
        wifiInfo = wifiUtils.getmWifiInfo();
        adapter = new MyWifiListAdapter(scanResultList,wifiInfo,this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    public class NetworkConnectChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这个监听wifi的打开与关闭，与wifi的连接无关
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                Log.d("zyr", "wifiState" + wifiState);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        stateTv.setText("wifi disabled");
                        checkBox.setChecked(false);
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        stateTv.setText("wifi disabling");
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        stateTv.setText("wifi abled");
                        checkBox.setChecked(true);
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        stateTv.setText("wifi abling");
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        stateTv.setText("wifi unknow");
                        break;

                }
            }
            // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
            // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (null != parcelableExtra) {
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    NetworkInfo.State state = networkInfo.getState();
                    boolean isConnected = state == NetworkInfo.State.CONNECTED;//当然，这边可以更精确的确定状态
                    Log.d(this.getClass().getSimpleName(), "isConnected" + isConnected);
                    if (isConnected) {
                    } else {

                    }
                }
            }
            //这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
            //最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
            // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (info != null) {
                    if (NetworkInfo.State.CONNECTED == info.getState()) {

                    } else if (NetworkInfo.State.DISCONNECTING == info.getState()) {

                    }
                }
            }

            // 这个监听wifi的连接状态
            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (null != parcelableExtra) {
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    NetworkInfo.State state = networkInfo.getState();
                    if (state == NetworkInfo.State.CONNECTED) {
//                        showWifiCconnected(context);
                    }
                    /**else if(state==State.DISCONNECTED){
                     showWifiDisconnected(context);
                     }*///昨天写的这个方法，在坐地铁的时候发现，如果地铁上有无效的wifi站点，手机会自动连接，但是连接失败后还是会接到广播，所以不能用了
                }
            }
        }
    }
}
