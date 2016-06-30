package umengchatdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.myapp.activity.MyApplication;

import umengchatdemo.fragment.UMFriendsFragment;
import umengchatdemo.fragment.UMGroupFragment;
import umengchatdemo.fragment.UMMessageFragment;
import umengchatdemo.fragment.UMSettingFragment;
import umengchatdemo.utils.UMUtils;

/**
 * Created by yanru.zhang on 16/6/28.
 * Email:yanru.zhang@renren-inc.com
 */
public class UMMainAdapter extends FragmentPagerAdapter {
    private String[] tabTitles = new String[]{"消息","联系人","群组","设置"};
    private Fragment[] fragments = new Fragment[]{
//            UMMessageFragment.newInstance(null),
            UMUtils.getInstance().getmIMKit().getConversationFragment(),
//            UMFriendsFragment.newInstance(null),
            UMUtils.getInstance().getmIMKit().getContactsFragment(),
            UMGroupFragment.newInstance(null),
            UMSettingFragment.newInstance(null)};

    public UMMainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
