package com.example.myeverytime.signIn;

import android.util.Log;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.signIn.interfaces.SignInActivityView;
import com.example.myeverytime.signIn.interfaces.SignInRetrofitInterface;
import com.example.myeverytime.signIn.model.SignInDto;

import static com.example.myeverytime.ApplicationClass.getRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInService {
    private static final String TAG = "SignInService";

    private final SignInActivityView mSignInActivityView;
    private SignInRetrofitInterface signInRetrofitInterface;


    SignInService(final SignInActivityView signInActivityView) {
        this.mSignInActivityView = signInActivityView;
    }

    public void postSignIn(SignInDto signInDto) {
        // getRetrofit 사용하려면 import static 해줘야 합니다.
        signInRetrofitInterface = getRetrofit().create(SignInRetrofitInterface.class);
        Call<CMRespDto<SignInDto>> signInCall = signInRetrofitInterface.signIn(signInDto);
        signInCall.enqueue(new Callback<CMRespDto<SignInDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<SignInDto>> call, Response<CMRespDto<SignInDto>> response) {
                CMRespDto signInRespDto = response.body();

                if (signInRespDto.getData() == null) {
                    Log.d(TAG, "onResponse: " + signInRespDto);
                    Log.d(TAG, "onResponse: 로그인 실패");
                    mSignInActivityView.validateSuccess("아이디가 없거나 비밀번호가 틀립니다.");
                    return;
                }
                Log.d(TAG, "onResponse: " + signInRespDto);

                    mSignInActivityView.signInSuccess(signInRespDto);
                    Log.d(TAG, "onResponse: 로그인 성공");
            }

            @Override
            public void onFailure(Call<CMRespDto<SignInDto>> call, Throwable t) {
                mSignInActivityView.validateFailure(null);
                Log.d(TAG, "onFailure: 로그인 구조적으로 실패");
            }
        });

    }
}
