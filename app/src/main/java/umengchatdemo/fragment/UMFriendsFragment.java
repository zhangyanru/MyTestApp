package umengchatdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.R;

/**
 * Created by yanru.zhang on 16/6/28.
 * Email:yanru.zhang@renren-inc.com
 */
public class UMFriendsFragment extends Fragment {
    private View rootView;

    public static UMFriendsFragment newInstance(Bundle args) {

        UMFriendsFragment fragment = new UMFriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_um_friends,null);
        return rootView;
    }
}
