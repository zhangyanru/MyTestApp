package com.example.myapp.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import com.example.myapp.R;
import com.example.myapp.fragment.AnimFragment;
import com.example.myapp.view.DatePickerDialogWithMaxMin;

import java.util.Calendar;

/**
 * Created by admin on 15/9/8.
 */
public class MainActivity extends Activity implements View.OnClickListener{
    private Button shareweibo;
    private Button drawpath;
    private Button animation;
    private Button xListView;
    private Button gridPasswardView;
    private Button viewpager;
    private Button viewpager_fragment;
    private Button topbar;
    private Button flowlayout;
    private Button startForResult;
    private Button colorChangedCircle;
    private Button fragmentAnim;
    private Button arcMenu;
    private Button datePicker;
    private Button arcMenu2;
    private Button dragView;
    private Button slidListview;
    private Button autoSlid;
    private Button lookBigImage;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    private final static int TO_A_ACTIVITY = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        initViews();
        initListener();
    }

    public void initViews(){
        shareweibo = (Button)findViewById(R.id.shareweibo);
        drawpath = (Button)findViewById(R.id.drawpath);
        animation = (Button)findViewById(R.id.animation);
        xListView = (Button)findViewById(R.id.xListView);
        gridPasswardView = (Button)findViewById(R.id.gridPasswardView);
        viewpager = (Button)findViewById(R.id.viewpager);
        viewpager_fragment = (Button)findViewById(R.id.viewpager_fragment);
        topbar = (Button)findViewById(R.id.topbar);
        flowlayout = (Button)findViewById(R.id.flowlayout);
        startForResult = (Button)findViewById(R.id.startAcyivityForResult);
        colorChangedCircle = (Button)findViewById(R.id.color_changed_circle);
        arcMenu = (Button)findViewById(R.id.arc_menu);
        datePicker = (Button)findViewById(R.id.date_picker);
        fragmentAnim = (Button)findViewById(R.id.fragment_anim);
        arcMenu2 = (Button)findViewById(R.id.arc_menu2);
        dragView = (Button)findViewById(R.id.drag_view);
        slidListview = (Button)findViewById(R.id.hslid_listview);
        autoSlid = (Button)findViewById(R.id.auto_slid);
        lookBigImage = (Button)findViewById(R.id.look_big_image);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    public void initListener(){
        shareweibo.setOnClickListener(this);
        drawpath.setOnClickListener(this);
        animation.setOnClickListener(this);
        xListView.setOnClickListener(this);
        gridPasswardView.setOnClickListener(this);
        viewpager.setOnClickListener(this);
        viewpager_fragment.setOnClickListener(this);
        topbar.setOnClickListener(this);
        flowlayout.setOnClickListener(this);
        startForResult.setOnClickListener(this);
        colorChangedCircle.setOnClickListener(this);
        arcMenu.setOnClickListener(this);
        datePicker.setOnClickListener(this);
        fragmentAnim.setOnClickListener(this);
        arcMenu2.setOnClickListener(this);
        dragView.setOnClickListener(this);
        slidListview.setOnClickListener(this);
        autoSlid.setOnClickListener(this);
        lookBigImage.setOnClickListener(this);
    }

    public void show(Class<? extends Activity> activity){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, activity);
        this.startActivity(intent);
    }
    public void showActivityForResult(Class<? extends Activity> activity,int requestCode){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,activity);
        this.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case TO_A_ACTIVITY:
                    Toast.makeText(this,"to c activity result",Toast.LENGTH_LONG).show();
                    break;

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shareweibo:
                show(WeiboSsoAuthActivity.class);
                break;
            case R.id.drawpath:
                show(DrawPathActivity.class);
                break;
            case R.id.animation:
                show(AnimationActivity.class);
                break;
            case R.id.xListView:
                show(XListViewActivity.class);
                break;
            case R.id.gridPasswardView:
                show(GridPasswardActivity.class);
                break;
            case R.id.viewpager:
                show(ViewPagerActivity.class);
                break;
            case R.id.viewpager_fragment:
                show(ViewPagerFragmentActivity.class);
                break;
            case R.id.topbar:
                show(TopBarActivity.class);
                break;
            case R.id.flowlayout:
                show(FlowLayoutActivity.class);
                break;
            case R.id.startAcyivityForResult:
                showActivityForResult(AActivity.class,TO_A_ACTIVITY);
                break;
            case R.id.color_changed_circle:
                show(ColorChangedCircleActivity.class);
                break;
            case R.id.fragment_anim:
                fragmentTransaction.setCustomAnimations(R.anim.sign_up_enter, R.anim.sign_up_exit);
                AnimFragment animFragment = new AnimFragment();
                fragmentTransaction.add(R.id.fragment_anim,animFragment);
                break;
            case R.id.arc_menu:
                show(ArcMenuActivity.class);
                break;
            case R.id.date_picker:
                Calendar calendar = Calendar.getInstance();
                DatePickerDialogWithMaxMin datePickerDialogWithMaxMin = new DatePickerDialogWithMaxMin(MainActivity.this,
                        new DatePickerDialogWithMaxMin.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datePicker.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                DatePicker datePicker = datePickerDialogWithMaxMin.getDatePicker();
                datePicker.setMinDate(calendar.getTimeInMillis());
                datePickerDialogWithMaxMin.show();
//                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                                datePicker.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
//                            }
//                        }, calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.show();
                break;
            case R.id.arc_menu2:
                show(ArcMenu2Activity.class);
                break;
            case R.id.drag_view:
                show(DragViewActivity.class);
                break;
            case R.id.hslid_listview:
                show(HSlidListViewActivity.class);
                break;
            case R.id.auto_slid:
                show(AutoSlidActivity.class);
                break;
            case R.id.look_big_image:
                show(SmallImageActivity.class);
                break;
            default:
                break;
        }
    }
}
