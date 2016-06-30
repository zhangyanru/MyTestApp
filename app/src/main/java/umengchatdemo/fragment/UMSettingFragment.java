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
public class UMSettingFragment extends Fragment {
    private View rootView;

    public static UMSettingFragment newInstance(Bundle args) {

        UMSettingFragment fragment = new UMSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_um_setting,null);
        return rootView;
    }
}