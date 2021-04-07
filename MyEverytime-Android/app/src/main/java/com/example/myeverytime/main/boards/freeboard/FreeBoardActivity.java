package com.example.myeverytime.main.boards.freeboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.myeverytime.BaseActivity;
import com.example.myeverytime.CMRespDto;
import com.example.myeverytime.R;
import com.example.myeverytime.main.boards.model.PostItem;
import com.example.myeverytime.main.boards.model.adapter.FreeBoardAdapter;
import com.example.myeverytime.main.boards.writing.WritingActivity;
import com.example.myeverytime.main.interfaces.BoardActivityView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FreeBoardActivity extends BaseActivity implements BoardActivityView, PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "FreeBoardActivity";

    private ArrayList<PostItem> m_post_item_list;
    private FreeBoardAdapter free_board_adapter;
    private RecyclerView rv_free_board;
    private LinearLayoutManager linear_layout_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_board);

        init();
        tryGetFreeBoard();
    }

    private void init(){
        free_board_adapter = new FreeBoardAdapter(getApplicationContext(), m_post_item_list);
        rv_free_board = findViewById(R.id.rv_free_board_post_list);

        linear_layout_manager = new LinearLayoutManager(getApplicationContext());
        rv_free_board.setLayoutManager(linear_layout_manager);

        m_post_item_list = new ArrayList<>();
        free_board_adapter = new FreeBoardAdapter(getApplicationContext(), m_post_item_list);
        rv_free_board.setAdapter(free_board_adapter);
    }

    // 게시판 전체 조회
    private void tryGetFreeBoard() {

        final FreeBoardService freeBoardService = new FreeBoardService(this);
        freeBoardService.getFreeBoard();
    }

    // BoardActivityView 인터페이스 구현
    @Override
    public void validateSuccess(String text) {

    }

    // BoardActivityView 인터페이스 구현
    @Override
    public void validateFailure(String message) {

    }

    // BoardActivityView 인터페이스 구현
    @Override
    public void boardSuccess(CMRespDto cmRespDto) {
        switch (cmRespDto.getCode()) {
            case 100:
                int num_of_posts_in_free_board = ((List<PostItem>)cmRespDto.getData()).size();
                for (int i = 0; i < num_of_posts_in_free_board; i++) {
                    PostItem getPostItemData = (((List<PostItem>) cmRespDto.getData()).get(i));
                    PostItem postItem = new PostItem();

                    //postItem.setContent_index(cmRespDto.getData().get(i).getContentIdx());
                    postItem.setId(getPostItemData.getId());
                    postItem.setUserId(getPostItemData.getUserId());
                    postItem.setTitle(getPostItemData.getTitle());
                    postItem.setContent(getPostItemData.getContent());
                    postItem.setCreateDate(getPostItemData.getCreateDate());
                    postItem.setNickname(getPostItemData.getNickname());
                    postItem.setLike_num(getPostItemData.getLike_num());
                    postItem.setComment_num(getPostItemData.getComment_num());

                    m_post_item_list.add(postItem);
                    Log.d(TAG, "boardSuccess: 자유게시판 데이터? :" + postItem);
                }

                free_board_adapter.notifyDataSetChanged();
                break;
        }
    }


    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_free_board_go_back: // 로비 화면으로 이동
                onBackPressed();
                break;
            case R.id.iv_free_board_more: // 글 쓰기 메뉴 열기
                showPopUp(view);
                break;
            case R.id.iv_free_board_sync: // 글 목록 갱신
                init();
                tryGetFreeBoard();
                break;
        }

    }

    public void showPopUp(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);

        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_board);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.write_post:
                Intent intent = new Intent(FreeBoardActivity.this, WritingActivity.class);
                intent.putExtra("boardName", 1);
                startActivity(intent);
                finish();
                Log.d(TAG, "onMenuItemClick: 글쓰기 버튼 누름");
                return true;
            default:
                return false;
        }
    }
}