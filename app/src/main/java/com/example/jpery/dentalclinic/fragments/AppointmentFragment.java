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

import com.example.jpery.dentalclinic.model.Appointment;
import com.example.jpery.dentalclinic.adapters.AppointmentAdapter;
import com.example.jpery.dentalclinic.services.AppointmentsService;
import com.example.jpery.dentalclinic.utils.Constants;
import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.utils.SimpleDividerItemDecoration;
import com.example.jpery.dentalclinic.activities.AddAppointmentActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppointmentFragment extends Fragment {


    private OnAppointmentsLoadedListener mCallback;
    private static AppointmentAdapter mAdapter;
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
            AppointmentsService service = retrofit.create(AppointmentsService.class);
            Call<List<Appointment>> call = service.getAppointmentsbyUserID(userID);
            call.enqueue(new Callback<List<Appointment>>() {
                @Override
                public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                    if (response.code() == 200) {
                        for (Appointment appointment : response.body()) {
                            mAdapter.add(appointment);
                        }
                        mCallback.showToast();
                    }
                }

                @Override
                public void onFailure(Call<List<Appointment>> call, Throwable t) {
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
        final View v = inflater.inflate(R.layout.fragment_appointments, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AddAppointmentActivity.class), 1);
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_activity_navigation_drawer);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.appointments_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AppointmentAdapter(new AppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Appointment item) {
                Fragment fragment = new AppointmentInformationFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.EXTRAS_APPOINTMENT_ID,item.getId());
                bundle.putInt(Constants.EXTRAS_KIND_OF_INTERVENTION,item.getKindOfIntervention());
                DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_STRING, Locale.US);
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
            mCallback = (OnAppointmentsLoadedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAppointmentsLoadedListener");
        }
        super.onAttach(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                final Appointment appointment = new Appointment(data,userID);
                Gson gson = new GsonBuilder()
                        .setDateFormat(Constants.DATE_FORMAT_STRING_JSON)
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.API_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                AppointmentsService service = retrofit.create(AppointmentsService.class);
                Call<Appointment> call = service.addAppointment(appointment);
                call.enqueue(new Callback<Appointment>() {
                    @Override
                    public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                        if (response.code() == 201 && response.body().getKindOfIntervention()>0) {
                            mAdapter.add(response.body());
                        } else
                            Toast.makeText(getActivity().getCurrentFocus().getContext(), R.string.operation_not_completed, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Appointment> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getActivity().getCurrentFocus().getContext(), R.string.internet_problem, Toast.LENGTH_LONG).show();
                    }
                });

            }
        }
    }

    public interface OnAppointmentsLoadedListener {
        void showToast();
    }
}