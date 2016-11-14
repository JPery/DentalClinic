package com.example.jpery.dentalclinic;

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

import java.util.Calendar;
import java.util.Date;

public class CreateArrangementFragment extends Fragment {

    private static final int SEVEN_DAYS = 604800000;

    private static String timeString;
    private static String dateString;
    private static TextView dateView;
    private static TextView timeView;
    private static Spinner spinner;

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
        // Set the default date and time

        setDefaultDateTime();

        // OnClickListener for the Date button, calls showDatePickerDialog() to show
        // the Date dialog

        final Button datePickerButton = (Button) v.findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // OnClickListener for the Time button, calls showTimePickerDialog() to show
        // the Time Dialog

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
        datePickerFragment.show(getFragmentManager(),Constants.DATEPICKER);
    }

    private void showTimePickerDialog() {
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getChildFragmentManager(),Constants.TIMEPICKER);
    }

    // Do not modify below here
    // Use this method to set the default date and time

    private void setDefaultDateTime() {

        // Default is current time + 7 days
        mDate = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        dateView.setText(dateString);

        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                c.get(Calendar.MILLISECOND));

        timeView.setText(timeString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        dateString = year + "-" + mon + "-" + day;
    }

    private static void setTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        timeString = hour + ":" + min + ":00";
    }
    // DialogFragment used to pick a ToDoItem deadline date

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);
            dateView.setText(dateString);
        }
    }

    // DialogFragment used to pick a ToDoItem deadline time
    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute, 0);
            timeView.setText(timeString);
        }
    }
}