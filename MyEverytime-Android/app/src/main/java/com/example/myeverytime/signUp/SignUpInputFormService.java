package com.example.myeverytime.signUp;

import android.util.Log;

import com.example.myeverytime.BaseActivity;
import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.signUp.interfaces.SignUpInputFormActivityView;
import com.example.myeverytime.signUp.interfaces.SignUpInputFormRetrofitInterface;
import com.example.myeverytime.signUp.model.SignUpDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myeverytime.ApplicationClass.getRetrofit;

public class SignUpInputFormService extends BaseActivity {

    private static final String TAG = "SignUpInputFormService";
    private SignUpInputFormRetrofitInterface signUpInputFormRetrofitInterface;

    private final SignUpInputFormActivityView mSignUpInputFormActivity_View;

    SignUpInputFormService(final SignUpInputFormActivityView signUpInputFormActivity_view) {
        this.mSignUpInputFormActivity_View = signUpInputFormActivity_view;
    }


    public void postSignUp(SignUpDto signUpDto) {
        // getRetrofit 사용하려면 import static 해줘야 합니다.
        signUpInputFormRetrofitInterface = getRetrofit().create(SignUpInputFormRetrofitInterface.class);
        Call<CMRespDto<SignUpDto>> signUpCall = signUpInputFormRetrofitInterface.save(signUpDto);
        signUpCall.enqueue(new Callback<CMRespDto<SignUpDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<SignUpDto>> call, Response<CMRespDto<SignUpDto>> response) {
                CMRespDto signUpRespDto = response.body();
                if (signUpRespDto == null) {
                    Log.d(TAG, "onResponse: 회원가입 실패");
                    mSignUpInputFormActivity_View.validateFailure(null);
                    return;
                }

                mSignUpInputFormActivity_View.signUpSuccess(signUpRespDto);
                Log.d(TAG, "onResponse: 회원가입 성공");
            }

            @Override
            public void onFailure(Call<CMRespDto<SignUpDto>> call, Throwable t) {
                mSignUpInputFormActivity_View.validateFailure(null);
                Log.d(TAG, "onResponse: 회원가입 구조적으로 실패 " + t.getMessage());
            }
        });
    }
}
