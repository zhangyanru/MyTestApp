package com.example.myapp.fragment;


import android.animation.Animator;
import android.app.Fragment;

/**
 * Created by zyr
 * DATE: 15-11-16
 * Time: 上午11:31
 * Email: yanru.zhang@renren-inc.com
 */
public class AnimFragment extends Fragment {
    public final static String TAG = "AnimFragment";

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimator(transit, enter, nextAnim);
    }
}
