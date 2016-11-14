package com.example.jpery.dentalclinic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by j.pery on 10/11/16.
 */

public interface ArrangementsService {
    @GET("arrangements/{arrangementid}")
    Call<Arrangement> getArrangement(@Path("arrangementid") int arrangementid);

    @GET("arrangements/user={userid}")
    Call<List<Arrangement>> getArrangementsbyUserID(@Path("userid") int userid);

    @POST("arrangements/")
    Call<Arrangement> addArrangement(@Body Arrangement arrangement);

}
