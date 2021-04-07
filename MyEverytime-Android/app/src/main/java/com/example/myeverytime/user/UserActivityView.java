package com.example.myeverytime.user;

import com.example.myeverytime.CMRespDto;

public interface UserActivityView {
    void validateSuccess(String text);

    void validateFailure(String message);

    void getUserSuccess(CMRespDto cmRespDto);
}
