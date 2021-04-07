package com.example.myeverytime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.myeverytime.main.boards.freeboard.FreeBoardActivity;
import com.example.myeverytime.signIn.SignInActivity;
import com.example.myeverytime.user.UserActivityView;
import com.example.myeverytime.user.UserService;
import com.example.myeverytime.user.model.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button button1, button2, button_go_fragment;
    TextView hello_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        hello_user = findViewById(R.id.hello_user);
        button_go_fragment = findViewById(R.id.button_go_fragment);

        button1.setOnClickListener(v -> { // 로그인 화면으로
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        button2.setOnClickListener(v -> { // 자유게시판 화면으로
            Intent intent = new Intent(MainActivity.this, FreeBoardActivity.class);
            startActivity(intent);
        });

        button_go_fragment.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivityForFragment.class);
            startActivity(intent);
            //tryGetUser(getAttribute(getApplicationContext(), "loginId"));
        });

    }

//    private void tryGetUser(String username){
//        final UserService userService = new UserService(this);
//        userService.getUser(username);
//        Log.d(TAG, "tryGetUser: 로그인에 사용한 username : " + username);
//    }
//
//    @Override
//    public void validateSuccess(String text) {
//
//    }
//
//    @Override
//    public void validateFailure(String message) {
//
//    }
//
//    @Override
//    public void getUserSuccess(CMRespDto cmRespDto) {
//        switch (cmRespDto.getCode()) {
//            case 100:
//                User getUserData = (User) cmRespDto.getData();
//
//                User userEntity = new User();
//
//                userEntity.setId(getUserData.getId());
//                userEntity.setUsername(getUserData.getUsername());
//                userEntity.setEmail(getUserData.getEmail());
//                userEntity.setNickname(getUserData.getNickname());
//                userEntity.setUniversity(getUserData.getUniversity());
//                userEntity.setEntranceYear(getUserData.getEntranceYear());
//
//                Log.d(TAG, "getUserSuccess: userEntity: " + userEntity.toString());
//        }
//    }
}