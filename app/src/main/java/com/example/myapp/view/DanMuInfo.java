package com.example.myapp.view;

/**
 * Created by yanru.zhang on 16/9/20.
 * Email:yanru.zhang@renren-inc.com
 */
public class DanMuInfo {
    public String data;
    public int x,y,v,textSize,textColor;

    public DanMuInfo(String data) {
        this.data = data;
    }

    public DanMuInfo(String data, int x, int y, int v, int textSize) {
        this.data = data;
        this.x = x;
        this.y = y;
        this.v = v;
        this.textSize = textSize;
    }
}
