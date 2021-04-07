package com.example.myeverytime.signIn.interfaces;

import com.example.myeverytime.CMRespDto;

public interface SignInActivityView {
    void validateSuccess(String text);

    void validateFailure(String message);

    void signInSuccess(CMRespDto cmRespDto);
}
