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
        Log.d("zyr","size:" + scanResults.size());
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
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.wifi_list_adapter,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }


//     ScanResult类的成员变量：
//     1.String SSID：网络名称。
//     2.WifiSsid wifiSsid：Ascii编码的SSID。
//     3.String BSSID：AP的地址。
//     4.String capabilities：描述认证、密钥管理以及加密方式，通过AP来支持。
//     5.int level：发现的信号等级，单位是dBm，也被称作RSSI，通过调用WifiManager的caculateSignalLevel方法将该数值进行换算，换算成一个绝对信号等级呈现给用户。
//     6.int frequency：channel（客户端与AP通信的信道）的频率，单位MHz。
//     7.long timestamp:从启动开始到该扫描记录最后一次被发现经过的微秒数。
//     8.long seen：代表该扫描结果最后一次被发现的日期，从1970年到该日期的毫秒数。
//     9.int isAutoJoinCandidate：代表该扫描结果是否是一个有效的自动加入的候选项。
//     10.int autoJoinStatus：表示join的状态。
//     11.numIpConfigFailure：IP配置失败的次数。
//     12.long blackListTimestamp：我们最后一次将该ScanResult加入黑名单的时间。
//     13.boolean untrusted：如果为true，说明该扫描结果并不是用户已保存配置中的部分。
//     14.int numConnection：我们连接到该扫描结果的次数。
//     15.int numUsage：自动加入使用该扫描结果的次数。
//     16.int distanceCm：到AP的大概距离，单位：米，如果无法获取该值，则该值为UNSPECIFIED。
//     17.int distanceSdCm：到AP距离的标准差，若无法获取该值，则该值为UNSPECIFIED。
//     18.public InformationElement informationElements[]：在信号中发现的信息元素。


//        一般WIFI加密有几种方式：
//        (1).WPA-PSK/WPA2-PSK(目前最安全家用加密)
//        (2).WPA/WPA2(较不安全)
//        (3).WEP(安全较差)
//        (4).EAP(迄今最安全的)

        ScanResult scanResult = scanResults.get(position);
        viewHolder.strengthTv.setText(scanResult.level + "");
        if(wifiInfo.getBSSID().equals(scanResult.BSSID)){
            viewHolder.nameTv.setTextColor(mContext.getResources().getColor(R.color.primary));
        }else{
            viewHolder.nameTv.setTextColor(mContext.getResources().getColor(R.color.gray_333333));
        }
        viewHolder.nameTv.setText(scanResult.SSID);
        viewHolder.addressTv.setText(scanResult.BSSID);
        viewHolder.stateTv.setText(scanResult.capabilities);
        viewHolder.positionTv.setText("(" + position + ")");

        return convertView;
    }

    public static class ViewHolder{
        private TextView strengthTv;
        private TextView nameTv;
        private TextView addressTv;
        private TextView stateTv;
        private TextView positionTv;

        public ViewHolder(View rootView) {
            strengthTv = (TextView) rootView.findViewById(R.id.wifi_strength);
            nameTv = (TextView) rootView.findViewById(R.id.wifi_name);
            addressTv = (TextView) rootView.findViewById(R.id.wifi_address);
            stateTv = (TextView) rootView.findViewById(R.id.wifi_state);
            positionTv = (TextView) rootView.findViewById(R.id.position);

        }
    }


}
