package com.example.myeverytime.main.boards.in_post.reply.interfaces;

import com.example.myeverytime.CMRespDto;

public interface ReplyActivityView {

    void validateSuccess(String text);

    void validateFailure(String message);

    void saveReplySuccess(CMRespDto cmRespDto);

    void getReplySuccess(CMRespDto cmRespDto);

    void deleteReplySuccess(CMRespDto cmRespDto);
}
