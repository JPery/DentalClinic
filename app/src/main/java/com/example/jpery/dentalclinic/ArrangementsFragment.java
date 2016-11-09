package com.example.jpery.dentalclinic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ArrangementsFragment extends Fragment {

    private static FloatingActionButton fab;
    private static RecyclerView mRecyclerView;
    private static RecyclerView.LayoutManager mLayoutManager;
    private static ArrangementAdapter mAdapter;

    public ArrangementsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_arrangements, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(),AddArrangementActivity.class),1);
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_activity_navigation_drawer);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.arrangements_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new ArrangementAdapter(new ArrangementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Arrangement item) {
                Snackbar.make(getActivity().getCurrentFocus(),"Item "+item.getTitle()+" clicked",Snackbar.LENGTH_LONG).show();
            }
        }
        );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1) {
            if (resultCode == Activity.RESULT_OK) {
                Arrangement a = new Arrangement(data);
                Log.i("Arrangement", a.toLog());
                mAdapter.add(a);
            }
        }
    }
}