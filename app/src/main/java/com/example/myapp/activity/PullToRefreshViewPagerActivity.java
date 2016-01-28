package com.example.myapp.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapp.R;
import com.example.myapp.view.PullToRefreshViewPager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by zyr
 * DATE: 16-1-26
 * Time: 下午7:40
 * Email: yanru.zhang@renren-inc.com
 */
public class PullToRefreshViewPagerActivity extends Activity implements PullToRefreshBase.OnRefreshListener<ViewPager> {

    private PullToRefreshViewPager mPullToRefreshViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_viewpager);

        mPullToRefreshViewPager = (PullToRefreshViewPager) findViewById(R.id.pull_refresh_viewpager);
        mPullToRefreshViewPager.setOnRefreshListener(this);

        ViewPager vp = mPullToRefreshViewPager.getRefreshableView();
        vp.setAdapter(new SamplePagerAdapter());
    }

    @Override
    public void onRefresh(PullToRefreshBase<ViewPager> refreshView) {
        new GetDataTask().execute();
    }

    static class SamplePagerAdapter extends PagerAdapter {

        private static int[] sDrawables = { R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper,
                R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper };

        @Override
        public int getCount() {
            return sDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(sDrawables[position]);

            // Now just add ImageView to ViewPager and return it
            container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mPullToRefreshViewPager.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

}
