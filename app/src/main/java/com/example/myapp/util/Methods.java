package com.example.myapp.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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

    public static void toast(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x || ev.getRawX() > (x + view.getWidth()) || ev.getRawY() < y || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }
}
