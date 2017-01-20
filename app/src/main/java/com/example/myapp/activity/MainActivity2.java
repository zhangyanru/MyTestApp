package com.example.myapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.github.parallaxheaderviewpager.demo.ParallaxHeaderViewPagerMainActivity;
import com.example.myapp.github.pullzoomview.demo.PullZoomViewMainActivity;
/**
 * Created by zyr
 * DATE: 16-2-24
 * Time: 下午7:17
 * Email: yanru.zhang@renren-inc.com
 */
public class MainActivity2 extends BaseActivity {
    private Button scrollerTest;
    private Button shimmerText;
    private Button pathAnimator;
    private Button customPullToRefreshListViewTest;
    private Button customSlideDeleteListViewTest;
    private Button popWindowTest;
    private Button pullToZoomView;
    private Button customPullToRefreshListViewTest2;
    private Button customSearchListViewTest;
    private Button pinnedSectionListView;
    private Button recyclerViewTest;
    private Button recyclerViewGalleryTest;
    private Button customExpandableLv;
    private Button searchAnim;
    private Button netWork;
    private Button githubPullZoomView;
    private Button githubParallaxHeaderViewPager;
    private Button javaMutilThread;
    private Button handlerTest;
    private Button threadPoolTest;
    private Button singleTaskTest;
    private Button imageTextShowTest;
    private Button cameraTest;
    private ImageView imageView;
    private Button vectorTest;
    private Button httpMultiPartTest;
    private Button webViewTest;
    private Button xmlParseTest;
    private Button wifiTest;
    private Button frameAnimTest;
    private Button saiBeiErTest;
    private Button optimizeViewTest;
    private Button qqSideSlideTest;
    private Button pullToRefreshWebView;
    @Override
    protected void initView() {
        scrollerTest = (Button)findViewById(R.id.scroller_test);
        shimmerText = (Button)findViewById(R.id.shimmer_text);
        pathAnimator = (Button)findViewById(R.id.path_animator);
        customPullToRefreshListViewTest = (Button) findViewById(R.id.custom_pull_to_refresh_listview);
        customSlideDeleteListViewTest = (Button) findViewById(R.id.custom_slid_delete_list_view);
        popWindowTest = (Button) findViewById(R.id.popwindow_test);
        pullToZoomView = (Button) findViewById(R.id.pull_to_zoom_view_test);
        customPullToRefreshListViewTest2 = (Button) findViewById(R.id.pull_to_refresh_list_view2);
        customSearchListViewTest = (Button) findViewById(R.id.search_list_view);
        pinnedSectionListView = (Button) findViewById(R.id.pinned_section_list_view);
        recyclerViewTest = (Button) findViewById(R.id.recycler_view_test);
        recyclerViewGalleryTest = (Button) findViewById(R.id.recycler_view_gallery_test);
        customExpandableLv = (Button) findViewById(R.id.custom_expandable_layout);
        searchAnim = (Button) findViewById(R.id.search_anim);
        netWork = (Button) findViewById(R.id.net_work);
        githubPullZoomView = (Button) findViewById(R.id.github_pullzoomview);
        githubParallaxHeaderViewPager = (Button) findViewById(R.id.github_parallax_headerviewpager);
        javaMutilThread = (Button) findViewById(R.id.muti_thread);
        handlerTest = (Button) findViewById(R.id.handler_test);
        threadPoolTest = (Button) findViewById(R.id.thread_pool_test);
        singleTaskTest = (Button) findViewById(R.id.activity_single_task_test);
        imageTextShowTest = (Button) findViewById(R.id.image_text_show);
        cameraTest = (Button) findViewById(R.id.camera_test);
        imageView = (ImageView) findViewById(R.id.image_view);
        vectorTest = (Button) findViewById(R.id.vector_test);
        httpMultiPartTest = (Button) findViewById(R.id.http_multi_part_test);
        webViewTest = (Button) findViewById(R.id.web_view_test);
        xmlParseTest = (Button) findViewById(R.id.xml_parse_test);
        wifiTest = (Button) findViewById(R.id.wifi_test);
        frameAnimTest = (Button) findViewById(R.id.frame_amin_test);
        saiBeiErTest = (Button) findViewById(R.id.sai_bei_er_test);
        optimizeViewTest = (Button) findViewById(R.id.optimised_view_test);
        qqSideSlideTest = (Button) findViewById(R.id.qq_side_slide);
        pullToRefreshWebView = (Button) findViewById(R.id.pull_to_refresh_webView);
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_main2;
    }

