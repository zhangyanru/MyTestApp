

package com.example.myapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SectionedDemo extends Activity {
	private static String[] items={"lorem", "ipsum", "dolor","purus"};
	private ListView listView;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.section_layout);
		listView = (ListView)findViewById(R.id.listview);
		adapter.addSection("Original", new ArrayAdapter<String>(this,
				R.layout.array_adapter_0,
				items));
		
		List<String> list= Arrays.asList(items);
		
		Collections.shuffle(list);
		ArrayAdapter arrayAdapter2 =  new ArrayAdapter<String>(this,
				R.layout.array_adapter_1,
				list);
		adapter.addSection("Shuffled", arrayAdapter2);

		list= Arrays.asList(items);
		
		Collections.shuffle(list);

		adapter.addSection("Re-shuffled", new ArrayAdapter<String>(this,
				R.layout.array_adapter_2,
				new ArrayList<String>()));
		adapter.addSection("ZYR",new MyAdapter(items));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(SectionedDemo.this, position + "", Toast.LENGTH_SHORT).show();
				Log.d("zyr", "position-------------------" + position);
			}
		});
	}
	
	SectionedAdapter adapter=new SectionedAdapter() {
		protected View getHeaderView(String caption, int index,View convertView,ViewGroup parent) {
			TextView result=(TextView)convertView;
			
			if (convertView==null) {
				result=(TextView)getLayoutInflater().inflate(R.layout.header, null);
			}
			
			result.setText(caption);
			
			return(result);
		}
	};

	class MyAdapter extends BaseAdapter {
		private String[] arrayList;

		public MyAdapter(String[] arrayList) {
			this.arrayList = arrayList;
		}

		@Override
		public int getCount() {
			return arrayList.length;
		}

		@Override
		public Object getItem(int position) {
			return arrayList[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if(convertView == null){
				convertView = LayoutInflater.from(SectionedDemo.this).inflate(R.layout.array_adapter_3,null);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder)convertView.getTag();
			}
			viewHolder.textView.setText(arrayList[position]);
			viewHolder.textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(SectionedDemo.this, position + "", Toast.LENGTH_SHORT).show();
				}
			});
			return convertView;
		}

		class ViewHolder{
			private TextView textView;

			public ViewHolder(View view) {
				textView = (TextView)view.findViewById(R.id.text3);
			}
		}
	}
}
