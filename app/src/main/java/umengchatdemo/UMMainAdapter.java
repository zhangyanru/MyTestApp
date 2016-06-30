package umengchatdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by yanru.zhang on 16/6/28.
 * Email:yanru.zhang@renren-inc.com
 */
public class UMMainAdapter extends FragmentPagerAdapter {
    private String[] tabTitles = new String[]{"消息","联系人","群组","设置"};
    private Fragment[] fragments = new Fragment[]{UMMessageFragment.newInstance(null),
            UMFriendsFragment.newInstance(null),
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
