package com.example.myapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapp.Model.Document;
import com.example.myapp.Model.Member;
import com.example.myapp.Model.SearchResult;
import com.example.myapp.Model.TaskModel;
import com.example.myapp.Model.Transaction;
import com.example.myapp.R;

import java.util.ArrayList;

/**
 * Created by zyr
 * DATE: 16-1-5
 * Time: 下午3:57
 * Email: yanru.zhang@renren-inc.com
 */
public class GlobalSearchAdapter extends BaseAdapter {
    private SearchResult searchResult;
    private Context context;
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private ArrayList<Document> documents = new ArrayList<Document>();
    private ArrayList<TaskModel> taskModels = new ArrayList<TaskModel>();
    private ArrayList<Member> members = new ArrayList<Member>();

    final int VIEW_TYPE_COUNT = 5;
    final int TYPE_TRANS = 0;
    final int TYPE_DOCS = 1;
    final int TYPE_TASKS = 2;
    final int TYPE_MEMBERS = 3;
    final int TYPE_TAG_TITLE = 4;

    String[] titles = {"Trans","Documents","Tasks","Members"};

    public GlobalSearchAdapter(SearchResult searchResult,Context context) {
        this.searchResult = searchResult;
        if(searchResult!=null){
            if(searchResult.transactions!=null && searchResult.transactions.size()>0){
                Transaction transaction = new Transaction();
                transaction.type = 4;
                transactions.add(transaction);
                transactions.addAll(searchResult.transactions);
            }else{
                transactions = new ArrayList<Transaction>();
            }

            if(searchResult.documents!=null && searchResult.documents.size()>0){
                Document document = new Document();
                document.type = 4;
                documents.add(document);
                documents.addAll( searchResult.documents);
            }else{
                documents = new ArrayList<Document>();
            }

            if(searchResult.taskModels!=null && searchResult.taskModels.size()>0){
                TaskModel taskModel = new TaskModel();
                taskModel.type = 4;
                taskModels.add(taskModel);
                taskModels.addAll(searchResult.taskModels);
            }else{
                taskModels = new ArrayList<TaskModel>();
            }

            if(searchResult.members!=null && searchResult.members.size()>0){
                Member member = new Member();
                member.type = 4;
                members.add(member);
                members.addAll(searchResult.members);
            }else{
                members = new ArrayList<Member>();
            }
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return transactions.size() + documents.size() + taskModels.size() + members.size();
    }

    @Override
    public Object getItem(int position) {
       int type = getItemViewType(position);
        int p = getItemViewPosition(position);
        switch (type){
            case TYPE_TRANS:
                return transactions.get(p);
            case TYPE_DOCS:
                return documents.get(p);
            case TYPE_TASKS:
                return taskModels.get(p);
            case TYPE_MEMBERS:
                return members.get(p);
            default:
                return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        int p = getItemViewPosition(position);
        Log.d("zyr","************************position::::"+position);
        Log.d("zyr","************************type::::::" + type);
        Log.d("zyr","************************p::::::" + p);
        ViewHolder0 viewHolder0 = null;
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        ViewHolder3 viewHolder3 = null;
        ViewHolderTitle viewHolderTitle = null;
        if(convertView == null){

            switch (type){
                case TYPE_TAG_TITLE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_view_adapter_title,null);
                    viewHolderTitle = new ViewHolderTitle(convertView);
                    convertView.setTag(viewHolderTitle);
                    break;
                case TYPE_TRANS:
                    //inflate xml
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_view_adapter_item0,null);
                    viewHolder0 = new ViewHolder0(convertView);
                    convertView.setTag(viewHolder0);
                    break;
                case TYPE_DOCS:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_view_adapter_item1,null);
                    viewHolder1 = new ViewHolder1(convertView);
                    convertView.setTag(viewHolder1);
                    break;
                case TYPE_TASKS:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_view_adapter_item2,null);
                    viewHolder2 = new ViewHolder2(convertView);
                    convertView.setTag(viewHolder2);
                    break;
                case TYPE_MEMBERS:
                    convertView = LayoutInflater.from(context).inflate(R.layout.list_view_adapter_item3,null);
                    viewHolder3 = new ViewHolder3(convertView);
                    convertView.setTag(viewHolder3);
                    break;
            }

        }else{

            switch (type){
                case TYPE_TAG_TITLE:
                    viewHolderTitle = (ViewHolderTitle) convertView.getTag();
                    break;
                case TYPE_TRANS:
                    //get view holder
                    viewHolder0 =(ViewHolder0) convertView.getTag();
                    break;
                case TYPE_DOCS:
                    viewHolder1 =(ViewHolder1) convertView.getTag();
                    break;
                case TYPE_TASKS:
                    viewHolder2 =(ViewHolder2) convertView.getTag();
                    break;
                case TYPE_MEMBERS:
                    viewHolder3 =(ViewHolder3) convertView.getTag();
                    break;
            }


        }

        switch (type){
            case TYPE_TAG_TITLE:
                viewHolderTitle.textView.setText(titles[getItemTitleType(position)]);
                break;
            case TYPE_TRANS:
                //set text
                viewHolder0.textView.setText(transactions.get(p).transactionName);
                break;
            case TYPE_DOCS:
                viewHolder1.textView.setText(documents.get(p).documentName);
                break;
            case TYPE_TASKS:
                viewHolder2.textView.setText(taskModels.get(p).taskName);
                break;
            case TYPE_MEMBERS:
                viewHolder3.textView.setText(members.get(p).memberName);
                break;
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return getTypeAndPosition(position).get(0);
    }

    private ArrayList<Integer> getTypeAndPosition(int position){
        Log.d("zyr","getTypeAndPosition:::POSITION:::" + position);

        ArrayList<Integer> integers = new ArrayList<Integer>();
        int p ;
        if(position < transactions.size()){
            p = position;
            Log.d("zyr","getTypeAndPosition:::P:::" + p);
            if(p ==0 ){
                integers.add(TYPE_TAG_TITLE);//type
                integers.add(p);//position
                integers.add(TYPE_TRANS);//title tag
            }else{
                integers.add(TYPE_TRANS);
                integers.add(p);
                integers.add(TYPE_TRANS);//title tag
            }
            return  integers;
        }else if(position < ( transactions.size() + documents.size() )){
            p = position - transactions.size();
            Log.d("zyr","getTypeAndPosition:::P:::" + p);

            if( p ==0){
                integers.add(TYPE_TAG_TITLE);
                integers.add(p);
                integers.add(TYPE_DOCS);//title tag
            }else{
                integers.add(TYPE_DOCS);
                integers.add(p);
                integers.add(TYPE_DOCS);//title tag
            }

            return  integers;
        }else if(position < transactions.size() + documents.size() + taskModels.size() ){
            p = position - transactions.size() - documents.size();
            Log.d("zyr","getTypeAndPosition:::P:::" + p);

            if( p == 0){
                integers.add(TYPE_TAG_TITLE);
                integers.add(p);
                integers.add(TYPE_TASKS);//title tag
            }else{
                integers.add(TYPE_TASKS);
                integers.add(p);
                integers.add(TYPE_TASKS);//title tag
            }

            return  integers;
        }else{
            p = position - transactions.size() - documents.size() - taskModels.size();
            Log.d("zyr","getTypeAndPosition:::P:::" + p);

            if( p ==0){
                integers.add(TYPE_TAG_TITLE);
                integers.add(p);
                integers.add(TYPE_MEMBERS);//title tag
            }else{
                integers.add(TYPE_MEMBERS);
                integers.add(p);
                integers.add(TYPE_MEMBERS);//title tag
            }
            return  integers;
        }
    }

    private int getItemViewPosition(int position){
        return getTypeAndPosition(position).get(1);
    }
    private int getItemTitleType(int position){
        return getTypeAndPosition(position).get(2);
    }



    class ViewHolder0{
        private TextView textView;
        public ViewHolder0(View view) {
            textView = (TextView)view.findViewById(R.id.textView);
        }
    }
    class ViewHolder1{
        private TextView textView;
        public ViewHolder1(View view) {
            textView = (TextView)view.findViewById(R.id.textView);

        }
    }
    class ViewHolder2{
        private TextView textView;
        public ViewHolder2(View view) {
            textView = (TextView)view.findViewById(R.id.textView);

        }
    }
    class ViewHolder3{
        private TextView textView;
        public ViewHolder3(View view) {
            textView = (TextView)view.findViewById(R.id.textView);

        }
    }
    class ViewHolderTitle{
        private TextView textView;
        public ViewHolderTitle(View view) {
            textView = (TextView)view.findViewById(R.id.title_text);
        }
    }



}
