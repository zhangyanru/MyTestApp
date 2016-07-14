package com.example.myapp.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanru.zhang on 16/7/13.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyWifiListAdapter extends BaseAdapter {
    private List<ScanResult> scanResults = new ArrayList<ScanResult>();
    private WifiInfo wifiInfo;
    private Context mContext;

    public MyWifiListAdapter(List<ScanResult> scanResults,WifiInfo wifiInfo, Context mContext) {
        this.scanResults.clear();
        this.scanResults.addAll(scanResults);
        this.wifiInfo = wifiInfo;
        this.mContext = mContext;
    }

    public void setData(List<ScanResult> scanResults, WifiInfo wifiInfo){
        this.scanResults.clear();
        this.scanResults.addAll(scanResults);
        this.wifiInfo = wifiInfo;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return scanResults.size();
    }

    @Override
    public Object getItem(int position) {
        return scanResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(viewHolder == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.wifi_list_adapter,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        //ScanResult的重要属性有一下几个：
        //BSSID 接入点的地址
        //SSID 网络的名字，唯一区别WIFI网络的名字
        //Capabilities 网络接入的性能
        //Frequency 当前WIFI设备附近热点的频率(MHz)
        //Level 所发现的WIFI网络信号强度

        ScanResult scanResult = scanResults.get(position);
        viewHolder.strengthTv.setText(scanResult.level + "");
        Log.d("zyr","wifiinfo ssid:" + wifiInfo.getSSID() + " " + "result ssid:" + scanResult.SSID);
        if(wifiInfo.getSSID().equals(scanResult.SSID)){
            viewHolder.nameTv.setTextColor(mContext.getResources().getColor(R.color.primary));
        }else{
            viewHolder.nameTv.setTextColor(mContext.getResources().getColor(R.color.gray_333333));
        }
        viewHolder.nameTv.setText(scanResult.SSID);
        viewHolder.addressTv.setText(scanResult.BSSID);
        viewHolder.stateTv.setText(scanResult.describeContents()+"");
        return convertView;
    }

    public static class ViewHolder{
        private TextView strengthTv;
        private TextView nameTv;
        private TextView addressTv;
        private TextView stateTv;

        public ViewHolder(View rootView) {
            strengthTv = (TextView) rootView.findViewById(R.id.wifi_strength);
            nameTv = (TextView) rootView.findViewById(R.id.wifi_name);
            addressTv = (TextView) rootView.findViewById(R.id.wifi_address);
            stateTv = (TextView) rootView.findViewById(R.id.wifi_state);

        }
    }


}
