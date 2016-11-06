package com.example.jpery.dentalclinic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

public class InformationFragment extends Fragment {


    public InformationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).hideFloatingActionButton();
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (FragmentUtils.sDisableFragmentAnimations) {
            Animation a = new Animation() {};
            a.setDuration(0);
            return a;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onDestroy() {
        ((MainActivity) getActivity()).showFloatingActionButton();
        super.onDestroy();
    }
}
