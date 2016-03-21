package com.example.myapp.util;

import android.content.Context;

/**
 * Created by zyr
 * DATE: 16-3-21
 * Time: 下午1:52
 * Email: yanru.zhang@renren-inc.com
 */
public class Methods {
    public static int computePixelsWithDensity(Context context,int dp) {
        float scale;
        scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp *scale + 0.5);
    }

    public static int computePixelsTextSize(Context context,int sp) {
        float scale;
        scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5);
    }
}
