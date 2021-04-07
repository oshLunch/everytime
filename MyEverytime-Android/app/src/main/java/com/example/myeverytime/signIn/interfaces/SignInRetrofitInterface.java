package com.example.myeverytime.signIn.interfaces;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.signIn.model.SignInDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInRetrofitInterface {

    @POST("login")
    Call<CMRespDto<SignInDto>> signIn(@Body SignInDto signInDto);

}
