package com.example.myeverytime.main.boards.in_post.reply;


import android.util.Log;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.main.boards.in_post.reply.interfaces.ReplyActivityView;
import com.example.myeverytime.main.boards.in_post.reply.interfaces.ReplyRetrofitInterface;
import com.example.myeverytime.main.boards.in_post.reply.model.Reply;
import com.example.myeverytime.main.boards.in_post.reply.model.ReplySaveReqDto;

import java.util.List;
import java.util.concurrent.BlockingDeque;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myeverytime.ApplicationClass.getRetrofit;

public class ReplyService {
    private static final String TAG = "ReplyService";
    private final ReplyActivityView mReplyActivityView;
    private ReplyRetrofitInterface replyRetrofitInterface;

    public ReplyService(ReplyActivityView mReplyActivityView) {
        this.mReplyActivityView = mReplyActivityView;
    }

    // 댓글 저장하는 call
    public void saveReply(Long boardId, Long userId, ReplySaveReqDto replySaveReqDto){
        Log.d(TAG, "saveReply: 댓글 저장 통신중");
        replyRetrofitInterface = getRetrofit().create(ReplyRetrofitInterface.class);
        Call<CMRespDto<Reply>> saveReplyCall = replyRetrofitInterface.saveReply(boardId, userId, replySaveReqDto);
        saveReplyCall.enqueue(new Callback<CMRespDto<Reply>>() {
            @Override
            public void onResponse(Call<CMRespDto<Reply>> call, Response<CMRespDto<Reply>> response) {
                CMRespDto cmRespDto = response.body();
                if (cmRespDto == null){
                    mReplyActivityView.validateFailure(null);
                    Log.d(TAG, "onResponse: 댓글 저장 실패");
                }
                mReplyActivityView.saveReplySuccess(cmRespDto);
                Log.d(TAG, "onResponse: 댓글 저장 성공");

                Log.d(TAG, "onResponse: cmRespDto: " + cmRespDto);
            }

            @Override
            public void onFailure(Call<CMRespDto<Reply>> call, Throwable t) {
                mReplyActivityView.validateFailure(null);
                Log.d(TAG, "onFailure: 댓글 저장 구조적으로 실패");
            }
        });
    }

    // 댓글 저장 후 바로 갱신 하기 위해 댓글 리스트 get call
    public void getReply(Long boardId){
        Log.d(TAG, "getReply: 댓글 조회 통신중");
        replyRetrofitInterface = getRetrofit().create(ReplyRetrofitInterface.class);
        Call<CMRespDto<List<Reply>>> getReplyCall = replyRetrofitInterface.getReply(boardId);
        getReplyCall.enqueue(new Callback<CMRespDto<List<Reply>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<Reply>>> call, Response<CMRespDto<List<Reply>>> response) {
                final CMRespDto cmRespDto = response.body();

                if(cmRespDto == null){
                    mReplyActivityView.validateFailure(null);
                    Log.d(TAG, "onResponse: 댓글 조회 실패");
                }
                mReplyActivityView.getReplySuccess(cmRespDto);
                Log.d(TAG, "onResponse: 댓글 조회 성공");
            }

            @Override
            public void onFailure(Call<CMRespDto<List<Reply>>> call, Throwable t) {
                mReplyActivityView.validateFailure(null);
                Log.d(TAG, "onFailure: 댓글 조회 구조적으로 실패" + t.getMessage());
            }
        });
    }


    public void deleteReply(Long replyId, Long userId){
        Log.d(TAG, "deleteReply: 댓글 삭제 통신중");
        replyRetrofitInterface = getRetrofit().create(ReplyRetrofitInterface.class);
        Call<CMRespDto<Void>> deleteReplyCall = replyRetrofitInterface.deleteReply(replyId, userId);
        deleteReplyCall.enqueue(new Callback<CMRespDto<Void>>() {
            @Override
            public void onResponse(Call<CMRespDto<Void>> call, Response<CMRespDto<Void>> response) {
                CMRespDto cmRespDto = response.body();
                mReplyActivityView.deleteReplySuccess(cmRespDto);
                Log.d(TAG, "onResponse: 댓글 삭제 통신 성공");
            }

            @Override
            public void onFailure(Call<CMRespDto<Void>> call, Throwable t) {
                mReplyActivityView.validateFailure("");
                Log.d(TAG, "onFailure: 댓글 삭제 구조적으로 실패");
            }
        });
    }
}
