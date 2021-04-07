package com.example.myeverytime.signUp.interfaces;

import com.example.myeverytime.CMRespDto;

public interface SignUpInputFormActivityView {
    void validateSuccess(String text);

    void validateFailure(String message);

    void signUpSuccess(CMRespDto cmRespDto);
}
