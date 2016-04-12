package com.example.myapp.github.pinnedheaderlistview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.util.Methods;

public class PinnedHeaderListViewMainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinned_header_lv_activity_main);
        PinnedHeaderListView listView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout header1 = (LinearLayout) inflator.inflate(R.layout.list_item, null);
        ((TextView) header1.findViewById(R.id.textItem)).setText("HEADER 1");
        LinearLayout header2 = (LinearLayout) inflator.inflate(R.layout.list_item, null);
        ((TextView) header2.findViewById(R.id.textItem)).setText("HEADER 2");
        LinearLayout footer = (LinearLayout) inflator.inflate(R.layout.list_item, null);
        ((TextView) footer.findViewById(R.id.textItem)).setText("FOOTER");
        listView.addHeaderView(header1);
        listView.addHeaderView(header2);
        listView.addFooterView(footer);
        TestSectionedAdapter sectionedAdapter = new TestSectionedAdapter();
        listView.setAdapter(sectionedAdapter);
        listView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onSectionItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                Methods.toast(PinnedHeaderListViewMainActivity.this,"onItemClick section :" + section + " position :" + position);
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
                Methods.toast(PinnedHeaderListViewMainActivity.this,"onSectionClick section :" + section);
            }

            @Override
            public void onHeaderClick(AdapterView<?> adapterView, View view, int position, long id) {
                Methods.toast(PinnedHeaderListViewMainActivity.this,"onHeaderClick header :" + position);
            }

            @Override
            public void onFooterClick(AdapterView<?> adapterView, View view, int position, long id) {
                Methods.toast(PinnedHeaderListViewMainActivity.this,"onFooterClick footer :" + position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pinned_header_lv_activity_main, menu);
        return true;
    }
}
