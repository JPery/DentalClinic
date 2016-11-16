package com.example.jpery.dentalclinic.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
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
import com.example.jpery.dentalclinic.adapters.ConfirmArrangementsAdapter;
import com.example.jpery.dentalclinic.model.Arrangement;
import com.example.jpery.dentalclinic.services.ArrangementsService;
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

public class ConfirmArrangementFragment extends Fragment {

    private static ConfirmArrangementsAdapter mAdapter;
    private int kindOfIntervention;
    private String arrangementDate;
    private int userID;
    private String arrangementComment;
    private DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_STRING);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView;
        final View v = inflater.inflate(R.layout.fragment_confirm_arrangement, container, false);
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView = (RecyclerView) v.findViewById(R.id.arrangements_confirm_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ConfirmArrangementsAdapter(new ConfirmArrangementsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Arrangement item) {
                df.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
                arrangementDate = df.format(item.getDate());
                AlertDialog diaBox = AskOption();
                diaBox.show();
            }
        }
        );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        getActivity().setTitle(R.string.title_activity_confirm_arrangement);
        textView = (TextView) v.findViewById(R.id.textView4);
        textView.setText(R.string.choose_arrangement);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.kindOfIntervention = getArguments().getInt(Constants.EXTRAS_KIND_OF_INTERVENTION);
        this.userID = getArguments().getInt(Constants.API_USER_ID);
        this.arrangementComment = getArguments().getString(Constants.EXTRAS_COMMENT);
        final Arrangement arrangement = new Arrangement(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        df.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
        String date = df.format(arrangement.getDate());
        ArrangementsService service = retrofit.create(ArrangementsService.class);
        Call<List<Arrangement>> call = service.getArrangementsbyDate(date);
        call.enqueue(new Callback<List<Arrangement>>() {
            @Override
            public void onResponse(Call<List<Arrangement>> call, Response<List<Arrangement>> response) {
                if (response.code() == 200) {
                    for (Arrangement arrangement : response.body()) {
                        mAdapter.add(arrangement);
                    }
                } else
                    Toast.makeText(getActivity().getCurrentFocus().getContext(), R.string.operation_not_completed, Toast.LENGTH_LONG).show();
                Log.i("Response code", "" + response.code());
            }
            @Override
            public void onFailure(Call<List<Arrangement>>call, Throwable t) {
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
                        resultIntent.putExtra(Constants.EXTRAS_DATE,arrangementDate);
                        resultIntent.putExtra(Constants.EXTRAS_COMMENT,arrangementComment);
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
