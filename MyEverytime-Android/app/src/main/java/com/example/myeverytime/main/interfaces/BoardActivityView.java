package com.example.myeverytime.main.interfaces;

import com.example.myeverytime.CMRespDto;

public interface BoardActivityView {
    void validateSuccess(String text);

    void validateFailure(String message);

    void boardSuccess(CMRespDto cmRespDto);
}
