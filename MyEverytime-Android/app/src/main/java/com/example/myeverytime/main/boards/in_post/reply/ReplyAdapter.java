package com.example.myeverytime.main.boards.in_post.reply;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.R;
import com.example.myeverytime.main.boards.freeboard.FreeBoardActivity;
import com.example.myeverytime.main.boards.in_post.InPostActivity;
import com.example.myeverytime.main.boards.in_post.reply.interfaces.ReplyActivityView;
import com.example.myeverytime.main.boards.in_post.reply.model.Reply;
import com.example.myeverytime.signUp.SignUpInputFormActivity;

import static com.example.myeverytime.SharedPreference.getAttributeLong;

import java.util.ArrayList;

import static com.example.myeverytime.SharedPreference.getAttributeLong;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> implements ReplyActivityView {

    private static final String TAG = "ReplyAdapter";
    private Context mContext;
    private ArrayList<Reply> reply_item_list;


    public ReplyAdapter(ArrayList<Reply> reply_item_list, Context mContext) {
        this.reply_item_list = reply_item_list;
        this.mContext = mContext;
    }

    public Long getReplyId(int position){
        return reply_item_list.get(position).getId();
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReplyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent,false));

        // ViewHolder에 꼽아서 반환함
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {
        holder.setReply(reply_item_list.get(position));
    }

    @Override
    public int getItemCount() {
        return reply_item_list.size();
    }




    // 1번 ViewHolder 만들기 . 내부클래스로 만들어야함!!!
    // 다른데서 안쓰고 나만 쓸거라서 내부클래스임
    // ViewHolder란 : 하나의 View를 갖고있음.  // 위험한점 : 데이터는 없고 View만 있음
    public class ReplyViewHolder extends RecyclerView.ViewHolder {

        // 2번 item_reply가 가지고 있는 위젯들을 선언
        private TextView tv_reply_nickname;
        private TextView tv_reply_content;
        private TextView tv_reply_create_date;
        private ImageView iv_item_reply_more;
        private ImageView iv_item_reply_like;


        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_reply_nickname = itemView.findViewById(R.id.tv_item_reply_nickname);
            tv_reply_content = itemView.findViewById(R.id.tv_item_reply_content);
            tv_reply_create_date = itemView.findViewById(R.id.tv_item_reply_time);
            iv_item_reply_more = itemView.findViewById(R.id.iv_item_reply_more);
            iv_item_reply_like = itemView.findViewById(R.id.iv_item_reply_like);

            iv_item_reply_more.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext()); // 오류날수도
                builder.setTitle("댓글을 삭제 하시겠습니까?").setMessage("에브리타임");
                builder.setPositiveButton("삭제", (dialogInterface, i) -> {
                    // todo 어댑터 갱신 ( getReply ? )
                    Log.d(TAG, "ReplyViewHolder: 댓글 삭제 눌렀음! 통신 시작");
                    ReplyService replyService = new ReplyService(ReplyAdapter.this);
                    replyService.deleteReply(getReplyId(getAdapterPosition()), getAttributeLong(mContext, "loginUserId"));

                })
                .setNegativeButton("취소", (dialogInterface, i) -> {
                   // 아무일도 없음
                });
                builder.show();
            });
        }

        public void setReply(Reply reply){
            tv_reply_nickname.setText(reply.getNickname());
            tv_reply_content.setText(reply.getContent());
            tv_reply_create_date.setText(reply.getCreateDate());
//            tv_reply_create_date.setText(reply.getCreateDate().substring(5,16));
        }
    }

    @Override
    public void validateSuccess(String text) {

    }

    @Override
    public void validateFailure(String message) {

    }

    @Override
    public void saveReplySuccess(CMRespDto cmRespDto) {

    }

    @Override
    public void getReplySuccess(CMRespDto cmRespDto) {

    }

    // ReplyActivityView 인터페이스 구현
    @Override
    public void deleteReplySuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                Log.d(TAG, "DeleteSuccess: 글 삭제 성공 code 100");
                android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(mContext);
                dlg.setTitle("에브리타임");
                dlg.setMessage("댓글이 삭제 되었습니다.");
                dlg.setPositiveButton("확인", (dialog, which) -> {
                    // 어댑터와 ArrayList<Reply> 가 모두 필요한 함수를 호출해야해서, 적절한 생성자를 만들어서 인스턴스를 만듬.
                    InPostActivity inPostActivity = new InPostActivity(reply_item_list,this);
                    inPostActivity.tryGetReply(getAttributeLong(mContext,"freeBoardId"));
                });
                dlg.show();
                break;
            default:
                android.app.AlertDialog.Builder dlg2 = new android.app.AlertDialog.Builder(mContext);
                dlg2.setTitle("에브리타임");
                dlg2.setMessage("작성자가 아니면 지울 수 없습니다.");
                dlg2.setPositiveButton("확인", (dialog, which) -> {

                });
                dlg2.show();
                Log.d(TAG, "DeleteSuccess: code: " + cmRespDto.getCode());
                break;
        }
    }


}
