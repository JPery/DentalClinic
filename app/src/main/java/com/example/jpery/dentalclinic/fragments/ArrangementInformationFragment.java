package com.example.jpery.dentalclinic.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArrangementInformationFragment extends Fragment {

    public ArrangementInformationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_arrangement_information, container, false);
        TextView arrangementTitle = (TextView) v.findViewById(R.id.arrangement_title);
        TextView arrangementDescription = (TextView) v.findViewById(R.id.arrangement_date);
        TextView arrangementComment = (TextView) v.findViewById(R.id.arrangement_comment);
        int kindOfSource = getArguments().getInt(Constants.EXTRAS_KIND_OF_INTERVENTION);
        arrangementTitle.setText(getResources().getStringArray(R.array.interventions_array)[kindOfSource-1]);
        arrangementDescription.setText(getArguments().getString(Constants.EXTRAS_DATE));
        arrangementComment.setText(getArguments().getString(Constants.EXTRAS_COMMENT));
        return v;
    }

}
