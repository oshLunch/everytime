package com.example.myeverytime.main.boards.interfaces;

import com.example.myeverytime.CMRespDto;

public interface UpdatingActivityView {
    void validateSuccess(String text);

    void validateFailure(String message);

    void UpdatingSuccess(CMRespDto cmRespDto);
}
