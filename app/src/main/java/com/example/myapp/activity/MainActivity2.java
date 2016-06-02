package com.example.myapp.activity;

import android.view.View;
import android.widget.Button;

import com.example.myapp.R;
import com.example.myapp.github.parallaxheaderviewpager.demo.ParallaxHeaderViewPagerMainActivity;
import com.example.myapp.github.pinnedheaderlistview.PinnedHeaderListViewMainActivity;
import com.example.myapp.github.pinnedsectionlistview.PinnedSectionListActivity;
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
    private Button quxianAnimator;
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
    @Override
    protected void initView() {
        scrollerTest = (Button)findViewById(R.id.scroller_test);
        shimmerText = (Button)findViewById(R.id.shimmer_text);
        quxianAnimator = (Button)findViewById(R.id.quxian_animator);
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
    }

    @Override
    protected int onSetContainerViewId() {
        return R.layout.activity_main2;
    }

    @Override
    public void initListener() {
        scrollerTest.setOnClickListener(this);
        shimmerText.setOnClickListener(this);
        quxianAnimator.setOnClickListener(this);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scroller_test:
                show(this,ScrollerTestActivity.class);
                break;
            case R.id.shimmer_text:
                show(this, ShimmerTextViewTestActivity.class);
                break;
            case R.id.quxian_animator:
                show(this,QuXianAnimatorActivity.class);
                break;
            case R.id.path_animator:
                show(this,PathAnimatorActivity.class);
                break;
            case R.id.custom_pull_to_refresh_listview:
                show(this,CustomPullToRefreshListViewActivity.class);
                break;
            case R.id.custom_slid_delete_list_view:
                show(this,CustomSlideDeleteListViewTestActivity.class);
                break;
            case R.id.popwindow_test:
                show(this,PopupWindowTestActivity.class);
                break;
            case R.id.pull_to_zoom_view_test:
                show(this,PullToZoomViewTestActivity.class);
                break;
            case R.id.pull_to_refresh_list_view2:
                show(this,CustomPullToRefreshListViewTestAvtivity2.class);
                break;
            case R.id.search_list_view:
                show(this,CustomSearchListViewTestActivity.class);
                break;
            case R.id.pinned_section_list_view:
                show(this, CustomPinnedHeaderListViewTestActivity.class);
                break;
            case R.id.recycler_view_test:
                show(this, RecyclerViewTestActivity.class);
                break;
            case R.id.recycler_view_gallery_test:
                show(this,RecyclerViewGalleryTestActivity.class);
                break;
            case R.id.custom_expandable_layout:
                show(this,CustomExpandableLayoutTestActivity.class);
                break;
            case R.id.search_anim:
                show(this,SearchAnimTestActivity.class);
                break;
            case R.id.net_work:
                show(this,NetWorkTestActivity.class);
                break;
            case R.id.github_pullzoomview:
                show(this, PullZoomViewMainActivity.class);
                break;
            case R.id.github_parallax_headerviewpager:
                show(this, ParallaxHeaderViewPagerMainActivity.class);
                break;
            case R.id.muti_thread:
                show(this,MutilThreadTestActivity.class);
                break;
            case R.id.handler_test:
                show(this,HandlerTestActivity.class);
                break;
        }

    }
}
