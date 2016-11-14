package com.example.jpery.dentalclinic;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArrangementsFragment extends Fragment {


    private OnArrangementsLoadedListener mCallback;
    private static FloatingActionButton fab;
    private static RecyclerView mRecyclerView;
    private static RecyclerView.LayoutManager mLayoutManager;
    private static ArrangementAdapter mAdapter;
    private static int userID;

    public ArrangementsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userID = getArguments().getInt(Constants.API_USER_ID);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ArrangementsService service = retrofit.create(ArrangementsService.class);
            Call<List<Arrangement>> call = service.getArrangementsbyUserID(userID);
            call.enqueue(new Callback<List<Arrangement>>() {
                @Override
                public void onResponse(Call<List<Arrangement>> call, Response<List<Arrangement>> response) {
                    if (response.code() == 200) {
                        for (Arrangement arrangement : response.body()) {
                            mAdapter.add(arrangement);
                        }
                        mCallback.showToast();
                    }
                }

                @Override
                public void onFailure(Call<List<Arrangement>> call, Throwable t) {
                    Toast.makeText(getActivity().getCurrentFocus().getContext(), R.string.internet_problem, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_arrangements, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AddArrangementActivity.class), 1);
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
                Snackbar.make(getActivity().getCurrentFocus(), "Item " + item.getTitle() + " clicked", Snackbar.LENGTH_LONG).show();
            }
        }
        );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnArrangementsLoadedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArrangementsLoadedListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                final Arrangement arrangement = new Arrangement(data, userID);
                Gson gson = new GsonBuilder()
                        .setDateFormat(Constants.DATE_FORMAT_STRING)
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.API_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                ArrangementsService service = retrofit.create(ArrangementsService.class);
                Call<Arrangement> call = service.addArrangement(arrangement);
                call.enqueue(new Callback<Arrangement>() {
                    @Override
                    public void onResponse(Call<Arrangement> call, Response<Arrangement> response) {
                        if (response.code() == 201) {
                            mAdapter.add(arrangement);
                        }else
                            Toast.makeText(getActivity().getCurrentFocus().getContext(), R.string.operation_not_completed, Toast.LENGTH_LONG).show();
                        Log.i("Response code", "" + response.code());
                    }

                    @Override
                    public void onFailure(Call<Arrangement> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getActivity().getCurrentFocus().getContext(), R.string.internet_problem, Toast.LENGTH_LONG).show();
                    }
                });

            }
        }
    }
    public interface OnArrangementsLoadedListener {
        public void showToast();
    }
}