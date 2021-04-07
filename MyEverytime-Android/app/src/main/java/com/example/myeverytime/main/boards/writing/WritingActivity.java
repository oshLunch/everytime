package com.example.myeverytime.main.boards.writing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.example.myeverytime.MainActivity;
import com.example.myeverytime.R;
import com.example.myeverytime.main.boards.freeboard.FreeBoardActivity;
import com.example.myeverytime.main.boards.interfaces.WritingActivityView;
import com.example.myeverytime.main.boards.model.PostItem;
import com.example.myeverytime.main.boards.writing.model.WritingDto;
import com.example.myeverytime.signIn.SignInActivity;

import java.util.HashMap;

import static com.example.myeverytime.SharedPreference.getAttribute;
import static com.example.myeverytime.SharedPreference.getAttributeLong;

public class WritingActivity extends BaseActivity implements WritingActivityView {
    private static final String TAG = "WritingActivity";
    private Context mContext;

    private long mBackKeyPressedTime = 0;
    private Toast mToast;

    private int num_of_board_from;
    private Button btn_writing_complete;
    private EditText et_writing_title, et_writing_content;
    private ImageView iv_writing_cancel;
    private CheckBox chk_writing_anonymous;
    private Boolean anonymous_checked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        mContext = this;

        // 게시판이 여러개 일 때 구분하려고 만든 int변수 .. (사용 안하는중)
        Intent intent = getIntent();
        num_of_board_from = intent.getExtras().getInt("boardName");

        et_writing_title = findViewById(R.id.et_writing_title);
        et_writing_content = findViewById(R.id.et_writing_content);
        iv_writing_cancel = findViewById(R.id.btn_writing_cancel);
        chk_writing_anonymous = findViewById(R.id.chk_writing_anonymous);

        btn_writing_complete = findViewById(R.id.btn_writing_complete);

        // 글쓰기 완료 버튼
        btn_writing_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_writing_title.getText().toString().equals("") && et_writing_content.getText().toString().equals("")) {
                    showCustomToast("제목과 내용을 입력하세요");
                } else if (et_writing_title.getText().toString().equals("")) {
                    showCustomToast("제목을 입력하세요");
                } else if (et_writing_content.getText().toString().equals("")) {
                    showCustomToast("내용을 입력하세요");
                } else {
                    String input_title = et_writing_title.getText().toString();
                    String input_content = et_writing_content.getText().toString();

                    tryPostWriting(input_title, input_content);
                }

            }
        });

        // 뒤로가기 버튼 ( 폰에 내장된 버튼과 같은 동작 )
        iv_writing_cancel.setOnClickListener(v -> {
            onBackPressed();
        });

        // 글쓰기 익명 체크
        chk_writing_anonymous.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                anonymous_checked = true;
                Log.d(TAG, "onCreate: 글쓰기 익명 체크함");
            }else{
                anonymous_checked = false;
                Log.d(TAG, "onCreate: 글쓰기 익명 해제함");
            }
        });
    }

    // 글쓰기 + 익명세팅
    private void tryPostWriting(String title, String content) {
        WritingDto writingDto = new WritingDto(title, content);
        if(anonymous_checked){
            writingDto.setAnonymous(true);
            writingDto.setNickname("익명");
        }else{
            writingDto.setAnonymous(false);
            writingDto.setNickname(getAttribute(mContext, "loginUserNickname"));
        }

        final WritingService writingService = new WritingService(this);
        writingService.postWriting(getAttributeLong(mContext,"loginUserId"), writingDto);
    }

    // WritingActivityView 인터페이스 구현
    @Override
    public void validateSuccess(String text) {

    }

    // WritingActivityView 인터페이스 구현
    @Override
    public void validateFailure(String message) {

    }

    // WritingActivityView 인터페이스 구현
    @Override
    public void WritingSuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                showCustomToast("글쓰기 성공");
                Log.d(TAG, "WritingSuccess: 글쓰기 성공 code 100");

                Intent intent = new Intent(WritingActivity.this, FreeBoardActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                showCustomToast("글쓰기 실패");
                Log.d(TAG, "WritingSuccess: 코드가 100이 아님");
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
                    intent = new Intent(WritingActivity.this, FreeBoardActivity.class);
                    startActivity(intent);
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