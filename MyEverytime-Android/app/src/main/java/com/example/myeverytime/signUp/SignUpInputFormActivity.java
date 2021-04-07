package com.example.myeverytime.signUp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.myeverytime.BaseActivity;
import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.MainActivityForFragment;
import com.example.myeverytime.R;
import com.example.myeverytime.signIn.SignInActivity;
import com.example.myeverytime.signUp.interfaces.SignUpInputFormActivityView;
import com.example.myeverytime.signUp.model.SignUpDto;

import static com.example.myeverytime.SharedPreference.setAttribute;
import static com.example.myeverytime.SharedPreference.setAttributeBoolean;

public class SignUpInputFormActivity extends BaseActivity implements SignUpInputFormActivityView {
    private static final String TAG = "SignUpInputFormActivity";

    private EditText et_signup2_ID, et_signup2_PW, et_signup2_PW_again, et_signup2_email, et_signup2_nickName, et_signup2_univName, et_signup2_univYear;
    private Button btn_signUp;
    private SignUpInputFormService signUpInputForm_service;

    private void initEditTexts() {
        et_signup2_ID = findViewById(R.id.et_signup2_id);
        et_signup2_PW = findViewById(R.id.et_signup2_pw);
        et_signup2_PW_again = findViewById(R.id.et_signup2_pw_again);
        et_signup2_email = findViewById(R.id.et_signup2_email);
        et_signup2_nickName = findViewById(R.id.et_signup2_nickName);
        et_signup2_univName = findViewById(R.id.et_signup2_univName);
        et_signup2_univYear = findViewById(R.id.et_signup2_univYear);
    }

    private void initButton() {
        btn_signUp = findViewById(R.id.btn_signup2_signUp);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_signup2_ID.getText().toString().equals("") ||
                        et_signup2_PW.getText().toString().equals("") ||
                        et_signup2_PW_again.getText().toString().equals("") ||
                        et_signup2_email.getText().toString().equals("") ||
                        et_signup2_nickName.getText().toString().equals("") ||
                        et_signup2_univName.getText().toString().equals("") ||
                        et_signup2_univYear.getText().toString().equals("")) {
                    showCustomToast("모든 칸에 정보를 입력해주세요");
                    Log.d(TAG, "onClick: 정보 비우고 onclick");
                } else if (!et_signup2_PW.getText().toString().equals(et_signup2_PW_again.getText().toString())) {
                    showCustomToast("두 비밀번호가 일치하지 않습니다");
                } else {

                    String inputID = et_signup2_ID.getText().toString();
                    String inputPW = et_signup2_PW.getText().toString();
//                    String inputPWAgain = et_signup2_PW_again.getText().toString();
                    String inputEmail = et_signup2_email.getText().toString();
                    String inputNickName = et_signup2_nickName.getText().toString();
                    String inputUnivName = et_signup2_univName.getText().toString();
                    String inputUnivYear = et_signup2_univYear.getText().toString();

                    tryPostSignUp(inputID, inputPW, inputEmail, inputNickName, inputUnivName, inputUnivYear);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_input_form);
        signUpInputForm_service = new SignUpInputFormService(this);

        initEditTexts();
        initButton();
    }

    private void tryPostSignUp(String userID, String userPW, String email, String userNickname, String univName, String univYear) {
        SignUpDto signUpDto = new SignUpDto(userID, userPW, email, userNickname, univName, univYear);

        final SignUpInputFormService signUpInputForm_service = new SignUpInputFormService(this);
        signUpInputForm_service.postSignUp(signUpDto);
    }

    // SignUpInputFormActivityView 인터페이스 구현
    @Override
    public void validateSuccess(String text) {
        showCustomToast(text);
    }

    // SignUpInputFormActivityView 인터페이스 구현
    @Override
    public void validateFailure(String message) {
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    // SignUpInputFormActivityView 인터페이스 구현
    @Override
    public void signUpSuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpInputFormActivity.this);
                builder.setTitle("회원가입이 완료되었습니다.").setMessage("에브리타임").setCancelable(false);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SignUpInputFormActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.create().show();
                break;
            default:
                showCustomToast("default message");
                break;

        }
    }
}