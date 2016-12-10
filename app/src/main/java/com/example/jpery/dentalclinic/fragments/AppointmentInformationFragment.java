package com.example.jpery.dentalclinic.fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jpery.dentalclinic.utils.NotificationPublisher;
import com.example.jpery.dentalclinic.R;
import com.example.jpery.dentalclinic.activities.MainActivity;
import com.example.jpery.dentalclinic.services.AppointmentsService;
import com.example.jpery.dentalclinic.utils.AppointmentController;
import com.example.jpery.dentalclinic.utils.Constants;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentInformationFragment extends Fragment {

    public AppointmentInformationFragment() {
    }

    long alertTimeMilis=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final int appointmentId = getArguments().getInt(Constants.EXTRAS_APPOINTMENT_ID);
        final String appointmentDate = getArguments().getString(Constants.EXTRAS_DATE);
        View v = inflater.inflate(R.layout.fragment_appointment_information, container, false);
        TextView appointmentTitle = (TextView) v.findViewById(R.id.appointment_title);
        TextView appointmentDescription = (TextView) v.findViewById(R.id.appointment_date);
        final TextView appointmentComment = (TextView) v.findViewById(R.id.appointment_comment);
        Button deleteButton = (Button) v.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog diaBox = AskOption(appointmentId);
                diaBox.show();
            }
        });
        Button notificationButton = (Button) v.findViewById(R.id.notifications_button);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog timeDialog = selectTimeDialog(appointmentId, appointmentDate);
                timeDialog.show();
            }
        });
        int kindOfSource = getArguments().getInt(Constants.EXTRAS_KIND_OF_INTERVENTION);
        appointmentTitle.setText(getResources().getStringArray(R.array.interventions_array)[kindOfSource - 1]);
        appointmentDescription.setText(appointmentDate);
        appointmentComment.setText(getArguments().getString(Constants.EXTRAS_COMMENT));
        return v;
    }

    private AlertDialog AskOption(final int appointmentId)
    {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_delete)
                .setMessage(R.string.dialog_delete_text)
                .setPositiveButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constants.API_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        AppointmentsService service = retrofit.create(AppointmentsService.class);
                        service.deleteAppointment(appointmentId).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                AppointmentController.getInstance().deleteElementbyId(appointmentId);
                                getFragmentManager().popBackStackImmediate();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(getActivity().getCurrentFocus().getContext(), R.string.internet_problem, Toast.LENGTH_LONG).show();
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    private AlertDialog selectTimeDialog(final int appointmentId, final String appointmentDate){
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle(getString(R.string.notifications_dialog_title));
        final String[] times = getResources().getStringArray(R.array.time_array);
        b.setItems(times, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT_STRING, Locale.US);
                Calendar cal=Calendar.getInstance();
                try {
                    Date date = format.parse(appointmentDate);
                    cal.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                switch(which){
                    case 0:
                        alertTimeMilis = cal.getTimeInMillis()-300000;
                        break;
                    case 1:
                        alertTimeMilis = cal.getTimeInMillis()-900000;
                        break;
                    case 2:
                        alertTimeMilis = cal.getTimeInMillis()-1800000;
                        break;
                    case 3:
                        alertTimeMilis = cal.getTimeInMillis()-3600000;
                        break;
                    case 4:
                        alertTimeMilis = cal.getTimeInMillis()-7200000;
                        break;
                }
                Toast.makeText(getActivity().getCurrentFocus().getContext(),getString(R.string.scheduled_notification_toast,times),Toast.LENGTH_SHORT).show();
                scheduleNotification(getActivity().getCurrentFocus().getContext(),alertTimeMilis,appointmentId, times[which]);
                dialog.dismiss();
            }

        });
        return b.create();
    }

    public void scheduleNotification(Context context, long timeOfNotification, int notificationId, String contentText) {//delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_text,contentText))
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(((BitmapDrawable) context.getResources().getDrawable(R.drawable.icon)).getBitmap())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);
        Notification notification = builder.build();
        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, timeOfNotification, pendingIntent);
    }
}
