package com.example.jpery.dentalclinic.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jpery.dentalclinic.model.Arrangement;
import com.example.jpery.dentalclinic.adapters.ArrangementAdapter;
import com.example.jpery.dentalclinic.services.ArrangementsService;
import com.example.jpery.dentalclinic.utils.Constants;
import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.utils.SimpleDividerItemDecoration;
import com.example.jpery.dentalclinic.activities.AddAppointmentActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppointmentFragment extends Fragment {


    private OnArrangementsLoadedListener mCallback;
    private static ArrangementAdapter mAdapter;
    private static int userID;

    public AppointmentFragment() {
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
        FloatingActionButton fab;
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        final View v = inflater.inflate(R.layout.fragment_arrangements, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AddAppointmentActivity.class), 1);
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_activity_navigation_drawer);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.arrangements_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ArrangementAdapter(new ArrangementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Arrangement item) {
                Fragment fragment = new AppointmentInformationFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.EXTRAS_KIND_OF_INTERVENTION,item.getKindOfIntervention());
                DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_STRING);
                df.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
                bundle.putString(Constants.EXTRAS_DATE,df.format(item.getDate()));
                bundle.putString(Constants.EXTRAS_COMMENT,item.getComment());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment,Constants.FRAGMENT_BUDGETS);
                fragmentTransaction.addToBackStack(Constants.FRAGMENT_BUDGETS);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        }
        );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnArrangementsLoadedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArrangementsLoadedListener");
        }
        super.onAttach(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                final Arrangement arrangement = new Arrangement(data,userID);
                Gson gson = new GsonBuilder()
                        .setDateFormat(Constants.DATE_FORMAT_STRING_JSON)
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
                        if (response.code() == 201 && response.body().getKindOfIntervention()>0) {
                            mAdapter.add(arrangement);
                        } else
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