    @Override
    public void initListener() {
        scrollerTest.setOnClickListener(this);
        shimmerText.setOnClickListener(this);
        pathAnimator.setOnClickListener(this);
        customPullToRefreshListViewTest.setOnClickListener(this);
        customSlideDeleteListViewTest.setOnClickListener(this);
        popWindowTest.setOnClickListener(this);
        pullToZoomView.setOnClickListener(this);
        customPullToRefreshListViewTest2.setOnClickListener(this);
        customSearchListViewTest.setOnClickListener(this);
        pinnedSectionListView.setOnClickListener(this);
        recyclerViewTest.setOnClickListener(this);
        recyclerViewGalleryTest.setOnClickListener(this);
        customExpandableLv.setOnClickListener(this);
        searchAnim.setOnClickListener(this);
        netWork.setOnClickListener(this);
        githubPullZoomView.setOnClickListener(this);
        githubParallaxHeaderViewPager.setOnClickListener(this);
        javaMutilThread.setOnClickListener(this);
        handlerTest.setOnClickListener(this);
        threadPoolTest.setOnClickListener(this);
        singleTaskTest.setOnClickListener(this);
        imageTextShowTest.setOnClickListener(this);
        cameraTest.setOnClickListener(this);
        vectorTest.setOnClickListener(this);
        httpMultiPartTest.setOnClickListener(this);
        webViewTest.setOnClickListener(this);
        xmlParseTest.setOnClickListener(this);
        wifiTest.setOnClickListener(this);
        frameAnimTest.setOnClickListener(this);
        saiBeiErTest.setOnClickListener(this);
        optimizeViewTest.setOnClickListener(this);
        qqSideSlideTest.setOnClickListener(this);
        pullToRefreshWebView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scroller_test:
                show(this, ScrollerTestActivity.class);
                break;
            case R.id.shimmer_text:
                show(this, ShimmerTextViewTestActivity.class);
                break;
            case R.id.path_animator:
                show(this, PathAnimatorActivity.class);
                break;
            case R.id.custom_pull_to_refresh_listview:
                show(this, CustomPullToRefreshListViewActivity.class);
                break;
            case R.id.custom_slid_delete_list_view:
                show(this, CustomSlideDeleteListViewTestActivity.class);
                break;
            case R.id.popwindow_test:
                show(this, PopupWindowTestActivity.class);
                break;
            case R.id.pull_to_zoom_view_test:
                show(this, PullToZoomViewTestActivity.class);
                break;
            case R.id.pull_to_refresh_list_view2:
                show(this, CustomPullToRefreshListViewTestAvtivity2.class);
                break;
            case R.id.search_list_view:
                show(this, CustomSearchListViewTestActivity.class);
                break;
            case R.id.pinned_section_list_view:
                show(this, CustomPinnedHeaderListViewTestActivity.class);
                break;
            case R.id.recycler_view_test:
                show(this, RecyclerViewTestActivity.class);
                break;
            case R.id.recycler_view_gallery_test:
                show(this, RecyclerViewGalleryTestActivity.class);
                break;
            case R.id.custom_expandable_layout:
                show(this, CustomExpandableLayoutTestActivity.class);
                break;
            case R.id.search_anim:
                show(this, SearchAnimTestActivity.class);
                break;
            case R.id.net_work:
                show(this, NetWorkTestActivity.class);
                break;
            case R.id.github_pullzoomview:
                show(this, PullZoomViewMainActivity.class);
                break;
            case R.id.github_parallax_headerviewpager:
                show(this, ParallaxHeaderViewPagerMainActivity.class);
                break;
            case R.id.muti_thread:
                show(this, MutilThreadTestActivity.class);
                break;
            case R.id.handler_test:
                show(this, HandlerTestActivity.class);
                break;
            case R.id.thread_pool_test:
                show(this, ThreadPoolTestActivity.class);
                break;
            case R.id.activity_single_task_test:
//                show(this, SingleTaskTestActivity.class);

                Intent intent = new Intent();
//                intent.setComponent(
//                        new ComponentName("com.example.yanruzhang.myapplicationbb","com.example.yanruzhang.myapplicationbb.YActivity"));
                intent.setAction("yactivity");
                startActivity(intent);
                break;
            case R.id.image_text_show:
                show(this,ImageTextShowTestActivity.class);
                break;
            case R.id.camera_test:
                Intent intent1 = new Intent(this,CameraActivity.class);
                startActivityForResult(intent1,1);
                break;
            case R.id.vector_test:
                show(this,VectorTestActivity.class);
                break;
            case R.id.http_multi_part_test:
                show(this,HttpMultiPartTestActivity.class);
                break;
            case R.id.web_view_test:
                show(this,WebViewTestActivity.class);
                break;
            case R.id.xml_parse_test:
                show(this,XMLParseTestActivity.class);
                break;
            case R.id.wifi_test:
                show(this,WifiTestActivity.class);
                break;
            case R.id.frame_amin_test:
                show(this,FrameAnimTestActivity.class);
                break;
            case R.id.sai_bei_er_test:
                show(this,SaiBeiErTestActivity.class);
                break;
            case R.id.optimised_view_test:
                show(this,OptimizeViewTestActivity.class);
                break;
            case R.id.qq_side_slide:
                show(this,QQSideSlideTestActivity.class);
                break;
            case R.id.pull_to_refresh_webView:
                show(this,PullToRefreshWebViewTestActivity.class);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case 1:
                    if(data != null){


                        Bundle bundle = data.getExtras();

                        // 获得照片uri
                        Uri uri = Uri.parse(bundle.getString("uriStr"));

                        // 获得拍照时间
                        long dateTaken = bundle.getLong("dateTaken");
                        try {
                            // 从媒体数据库获取该照片
                            Bitmap cameraBitmap = MediaStore.Images.Media.getBitmap(
                                    getContentResolver(), uri);
                            imageView.setImageBitmap(cameraBitmap); // 预览图像


                            // 从媒体数据库删除该照片（按拍照时间）
                            getContentResolver().delete(
                                    CameraActivity.IMAGE_URI,
                                    MediaStore.Images.Media.DATE_TAKEN + "="
                                            + String.valueOf(dateTaken), null);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{

                    }
                    break;
            }
        }

    }
}
