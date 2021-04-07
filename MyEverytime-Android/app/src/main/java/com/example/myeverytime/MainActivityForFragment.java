package com.example.myeverytime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.myeverytime.fragment.frag_board.BoardFragment;
import com.example.myeverytime.fragment.frag_more.MoreFragment;
import com.example.myeverytime.fragment.frag_notification.NotificationFragment;
import com.example.myeverytime.fragment.frag_time_table.TimeTableFragment;
import com.example.myeverytime.fragment.fraghome.HomeFragment;
import com.example.myeverytime.user.UserActivityView;
import com.example.myeverytime.user.UserService;
import com.example.myeverytime.user.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.myeverytime.SharedPreference.getAttribute;
import static com.example.myeverytime.SharedPreference.setAttribute;
import static com.example.myeverytime.SharedPreference.getAttributeLong;
import static com.example.myeverytime.SharedPreference.setAttributeLong;

public class MainActivityForFragment extends AppCompatActivity implements UserActivityView {
    private static final String TAG = "MainActivityForFragment";
    private Context mContext;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 5개의 메뉴에 들어갈 Fragment들
    private HomeFragment homeFragment = new HomeFragment();
    private TimeTableFragment timeTableFragment = new TimeTableFragment();
    private BoardFragment boardFragment = new BoardFragment();
    private NotificationFragment notificationFragment = new NotificationFragment();
    private MoreFragment moreFragment = new MoreFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_fragment);

        mContext = this;
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        //첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        transaction.replace(R.id.frame_layout, timeTableFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        transaction.replace(R.id.frame_layout, boardFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu4: {
                        transaction.replace(R.id.frame_layout, notificationFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu5: {
                        transaction.replace(R.id.frame_layout, moreFragment).commitAllowingStateLoss();
                        break;
                    }
                }

                return true;
            }
        });


        // 로그인 할 때 작성 했던 username 값으로 findByUsername 해서 User 객체를 받아 와서 preference 에 저장함.
        tryGetUser(getAttribute(getApplicationContext(), "loginId"));
    }

    private void tryGetUser(String username){
        final UserService userService = new UserService(this);
        userService.getUser(username);
        Log.d(TAG, "tryGetUser: 로그인에 사용한 username : " + username);
    }


    // UserActivityView 인터페이스 구현
    @Override
    public void validateSuccess(String text) {

    }

    // UserActivityView 인터페이스 구현
    @Override
    public void validateFailure(String message) {

    }

    // UserActivityView 인터페이스 구현
    @Override
    public void getUserSuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                User getUserData = (User) cmRespDto.getData();

                User userEntity = new User();

                userEntity.setId(getUserData.getId());
                userEntity.setUsername(getUserData.getUsername());
                userEntity.setEmail(getUserData.getEmail());
                userEntity.setNickname(getUserData.getNickname());
                userEntity.setUniversity(getUserData.getUniversity());
                userEntity.setEntranceYear(getUserData.getEntranceYear());

                // 유저 정보 preference 로 관리 하는 곳
                setAttributeLong(mContext,"loginUserId",getUserData.getId());
                setAttribute(mContext,"loginUserNickname",getUserData.getNickname());

                Log.d(TAG, "getUserSuccess: loginUserId: " + getAttributeLong(mContext, "loginUserId"));
                Log.d(TAG, "getUserSuccess: userEntity: " + userEntity.toString());
        }
    }
}