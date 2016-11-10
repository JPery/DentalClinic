package com.example.jpery.dentalclinic;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by j.pery on 10/11/16.
 */

public interface UsersService {
    @GET("users/{user}")
    Call<User> getUser(@Path("user") String user);

    @POST("users/login")
    Call<User> loginUser(@Body User user);
}

