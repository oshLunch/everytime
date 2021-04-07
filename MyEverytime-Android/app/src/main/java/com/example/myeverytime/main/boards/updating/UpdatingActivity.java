package com.example.myeverytime.main.boards.updating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myeverytime.BaseActivity;
import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.R;
import com.example.myeverytime.main.boards.freeboard.FreeBoardActivity;
import com.example.myeverytime.main.boards.interfaces.UpdatingActivityView;
import com.example.myeverytime.main.boards.updating.model.UpdatingReqDto;

import static com.example.myeverytime.SharedPreference.getAttributeLong;

public class UpdatingActivity extends BaseActivity implements UpdatingActivityView {

    private static final String TAG = "UpdatingActivity";
    private Context mContext;
    private long mBackKeyPressedTime = 0;
    private Toast mToast;

    private int num_of_board_from;
    private Button btn_updating_complete;
    private EditText et_updating_title, et_updating_content;
    private ImageView iv_updating_cancel;
    private CheckBox chk_writing_anonymous;

    private Long boardId;
    private String updating_title;
    private String updating_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updating);

        mContext = this;
        Intent intent = getIntent();
        num_of_board_from = intent.getExtras().getInt("boardName");
        boardId = intent.getLongExtra("freeBoardId", 0);
        updating_title = intent.getExtras().getString("title");
        updating_content = intent.getExtras().getString("content");

        Log.d(TAG, "onCreate: boardId: " + boardId);
        Log.d(TAG, "onCreate: title: " + updating_title);
        Log.d(TAG, "onCreate: content: " + updating_content);

        et_updating_title = findViewById(R.id.et_updating_title);
        et_updating_content = findViewById(R.id.et_updating_content);
        iv_updating_cancel = findViewById(R.id.btn_updating_cancel);
        chk_writing_anonymous = findViewById(R.id.chk_writing_anonymous);

        et_updating_title.setText(updating_title);
        et_updating_content.setText(updating_content);

        btn_updating_complete = findViewById(R.id.btn_updating_complete);
        // 업데이트 완료 버튼
        btn_updating_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_updating_title.getText().toString().equals("") && et_updating_content.getText().toString().equals("")) {
                    showCustomToast("제목과 내용을 입력하세요");
                } else if (et_updating_title.getText().toString().equals("")) {
                    showCustomToast("제목을 입력하세요");
                } else if (et_updating_content.getText().toString().equals("")) {
                    showCustomToast("내용을 입력하세요");
                } else {
                    String input_title = et_updating_title.getText().toString();
                    String input_content = et_updating_content.getText().toString();

                    tryPostUpdating(boardId, getAttributeLong(mContext, "loginUserId"), input_title, input_content);
                }

            }
        });

        // 뒤로가기 버튼 ( 폰에 내장된 버튼과 같은 동작 )
        iv_updating_cancel.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    // 업데이트 서비스 호출 ( 작성자 본인 여부는 서버 BoardController 단에서 처리함 )
    private void tryPostUpdating(Long boardId, Long userId, String title, String content) {
        UpdatingReqDto updatingReqDto = new UpdatingReqDto(title, content);

        final UpdatingService updatingService = new UpdatingService(this);
        updatingService.postUpdating(boardId, userId, updatingReqDto);
    }

    // UpdatingActivityView 인터페이스 구현
    @Override
    public void validateSuccess(String text) {

    }

    // UpdatingActivityView 인터페이스 구현
    @Override
    public void validateFailure(String message) {

    }

    // UpdatingActivityView 인터페이스 구현
    @Override
    public void UpdatingSuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                showCustomToast("글수정 성공");
                Log.d(TAG, "UpdatingSuccess: 글 수정 성공 code 100");

                Intent intent = new Intent(UpdatingActivity.this, FreeBoardActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                showCustomToast("글수정 실패");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > mBackKeyPressedTime + 2000) {
            mBackKeyPressedTime = System.currentTimeMillis();
            mToast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 작성을 종료합니다.", Toast.LENGTH_SHORT);
            mToast.show();
        }
        else if(System.currentTimeMillis() <= mBackKeyPressedTime + 2000) {

            Intent intent;

            switch (num_of_board_from){
                case 1:
//                    intent = new Intent(UpdatingActivity.this, FreeBoardActivity.class);
//                    startActivity(intent);
                    finish();
                    break;
//                case 2:
//                    intent = new Intent(WritingActivity.this, SecretBoardActivity.class);
//                    startActivity(intent);
//                    finish();
//                    break;
//                case 3:
//                    intent = new Intent(WritingActivity.this, AlumniBoardActivity.class);
//                    startActivity(intent);
//                    finish();
//                    break;
//                case 4:
//                    intent = new Intent(WritingActivity.this, FreshmenBoardActivity.class);
//                    startActivity(intent);
//                    finish();
//                    break;
            }
            mToast.cancel();
        }
    }
}