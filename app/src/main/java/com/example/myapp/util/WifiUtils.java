package com.example.myapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by yanru.zhang on 16/7/13.
 * Email:yanru.zhang@renren-inc.com
 */
public class WifiUtils {
    private Context mContext;
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo; //当前连接wifi的信息
    private ConnectivityManager mConnectivityManager;

    public WifiUtils(Context context) {
        mContext = context;
        mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
        mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 重新获取当前wifi的信息
     */
    public void againGetWifiInfo(){
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    /**
     * 是否开启wifi
     * @return
     */
    public boolean isWifiEnabled(){
        return mWifiManager.isWifiEnabled();
    }

    /**
     * 是否已经连接wifi
     * @return
     */
    public boolean isConnectioned(){
        return mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ?true :false;
    }

    /**
     * 获取wifi连接的状态
     * @return
     */
    public State getConnectState(){
        return mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
    }

    /**
     * 设置wifi可用
     */
    public void openWifi(){
        if(!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 设置wifi不可用
     */
    public void closeWifi(){
        if(mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(false);
        }
    }

    /**
     * 检查当前wifi的网卡状态
     */
    public String checkWifiOpenState(){
        String string = null;
        switch (mWifiManager.getWifiState()){
            case WifiManager.WIFI_STATE_DISABLED:
                string = "wifi已经关闭";
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                string = "正在关闭wifi";
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                string = "wifi已经打开";
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                string = "正在打开wifi";
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                string = "获取不到wifi的状态";
                break;
        }
        return string;
    }

    /**
     * 扫描周边网络
     */
    public List<ScanResult> scan(){
        mWifiManager.startScan();
        return mWifiManager.getScanResults();
    }

    /**
     * 断开当前网络
     */
    public void disConnectWifi(){
        if(mWifiInfo == null){
            return;
        }
        int netId = mWifiInfo.getNetworkId();
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
        mWifiInfo = null;
    }

    /**
     * 得到当前连接的网络
     * @return
     */
    public WifiInfo getmWifiInfo() {
        return mWifiInfo;
    }

    /**
     * 添加一个网络并连接
     * @param configuration
     */
    public void addWifiAndConnect(WifiConfiguration configuration){
        int netId = mWifiManager.addNetwork(configuration);
        mWifiManager.enableNetwork(netId,true);
    }

}
