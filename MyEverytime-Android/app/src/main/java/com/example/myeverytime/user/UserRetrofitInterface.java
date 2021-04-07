package com.example.myeverytime.user;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.user.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserRetrofitInterface {

    @GET("user/{username}")
    Call<CMRespDto<User>> getUser(@Path("username") String username);
}
