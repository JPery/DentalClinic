package com.example.jpery.dentalclinic.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jpery.dentalclinic.R;

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
        View v = inflater.inflate(R.layout.fragment_information, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_information_fragment);
        return v;
    }
}
