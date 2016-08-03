package com.example.myapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.db.NoteBDManager;
import com.example.myapp.db.NoteBean;
import com.example.myapp.view.WrapListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanru.zhang on 16/8/1.
 * Email:yanru.zhang@renren-inc.com
 */
public class MyNotesActivity extends Activity {
    private List<NoteBean> goingDoLists = new ArrayList<NoteBean>();
    private List<NoteBean> completedLists = new ArrayList<NoteBean>();
    private WrapListView goingDoLv,completedLv;
    private GoingDoAdapter goingDoAdapter;
    private CompletedAdapter completedAdapter;
    private Button addGoingDoBtn,addCompleteBtn;
    private AlertDialog myDialog;
    private NoteBDManager noteBDManager;
    private long deleteNoteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_notes);
        noteBDManager = new NoteBDManager(this);
        myDialog = new AlertDialog.Builder(this).
                setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noteBDManager.delete(deleteNoteId);
                        getFromDB();
                    }
                }).
                create();
        goingDoLv = (WrapListView) findViewById(R.id.going_to_do_notes);
        completedLv = (WrapListView) findViewById(R.id.completed_notes);
        goingDoAdapter = new GoingDoAdapter(goingDoLists);
        goingDoLv.setAdapter(goingDoAdapter);
        goingDoLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!myDialog.isShowing()){
                    deleteNoteId = goingDoLists.get(position).id;
                    myDialog.show();
                }
                return false;
            }
        });
        goingDoLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyNotesActivity.this,AddNotesActivity.class);
                intent.putExtra("type",AddNotesActivity.TYPE_EDIT);
                intent.putExtra("note_bean",goingDoLists.get(position));
                startActivity(intent);
            }
        });
        completedAdapter = new CompletedAdapter(completedLists);
        completedLv.setAdapter(completedAdapter);
        completedLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!myDialog.isShowing()){
                    deleteNoteId = completedLists.get(position).id;
                    myDialog.show();
                }
                return false;
            }
        });
        completedLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyNotesActivity.this,AddNotesActivity.class);
                intent.putExtra("type",AddNotesActivity.TYPE_EDIT);
                intent.putExtra("note_bean",completedLists.get(position));
                startActivity(intent);
            }
        });
        addGoingDoBtn = (Button) findViewById(R.id.add_btn_to_going);
        addGoingDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNotesActivity.this,AddNotesActivity.class);
                intent.putExtra("type",AddNotesActivity.TYPE_ADD_GOING);
                startActivity(intent);
            }
        });
        addCompleteBtn = (Button) findViewById(R.id.add_btn_to_completed);
        addCompleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNotesActivity.this,AddNotesActivity.class);
                intent.putExtra("type",AddNotesActivity.TYPE_ADD_COMPLETED);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFromDB();
    }

    private void getFromDB(){
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    private class MyAsyncTask extends AsyncTask<String,Integer,List<NoteBean>>{

        @Override
        protected List<NoteBean> doInBackground(String... params) {
            return noteBDManager.query();
        }

        @Override
        protected void onPostExecute(List<NoteBean> noteBeen) {
            super.onPostExecute(noteBeen);
            if(noteBeen!=null){
                Log.d("zyr","db notes:" + noteBeen.toString());
                goingDoLists.clear();
                completedLists.clear();

                for(int i=0;i<noteBeen.size();i++){
                    if(noteBeen.get(i).status == 1){
                        goingDoLists.add(noteBeen.get(i));
                    }else if(noteBeen.get(i).status == 2){
                        completedLists.add(noteBeen.get(i));
                    }
                }

                goingDoAdapter.setData(goingDoLists);
                completedAdapter.setData(completedLists);
            }
        }
    }

    /**
     * Adapter
     */
    private class GoingDoAdapter extends BaseAdapter{

        private List<NoteBean> goingDos = new ArrayList<NoteBean>();

        public GoingDoAdapter(List<NoteBean> goingDos) {
            this.goingDos.addAll(goingDos);
        }

        public void setData(List<NoteBean> goingDos){
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
            viewHolder.tv.setText(goingDos.get(position).note);
            return convertView;
        }

        private class ViewHolder{
            public View rootView;
            public TextView tv;


            public ViewHolder(View rootView) {
                this.rootView = rootView;
                tv = (TextView) rootView.findViewById(R.id.tv);
            }
        }
    }

    private class CompletedAdapter extends BaseAdapter{

        private List<NoteBean> completeds = new ArrayList<NoteBean>();

        public CompletedAdapter(List<NoteBean> completeds) {
            this.completeds.addAll(completeds);
        }

        public void setData(List<NoteBean> completeds){
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
            viewHolder.tv.setText(completeds.get(position).note);
            return convertView;
        }

        private class ViewHolder{
            public View rootView;
            public TextView tv;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                tv = (TextView) rootView.findViewById(R.id.tv);
            }
        }
    }
}
