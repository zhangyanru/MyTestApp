package com.example.myapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.util.Methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zyr
 * DATE: 16-4-14
 * Time: 下午4:14
 * Email: yanru.zhang@renren-inc.com
 */
public class CommonRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewAdapter.ViewHolder> {

    private Context mContext;

    private List<String> strings = new ArrayList<>();

    private Random random;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        public void onItemClick(int position);
    }

    public CommonRecyclerViewAdapter(Context context,List<String> strings){
        this.mContext = context;
        this.strings = new ArrayList<>(strings);
        random = new Random();
    }

    public void insertData(int position,String s){
        strings.add(position,s);
        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public void deleteData(int position){
        strings.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_view_recycler_view_item,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(strings.get(position));
        ViewGroup.LayoutParams layoutParams = holder.textView.getLayoutParams();
//        switch (position){
//            case 0:
//                layoutParams.height = 100;
//                break;
//            case 1:
//                layoutParams.height = 200;
//                break;
//            case 2:
//                layoutParams.height = 300;
//                break;
//            case 3:
//                layoutParams.height = 150;
//                break;
//            case 4:
//                layoutParams.height = 250;
//                break;
//            case 5:
//                layoutParams.height = 210;
//                break;
//            case 6:
//                layoutParams.height = 130;
//                break;
//            case 7:
//                layoutParams.height = 180;
//                break;
//            case 8:
//                layoutParams.height = 150;
//                break;
//            case 9:
//                layoutParams.height = 250;
//                break;
//            case 10:
//                layoutParams.height = 210;
//            case 11:
//                layoutParams.height = 250;
//                break;
//            case 12:
//                layoutParams.height = 210;
//            case 13:
//                layoutParams.height = 100;
//                break;
//            case 14:
//                layoutParams.height = 200;
//                break;
//            case 15:
//                layoutParams.height = 300;
//                break;
//            case 16:
//                layoutParams.height = 150;
//                break;
//            case 17:
//                layoutParams.height = 250;
//                break;
//            case 18:
//                layoutParams.height = 210;
//                break;
//            case 19:
//                layoutParams.height = 130;
//                break;
//        }
//        layoutParams.height = random.nextInt(400) + 100;
//        holder.textView.setLayoutParams(layoutParams);
        holder.itemRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public List<String> getStrings() {
        return strings;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View itemRootView;

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemRootView = itemView;
            textView = (TextView) itemView.findViewById(R.id.list_view_text);
        }
    }
}
