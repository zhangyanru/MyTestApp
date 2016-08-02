package com.example.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.view.WrapListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyNotesActivity extends Activity {
    private List<String> goingDoLists = new ArrayList<String>();
    private List<String> completedLists = new ArrayList<String>();
    private WrapListView goingDoLv,completedLv;
    private GoingDoAdapter goingDoAdapter;
    private CompletedAdapter completedAdapter;
    private Button addGoingDoBtn,addCompleteBtn;

    public static final int REQUEST_ADD_GOING = 1;
    public static final int REQUEST_ADD_COMPLETED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_notes);
        goingDoLv = (WrapListView) findViewById(R.id.going_to_do_notes);
        completedLv = (WrapListView) findViewById(R.id.completed_notes);
        goingDoAdapter = new GoingDoAdapter(goingDoLists);
        goingDoLv.setAdapter(goingDoAdapter);
        completedAdapter = new CompletedAdapter(completedLists);
        completedLv.setAdapter(completedAdapter);
        addGoingDoBtn = (Button) findViewById(R.id.add_btn_to_going);
        addGoingDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNotesActivity.this,AddNotesActivity.class);
                startActivityForResult(intent,REQUEST_ADD_GOING);
            }
        });
        addCompleteBtn = (Button) findViewById(R.id.add_btn_to_completed);
        addCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNotesActivity.this,AddNotesActivity.class);
                startActivityForResult(intent,REQUEST_ADD_COMPLETED);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_ADD_COMPLETED:
                    completedLists.add(data.getStringExtra("note"));
                    completedAdapter.setData(completedLists);
                    break;
                case REQUEST_ADD_GOING:
                    goingDoLists.add(data.getStringExtra("note"));
                    goingDoAdapter.setData(goingDoLists);
                    break;
            }
        }
    }

    private class GoingDoAdapter extends BaseAdapter{

        private List<String> goingDos = new ArrayList<String>();

        public GoingDoAdapter(List<String> goingDos) {
            this.goingDos.addAll(goingDos);
        }

        public void setData(List<String> goingDos){
            if(goingDos==null)return;
            this.goingDos.clear();
            this.goingDos.addAll(goingDos);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return goingDos.size();
        }

        @Override
        public Object getItem(int position) {
            return goingDos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.adapter_going_do,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.tv.setText(goingDos.get(position));
            viewHolder.moveTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String item = goingDos.get(position);

                    goingDoLists.remove(position);
                    goingDoAdapter.setData(goingDoLists);

                    completedLists.add(item);
                    completedAdapter.setData(completedLists);

                }
            });
            return convertView;
        }

        private class ViewHolder{
            public View rootView;
            public TextView tv,moveTv;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                tv = (TextView) rootView.findViewById(R.id.tv);
                moveTv = (TextView) rootView.findViewById(R.id.move_btn);
            }
        }
    }

    private class CompletedAdapter extends BaseAdapter{

        private List<String> completeds = new ArrayList<String>();

        public CompletedAdapter(List<String> completeds) {
            this.completeds.addAll(completeds);
        }

        public void setData(List<String> completeds){
            if(completeds==null)return;
            this.completeds.clear();
            this.completeds.addAll(completeds);
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return completeds.size();
        }

        @Override
        public Object getItem(int position) {
            return completeds.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.adapter_going_do,null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.tv.setText(completeds.get(position));
            viewHolder.moveTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String item = completeds.get(position);

                    completedLists.remove(position);
                    completedAdapter.setData(goingDoLists);

                    goingDoLists.add(item);
                    goingDoAdapter.setData(goingDoLists);

                }
            });
            return convertView;
        }

        private class ViewHolder{
            public View rootView;
            public TextView tv,moveTv;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                tv = (TextView) rootView.findViewById(R.id.tv);
                moveTv = (TextView) rootView.findViewById(R.id.move_btn);
            }
        }
    }
}
