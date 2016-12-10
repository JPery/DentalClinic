package com.example.jpery.dentalclinic.services;

import com.example.jpery.dentalclinic.model.Appointment;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by j.pery on 10/11/16.
 */

public interface AppointmentsService {
    @GET("arrangements/{appointmentID}")
    Call<Appointment> getAppointment(@Path("appointmentID") int appointmentID);

    @GET("arrangements/user={userID}")
    Call<List<Appointment>> getAppointmentsbyUserID(@Path("userID") int userID);

    @POST("arrangements/")
    Call<Appointment> addAppointment(@Body Appointment appointment);

    @GET("arrangements/date={date}")
    Call<List<Appointment>> getAppointmentsbyDate(@Path("date") String date);

    @DELETE("arrangements/{appointmentID}")
    Call<ResponseBody> deleteAppointment(@Path("appointmentID") int appointmentID);
}
