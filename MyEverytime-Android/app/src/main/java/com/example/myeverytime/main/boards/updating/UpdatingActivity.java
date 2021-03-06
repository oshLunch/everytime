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
        // ???????????? ?????? ??????
        btn_updating_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_updating_title.getText().toString().equals("") && et_updating_content.getText().toString().equals("")) {
                    showCustomToast("????????? ????????? ???????????????");
                } else if (et_updating_title.getText().toString().equals("")) {
                    showCustomToast("????????? ???????????????");
                } else if (et_updating_content.getText().toString().equals("")) {
                    showCustomToast("????????? ???????????????");
                } else {
                    String input_title = et_updating_title.getText().toString();
                    String input_content = et_updating_content.getText().toString();

                    tryPostUpdating(boardId, getAttributeLong(mContext, "loginUserId"), input_title, input_content);
                }

            }
        });

        // ???????????? ?????? ( ?????? ????????? ????????? ?????? ?????? )
        iv_updating_cancel.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    // ???????????? ????????? ?????? ( ????????? ?????? ????????? ?????? BoardController ????????? ????????? )
    private void tryPostUpdating(Long boardId, Long userId, String title, String content) {
        UpdatingReqDto updatingReqDto = new UpdatingReqDto(title, content);

        final UpdatingService updatingService = new UpdatingService(this);
        updatingService.postUpdating(boardId, userId, updatingReqDto);
    }

    // UpdatingActivityView ??????????????? ??????
    @Override
    public void validateSuccess(String text) {

    }

    // UpdatingActivityView ??????????????? ??????
    @Override
    public void validateFailure(String message) {

    }

    // UpdatingActivityView ??????????????? ??????
    @Override
    public void UpdatingSuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                showCustomToast("????????? ??????");
                Log.d(TAG, "UpdatingSuccess: ??? ?????? ?????? code 100");

                Intent intent = new Intent(UpdatingActivity.this, FreeBoardActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                showCustomToast("????????? ??????");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > mBackKeyPressedTime + 2000) {
            mBackKeyPressedTime = System.currentTimeMillis();
            mToast = Toast.makeText(this, "\'??????\' ????????? ?????? ??? ???????????? ????????? ???????????????.", Toast.LENGTH_SHORT);
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