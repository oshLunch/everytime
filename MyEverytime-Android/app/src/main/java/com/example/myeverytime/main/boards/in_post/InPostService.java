package com.example.myeverytime.main.boards.in_post;

import android.util.Log;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.main.boards.in_post.interfaces.InPostActivityView;
import com.example.myeverytime.main.boards.in_post.interfaces.InPostRetrofitInterface;
import com.example.myeverytime.main.boards.model.PostItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myeverytime.ApplicationClass.getRetrofit;

public class InPostService {
    private static final String TAG = "InPostService";
    private final InPostActivityView mInPostActivityView;
    private InPostRetrofitInterface inPostRetrofitInterface;

    InPostService(final InPostActivityView inPostActivityView) {
        this.mInPostActivityView = inPostActivityView;
    }

    public void tryDeleteBoard(Long boardId, Long userId){
        inPostRetrofitInterface = getRetrofit().create(InPostRetrofitInterface.class);
        Call<CMRespDto<Void>> deleteOneFreeBoardCall = inPostRetrofitInterface.deleteOneFreeBoard(boardId, userId);
        deleteOneFreeBoardCall.enqueue(new Callback<CMRespDto<Void>>() {
            @Override
            public void onResponse(Call<CMRespDto<Void>> call, Response<CMRespDto<Void>> response) {
                CMRespDto cmRespDto = response.body();
                mInPostActivityView.DeleteSuccess(cmRespDto);
                Log.d(TAG, "onResponse: 글 삭제 통신 성공");
            }

            @Override
            public void onFailure(Call<CMRespDto<Void>> call, Throwable t) {
                Log.d(TAG, "onFailure: 글 삭제 구조적으로 실패");
            }
        });
    }

    public void getOneFreeBoard(Long boardId){
        inPostRetrofitInterface = getRetrofit().create(InPostRetrofitInterface.class);
        Call<CMRespDto<PostItem>> getOneFreeBoardCall = inPostRetrofitInterface.getOneFreeBoard(boardId);
        getOneFreeBoardCall.enqueue(new Callback<CMRespDto<PostItem>>() {
            @Override
            public void onResponse(Call<CMRespDto<PostItem>> call, Response<CMRespDto<PostItem>> response) {
                CMRespDto cmRespDto = response.body();
                if(cmRespDto.getData() == null){
                    mInPostActivityView.validateFailure(null);
                    return;
                }
                mInPostActivityView.freeBoardSuccess(cmRespDto);
                Log.d(TAG, "onResponse:  한 건 가져오기 성공");

            }

            @Override
            public void onFailure(Call<CMRespDto<PostItem>> call, Throwable t) {
                Log.d(TAG, "onFailure: 한 건 가져오기 구조적으로 실패");
            }
        });
    }

    public void principalCheck(Long boardId, Long userId){
        inPostRetrofitInterface = getRetrofit().create(InPostRetrofitInterface.class);
        Call<CMRespDto<Void>> principalCheckCall = inPostRetrofitInterface.principalCheck(boardId, userId);
        principalCheckCall.enqueue(new Callback<CMRespDto<Void>>() {
            @Override
            public void onResponse(Call<CMRespDto<Void>> call, Response<CMRespDto<Void>> response) {
                CMRespDto cmRespDto = response.body();
                Log.d(TAG, "onResponse: cmRespDto: " + cmRespDto);
                mInPostActivityView.principalCheckSuccess(cmRespDto);
                Log.d(TAG, "onResponse: 글 수정 본인 여부 통신 성공");
            }

            @Override
            public void onFailure(Call<CMRespDto<Void>> call, Throwable t) {
                Log.d(TAG, "onFailure: 글 수정 본인 여부 구조적으로 실패");
            }
        });
    }
}
