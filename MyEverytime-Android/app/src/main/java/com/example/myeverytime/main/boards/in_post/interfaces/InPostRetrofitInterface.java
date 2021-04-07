package com.example.myeverytime.main.boards.in_post.interfaces;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.main.boards.model.PostItem;
import com.example.myeverytime.main.boards.updating.model.UpdatingReqDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InPostRetrofitInterface {

    @GET("board/{boardId}")
    Call<CMRespDto<PostItem>> getOneFreeBoard(@Path("boardId") Long boardId);

    @DELETE("board/{boardId}/user/{userId}")
    Call<CMRespDto<Void>> deleteOneFreeBoard(@Path("boardId") Long boardId, @Path("userId")Long userId);

    @PUT("board/{boardId}/user/{userId}")
    Call<CMRespDto<UpdatingReqDto>> updateFreeBoard(@Path("boardId") Long boardId, @Path("userId") Long userId , @Body UpdatingReqDto updatingReqDto);

    @GET("board/{boardId}/user/{userId}")
    Call<CMRespDto<Void>> principalCheck(@Path("boardId") Long boardId, @Path("userId") Long userId);
}
