package com.example.jpery.dentalclinic.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jpery.dentalclinic.R;

/**
 * Created by j.pery on 14/11/16.
 */
public class SettingsFragment extends PreferenceFragment {

    public static final String KEY_PREF_WELCOME_MESSAGE = "pref_welcome_message";
    public static final String KEY_PREF_USERNAME = "pref_username";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.action_settings);
        return super.onCreateView(inflater,container,savedInstanceState);
    }
}