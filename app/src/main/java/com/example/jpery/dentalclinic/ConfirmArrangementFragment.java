package com.example.jpery.dentalclinic;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConfirmArrangementFragment extends Fragment {

    private static TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirm_arrangement, container, false);
        getActivity().setTitle(R.string.title_activity_confirm_arrangement);
        textView = (TextView) v.findViewById(R.id.textView4);
        textView.setText(R.string.choose_arrangement);
        return v;
    }
}
