package com.example.myeverytime.main.boards.interfaces;

import com.example.myeverytime.CMRespDto;

public interface WritingActivityView {

    void validateSuccess(String text);

    void validateFailure(String message);

    void WritingSuccess(CMRespDto cmRespDto);
}
