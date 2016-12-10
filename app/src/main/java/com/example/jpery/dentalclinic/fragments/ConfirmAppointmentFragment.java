package com.example.jpery.dentalclinic.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.adapters.ConfirmAppointmentAdapter;
import com.example.jpery.dentalclinic.model.Appointment;
import com.example.jpery.dentalclinic.services.AppointmentsService;
import com.example.jpery.dentalclinic.utils.Constants;
import com.example.jpery.dentalclinic.utils.SimpleDividerItemDecoration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmAppointmentFragment extends Fragment {

    private static ConfirmAppointmentAdapter mAdapter;
    private int kindOfIntervention;
    private String appointmentDate;
    private int userID;
    private String appointmentComment;
    private DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_STRING);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView;
        final View v = inflater.inflate(R.layout.fragment_confirm_appointment, container, false);
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = (RecyclerView) v.findViewById(R.id.appointments_confirm_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ConfirmAppointmentAdapter(new ConfirmAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Appointment item) {
                df.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
                appointmentDate = df.format(item.getDate());
                AlertDialog diaBox = AskOption();
                diaBox.show();
            }
        }
        );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        getActivity().setTitle(R.string.title_activity_confirm_appointment);
        textView = (TextView) v.findViewById(R.id.textView4);
        textView.setText(R.string.choose_appointment);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.kindOfIntervention = getArguments().getInt(Constants.EXTRAS_KIND_OF_INTERVENTION);
        this.userID = getArguments().getInt(Constants.API_USER_ID);
        this.appointmentComment = getArguments().getString(Constants.EXTRAS_COMMENT);
        final Appointment appointment = new Appointment(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        df.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
        String date = df.format(appointment.getDate());
        AppointmentsService service = retrofit.create(AppointmentsService.class);
        Call<List<Appointment>> call = service.getAppointmentsbyDate(date);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if (response.code() == 200) {
                    for (Appointment appointment : response.body()) {
                        mAdapter.add(appointment);
                    }
                } else
                    Toast.makeText(getActivity().getCurrentFocus().getContext(), R.string.operation_not_completed, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<List<Appointment>>call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity().getCurrentFocus().getContext(), R.string.internet_problem, Toast.LENGTH_LONG).show();
            }
        });
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_confirm)
                .setMessage(R.string.dialog_text)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(Constants.EXTRAS_KIND_OF_INTERVENTION,kindOfIntervention);
                        resultIntent.putExtra(Constants.API_URL,userID);
                        resultIntent.putExtra(Constants.EXTRAS_DATE,appointmentDate);
                        resultIntent.putExtra(Constants.EXTRAS_COMMENT,appointmentComment);
                        getActivity().setResult(Activity.RESULT_OK, resultIntent);
                        getActivity().finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }
}
