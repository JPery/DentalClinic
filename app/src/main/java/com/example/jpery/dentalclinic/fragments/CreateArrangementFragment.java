package com.example.jpery.dentalclinic.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.jpery.dentalclinic.model.Arrangement;
import com.example.jpery.dentalclinic.utils.Constants;
import com.example.jpery.dentalclinic.R;

import java.util.Calendar;
import java.util.Date;

public class CreateArrangementFragment extends Fragment {

    private static String timeString;
    private static String dateString;
    private static TextView dateView;
    private static TextView timeView;
    private Spinner spinner;

    private Date mDate;

    public CreateArrangementFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_arrangement,container,false);
        dateString="";
        timeString="";
        getActivity().setTitle(R.string.title_add_arrangement_activity);
        dateView = (TextView) v.findViewById(R.id.date);
        timeView = (TextView) v.findViewById(R.id.time);

        setDefaultDateTime();

        final Button datePickerButton = (Button) v.findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        final Button timePickerButton = (Button) v.findViewById(R.id.time_picker_button);
        timePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        spinner = (Spinner) v.findViewById(R.id.interventions_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.interventions_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final Button submitButton = (Button) v.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = spinner.getSelectedItem().toString();
                String date = dateString + " " + timeString;
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = new ConfirmArrangementFragment();
                Arrangement.packageFragment(fragment,title,date);
                ft.replace(R.id.fragment_container, fragment, null);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return v;
    }

    private void showDatePickerDialog() {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), Constants.DATE_PICKER);
    }

    private void showTimePickerDialog() {
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getChildFragmentManager(),Constants.TIME_PICKER);
    }

    private void setDefaultDateTime() {
        mDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        dateView.setText(dateString);
        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        timeView.setText(timeString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;
        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        dateString = year + "-" + mon + "-" + day;
    }

    private static void setTimeString(int hourOfDay, int minute) {
        String hour = "" + hourOfDay;
        String min = "" + minute;
        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;
        timeString = hour + ":" + min + ":00";
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);
            dateView.setText(dateString);
        }
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute);
            timeView.setText(timeString);
        }
    }
}
