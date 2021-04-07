package com.example.myeverytime.main.boards.freeboard;

import android.util.Log;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.main.boards.interfaces.FreeBoardRetrofitInterface;
import com.example.myeverytime.main.boards.model.PostItem;
import com.example.myeverytime.main.interfaces.BoardActivityView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myeverytime.ApplicationClass.getRetrofit;

public class FreeBoardService {
    private static final String TAG = "FreeBoardService";
    private final BoardActivityView mBoardActivityView;
    private FreeBoardRetrofitInterface freeBoardRetrofitInterface;

    FreeBoardService(final BoardActivityView boardActivityView) {
        this.mBoardActivityView = boardActivityView;
    }

    public void getFreeBoard(){
        freeBoardRetrofitInterface = getRetrofit().create(FreeBoardRetrofitInterface.class);
        Call<CMRespDto<List<PostItem>>> getFreeBoardCall = freeBoardRetrofitInterface.getFreeBoard();
        getFreeBoardCall.enqueue(new Callback<CMRespDto<List<PostItem>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<PostItem>>> call, Response<CMRespDto<List<PostItem>>> response) {
                final CMRespDto cmRespDto = response.body();
                cmRespDto.getData();
                if (cmRespDto == null) {
                    Log.d(TAG, "onResponse: 자유게시판 가져오기 실패");
                    mBoardActivityView.validateFailure(null);
                    return;
                }

                mBoardActivityView.boardSuccess(cmRespDto);
                Log.d(TAG, "onResponse: 자유게시판 가져오기 성공");
            }

            @Override
            public void onFailure(Call<CMRespDto<List<PostItem>>> call, Throwable t) {
                mBoardActivityView.validateFailure(null);
                Log.d(TAG, "onResponse: 자유게시판 가져오기 구조적으로 실패 " + t.getMessage());
            }
        });

    }
}
