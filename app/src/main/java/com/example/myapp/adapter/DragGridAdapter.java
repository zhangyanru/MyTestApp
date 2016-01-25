package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.myapp.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by zyr
 * DATE: 16-1-20
 * Time: 下午3:06
 * Email: yanru.zhang@renren-inc.com
 */
public  class DragGridAdapter extends ArrayAdapter<String> {

    public DragGridAdapter(Context context,  List<String> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.drag_grid_item, null);
        }
        try {
            //跟据文件名获取资源文件中的图片资源
            Field f = R.drawable.class.getDeclaredField(getItem(position));
            int i = f.getInt(R.drawable.class);
            ImageView imageview = (ImageView)view.findViewById(R.id.drag_grid_item_image);
            imageview.setImageResource(i);

        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//         }
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return view;
    }

}