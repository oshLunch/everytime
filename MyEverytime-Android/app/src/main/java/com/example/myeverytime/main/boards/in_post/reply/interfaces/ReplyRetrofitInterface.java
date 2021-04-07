package com.example.myeverytime.main.boards.in_post.reply.interfaces;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.main.boards.in_post.reply.model.Reply;
import com.example.myeverytime.main.boards.in_post.reply.model.ReplySaveReqDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReplyRetrofitInterface {

    @POST("reply/{boardId}/save/{userId}")
    Call<CMRespDto<Reply>> saveReply(@Path("boardId") Long boardId, @Path("userId") Long userId, @Body ReplySaveReqDto replySaveReqDto);

    @GET("reply/{boardId}")
    Call<CMRespDto<List<Reply>>> getReply(@Path("boardId") Long boardId);

    @DELETE("reply/{replyId}/user/{userId}")
    Call<CMRespDto<Void>> deleteReply(@Path("replyId")Long replyId, @Path("userId")Long userId);
}
