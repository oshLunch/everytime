package com.example.myeverytime.main.boards.writing;

import android.util.Log;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.main.boards.interfaces.FreeBoardRetrofitInterface;
import com.example.myeverytime.main.boards.interfaces.WritingActivityView;
import com.example.myeverytime.main.boards.model.PostItem;
import com.example.myeverytime.main.boards.writing.model.WritingDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myeverytime.ApplicationClass.getRetrofit;

public class WritingService {
    private static final String TAG = "WritingService";
    private final WritingActivityView mWritingActivityView;
    private FreeBoardRetrofitInterface freeBoardRetrofitInterface;

    public WritingService(WritingActivityView mWritingActivityView) {
        this.mWritingActivityView = mWritingActivityView;
    }

    void postWriting(Long userId, WritingDto writingDto){
        freeBoardRetrofitInterface = getRetrofit().create(FreeBoardRetrofitInterface.class);
        Call<CMRespDto<WritingDto>> saveFreeBoardCall = freeBoardRetrofitInterface.saveFreeboard(userId, writingDto);
        saveFreeBoardCall.enqueue(new Callback<CMRespDto<WritingDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<WritingDto>> call, Response<CMRespDto<WritingDto>> response) {
                CMRespDto writingRespDto = response.body();
                if (writingRespDto.getData() == null){
                    Log.d(TAG, "onResponse: 글쓰기 실패");
                    mWritingActivityView.validateFailure(null);

                    return;
                }
                mWritingActivityView.WritingSuccess(writingRespDto);
                Log.d(TAG, "onResponse: 글쓰기 성공");
            }

            @Override
            public void onFailure(Call<CMRespDto<WritingDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 글쓰기 구조적으로 실패");
            }
        });
    }
}
