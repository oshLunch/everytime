package com.example.myeverytime.main.boards.in_post;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myeverytime.BaseActivity;
import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.R;
import com.example.myeverytime.main.boards.freeboard.FreeBoardActivity;
import com.example.myeverytime.main.boards.in_post.interfaces.InPostActivityView;
import com.example.myeverytime.main.boards.in_post.reply.ReplyAdapter;
import com.example.myeverytime.main.boards.in_post.reply.ReplyService;
import com.example.myeverytime.main.boards.in_post.reply.interfaces.ReplyActivityView;
import com.example.myeverytime.main.boards.in_post.reply.model.Reply;
import com.example.myeverytime.main.boards.in_post.reply.model.ReplySaveReqDto;
import com.example.myeverytime.main.boards.model.PostItem;
import com.example.myeverytime.main.boards.updating.UpdatingActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.myeverytime.SharedPreference.getAttribute;
import static com.example.myeverytime.SharedPreference.getAttributeLong;

public class InPostActivity extends BaseActivity implements InPostActivityView, ReplyActivityView, PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "InPostActivity";
    private Context mContext;
    private Dialog deleteDialog;

    private ArrayList<Reply> m_reply_item_list;
    private RecyclerView rv_in_post_reply;
    private ReplyAdapter reply_adapter;
    private LinearLayoutManager linear_layout_manager;

    private CheckBox chk_in_reply_anonymous;
    private Boolean anonymous_checked = true;

    private TextView tv_in_post_nickname, tv_in_post_time, tv_in_post_title, tv_in_post_content, tv_in_post_like_num, tv_in_post_comment_num, tv_in_post_scrap_num;

    InputMethodManager imm;

    private InPostService inPostService;
    private String clicked;

    private EditText et_in_post_reply;
    private ImageView iv_in_post_register_reply;

    private int m_clicked_free_pos;
    private int m_clicked_secret_pos;
    private int m_clicked_alumni_pos;
    private int m_clicked_freshmen_pos;

    private int m_from_board_num;

    private int m_index_of_this_post;

    private boolean m_from_frag_home;

    private Long boardId; // 삭제, 수정 하기위해서 어댑터에서 intent로 받아온 boardId ( FreeBoardAdapter 에서 intent로 받아옴 )

    public InPostActivity() {
    }

    public InPostActivity(ArrayList<Reply> m_reply_item_list, ReplyAdapter reply_adapter) {
        this.m_reply_item_list = m_reply_item_list;
        this.reply_adapter = reply_adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_post);

        mContext = this;

        // 커스텀 다이얼로그
        deleteDialog = new Dialog(mContext);
        deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deleteDialog.setContentView(R.layout.dialog_yes_no);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE); // 댓글 작성후 키보드 내려주기
        m_reply_item_list = new ArrayList<>();

        reply_adapter = new ReplyAdapter(m_reply_item_list, mContext); // 오류 예상됨
        rv_in_post_reply = findViewById(R.id.rv_board_reply_list);

        linear_layout_manager = new LinearLayoutManager(getApplicationContext());
        rv_in_post_reply.setLayoutManager(linear_layout_manager);

        rv_in_post_reply.setAdapter(reply_adapter);

        // 뷰 세팅
        ViewBinding();

        // DB에 저장된 boardId 주고 받기
        Intent intent = getIntent();
        boardId = intent.getLongExtra("freeBoardId", 0); // ( FreeBoardAdapter 에서 intent로 받아옴 )

        // 댓글 익명 체크박스
        chk_in_reply_anonymous.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                anonymous_checked = true;
            }else {
                anonymous_checked = false;
            }
        });

        // 액티비티가 만들어지면서, 글 상세보기, 댓글목록 가져오기 실행
        tryGetOneFreeBoard(boardId);
        tryGetReply(boardId);

        // 댓글 저장 버튼, EditText에 적혀진 값을 전송
        iv_in_post_register_reply.setOnClickListener(v -> {
            trySaveReply(boardId, et_in_post_reply.getText().toString());
        });

    }

    // 뷰 세팅
    public void ViewBinding() {

        chk_in_reply_anonymous = findViewById(R.id.chk_in_reply_anonymous);

        et_in_post_reply = findViewById(R.id.et_in_post_reply);
        iv_in_post_register_reply = findViewById(R.id.iv_in_post_register_reply);

        tv_in_post_nickname = findViewById(R.id.tv_in_post_nickname);
        tv_in_post_time = findViewById(R.id.tv_in_post_time);
        tv_in_post_title = findViewById(R.id.tv_in_post_title);
        tv_in_post_content = findViewById(R.id.tv_in_post_content);

        tv_in_post_like_num = findViewById(R.id.tv_in_post_like_num);
        tv_in_post_comment_num = findViewById(R.id.tv_in_post_comment_num);
        tv_in_post_scrap_num = findViewById(R.id.tv_in_post_scrap_num);
    }

    // 글 하나 상세 보기
    private void tryGetOneFreeBoard(Long boardId){
        final InPostService inPostService = new InPostService(this);
        inPostService.getOneFreeBoard(boardId);
    }

    // 댓글 저장, 작성, 글쓴이 여부는 서버단 ReplyController 에서 구분함
    private void trySaveReply(Long boardId, String content){
        imm.hideSoftInputFromWindow(et_in_post_reply.getWindowToken(), 0);
        et_in_post_reply.setText("");
        ReplySaveReqDto replySaveReqDto = new ReplySaveReqDto(content);
        if(anonymous_checked){
            replySaveReqDto.setAnonymous(true);
            replySaveReqDto.setNickname("익명");
        }else{
            replySaveReqDto.setAnonymous(false);
            replySaveReqDto.setNickname(getAttribute(mContext, "loginUserNickname"));
        }

        final ReplyService replyService = new ReplyService(this);
        replyService.saveReply(boardId , getAttributeLong(mContext, "loginUserId"), replySaveReqDto);
    }

    // 리사이클러 뷰 갱신
    public void tryGetReply(Long boardId){
        m_reply_item_list.clear();
        final ReplyService replyService = new ReplyService(this);
        replyService.getReply(boardId);
    }


    // 뒤로가기, 더보기 버튼
    public void customOnClick2(View view) {
        switch (view.getId()) {
            case R.id.btn_in_post_go_back:
                onBackPressed();

                break;
            case R.id.btn_in_post_more:
                showPopUp(view);
                break;
        }

    }

    // 글 수정, 삭제 메뉴 팝업
    public void showPopUp(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);

        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_board_delete_update);
        popupMenu.show();
    }

    // 커스텀 다이얼로그
    public void showDeleteDialog(){
        deleteDialog.show();

        Button noBtn = deleteDialog.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(v -> {
            deleteDialog.dismiss();
        });

        Button yesBtn = deleteDialog.findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(v -> {
            // 삭제 서비스 호출 ( 작성자 본인 여부는 서버 BoardController 단에서 처리함 )
            InPostService inPostService = new InPostService(this);
            inPostService.tryDeleteBoard(boardId, getAttributeLong(mContext, "loginUserId"));
            deleteDialog.dismiss();
        });
    }

    // 서비스


    // 팝업메뉴 인터페이스 구현
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.post_delete:
                Log.d(TAG, "onMenuItemClick: 글 삭제 버튼 누름");
                showDeleteDialog();
                return true;

            case R.id.post_update:
                Log.d(TAG, "onMenuItemClick: 글 수정 버튼 누름");
                InPostService inPostService = new InPostService(this);
                inPostService.principalCheck(boardId, getAttributeLong(mContext, "loginUserId"));
                return true;

            default:
                return false;
        }
    }

    // InPostActivityView 인터페이스 구현
    @Override
    public void validateSuccess(String text) {

    }

    // InPostActivityView 인터페이스 구현
    @Override
    public void validateFailure(String message) {
        Log.d(TAG, "validateFailure: 실패했네요");
    }

    // InPostActivityView 인터페이스 구현
    @Override
    public void DeleteSuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                Log.d(TAG, "DeleteSuccess: 글 삭제 성공 code 100");
                AlertDialog.Builder dlg = new AlertDialog.Builder(InPostActivity.this);
                dlg.setTitle("에브리타임");
                dlg.setMessage("글이 삭제 되었습니다.");
                dlg.setPositiveButton("확인", (dialog, which) -> {
                    Intent intent = new Intent(InPostActivity.this, FreeBoardActivity.class);
                    startActivity(intent);
                    finish();
                });
                dlg.show();

                break;
            default:
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(InPostActivity.this);
                dlg2.setTitle("에브리타임");
                dlg2.setMessage("작성자가 아니면 지울 수 없습니다.");
                dlg2.setPositiveButton("확인", (dialog, which) -> {

                });
                dlg2.show();
                Log.d(TAG, "DeleteSuccess: code: " + cmRespDto.getCode());
                break;
        }
    }

    @Override
    public void principalCheckSuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                Log.d(TAG, "principalCheck: 글 수정 통신 성공 code 100");
                Intent intent = new Intent(InPostActivity.this, UpdatingActivity.class);
                intent.putExtra("boardName", 1);
                intent.putExtra("freeBoardId", boardId);
                intent.putExtra("title", tv_in_post_title.getText());
                intent.putExtra("content", tv_in_post_content.getText());
                startActivity(intent);


                break;
            default:
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(InPostActivity.this);
                dlg2.setTitle("에브리타임");
                dlg2.setMessage("작성자가 아니면 수정할 수 없습니다.");
                dlg2.setPositiveButton("확인", (dialog, which) -> {

                });
                dlg2.show();
                Log.d(TAG, "principalCheck: code: " + cmRespDto.getCode());
                break;
        }
    }

    // ReplyActivityView 인터페이스 구현
    @Override
    public void saveReplySuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                Log.d(TAG, "saveReplySuccess: 댓글 저장 성공 code 100");
                showCustomToast("댓글 작성 성공");

                tryGetReply(boardId);

                break;
            default:
                showCustomToast("댓글 작성 실패");
                break;
        }
    }

    // ReplyActivityView 인터페이스 구현
    @Override
    public void getReplySuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                Log.d(TAG, "getReplySuccess: 댓글 조회 성공 code 100");
                int num_of_reply_in_board = ((List<Reply>)cmRespDto.getData()).size();
                for(int i=0; i< num_of_reply_in_board; i++){
                    Reply getReplyItemData = ((List<Reply>)cmRespDto.getData()).get(i);
                    Reply reply = new Reply();

                    reply.setId(getReplyItemData.getId());
                    reply.setContent(getReplyItemData.getContent());
                    reply.setNickname(getReplyItemData.getNickname());
                    reply.setAnonymous(getReplyItemData.getAnonymous());
                    reply.setCreateDate(getReplyItemData.getCreateDate().substring(0,16));

                    Log.d(TAG, "getReplySuccess: 데이터? :" + cmRespDto.getData());
                    m_reply_item_list.add(reply);
                }
                reply_adapter.notifyDataSetChanged();
                break;
            default:
                Log.d(TAG, "getReplySuccess: 코드가 100이 아님");
                break;
        }
    }

    // ReplyActivityView 인터페이스 구현 -> ReplyAdapter 에서 하는중
    @Override
    public void deleteReplySuccess(CMRespDto cmRespDto) {

    }

    // InPostActivityView 인터페이스 구현
    @Override
    public void freeBoardSuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                Log.d(TAG, "freeBoardSuccess: 글 한건 보기 성공 code 100");
                PostItem postItem = (PostItem)cmRespDto.getData();

                tv_in_post_nickname.setText(postItem.getNickname());
                tv_in_post_time.setText(postItem.getCreateDate().substring(0,16));
                tv_in_post_title.setText(postItem.getTitle());
                tv_in_post_content.setText(postItem.getContent());

                // LikeNum , commentNum 아직 추가 안했음.
                break;
            default:
                Log.d(TAG, "freeBoardSuccess: 코드가 100이 아님");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed:  inpost 에서 뒤로가기 누름");
        Intent intent = new Intent(InPostActivity.this, FreeBoardActivity.class);
        startActivity(intent);
        finish();
    }
}