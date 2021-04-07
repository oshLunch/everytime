package com.example.myeverytime.main.boards.interfaces;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.main.boards.model.PostItem;
import com.example.myeverytime.main.boards.writing.model.WritingDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FreeBoardRetrofitInterface {

    @GET("board")
    Call<CMRespDto<List<PostItem>>> getFreeBoard();

    @POST("board/{userId}")
    Call<CMRespDto<WritingDto>> saveFreeboard(@Path ("userId") Long userId ,@Body WritingDto writingDto);
}
