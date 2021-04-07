package com.example.myeverytime.user;

import android.util.Log;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.user.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myeverytime.ApplicationClass.getRetrofit;

public class UserService {
    private static final String TAG = "UserService";
    private final UserActivityView mUserActivityView;
    private UserRetrofitInterface userRetrofitInterface;

    public UserService(UserActivityView mUserActivityView) {
        this.mUserActivityView = mUserActivityView;
    }

    public void getUser(String username){
        userRetrofitInterface = getRetrofit().create(UserRetrofitInterface.class);
        Call<CMRespDto<User>> getUserCall = userRetrofitInterface.getUser(username);
        getUserCall.enqueue(new Callback<CMRespDto<User>>() {
            @Override
            public void onResponse(Call<CMRespDto<User>> call, Response<CMRespDto<User>> response) {
                CMRespDto getUserDto = response.body();
                if(getUserDto.getData() == null){
                    mUserActivityView.validateFailure(null);
                    Log.d(TAG, "onResponse: 유저 가져오기 실패");
                }
                mUserActivityView.getUserSuccess(getUserDto);
            }

            @Override
            public void onFailure(Call<CMRespDto<User>> call, Throwable t) {
                Log.d(TAG, "onFailure: 유저 가져오기 구조적으로 실패");
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }
}
