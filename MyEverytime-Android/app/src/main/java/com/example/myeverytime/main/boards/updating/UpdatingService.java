package com.example.myeverytime.main.boards.updating;

import android.util.Log;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.main.boards.in_post.interfaces.InPostRetrofitInterface;
import com.example.myeverytime.main.boards.interfaces.FreeBoardRetrofitInterface;
import com.example.myeverytime.main.boards.interfaces.UpdatingActivityView;
import com.example.myeverytime.main.boards.updating.model.UpdatingReqDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myeverytime.ApplicationClass.getRetrofit;

public class UpdatingService {
    private static final String TAG = "UpdatingService";
    private final UpdatingActivityView mUpdatingActivityView;
    private InPostRetrofitInterface inPostRetrofitInterface;

    public UpdatingService(UpdatingActivityView mUpdatingActivityView) {
        this.mUpdatingActivityView = mUpdatingActivityView;
    }
    
    void postUpdating(Long boardId, Long userId, UpdatingReqDto updatingReqDto){
        inPostRetrofitInterface = getRetrofit().create(InPostRetrofitInterface.class);
        Call<CMRespDto<UpdatingReqDto>> updateFreeBoardCall = inPostRetrofitInterface.updateFreeBoard(boardId, userId, updatingReqDto);
        updateFreeBoardCall.enqueue(new Callback<CMRespDto<UpdatingReqDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<UpdatingReqDto>> call, Response<CMRespDto<UpdatingReqDto>> response) {
                CMRespDto updatingRespDto = response.body();
                Log.d(TAG, "onResponse: updatingRespDto: " + updatingRespDto);
                if(updatingRespDto == null){
                    Log.d(TAG, "onResponse: 글 수정 실패1111");
                    mUpdatingActivityView.validateFailure("널은안돼");

                }
                mUpdatingActivityView.UpdatingSuccess(updatingRespDto);
                Log.d(TAG, "onResponse: 글 수정 통신 성공");
            }

            @Override
            public void onFailure(Call<CMRespDto<UpdatingReqDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 글 수정 구조적으로 실패");
            }
        });
    }
}
