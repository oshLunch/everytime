package com.example.myeverytime.main.boards.model;

import java.sql.Timestamp;

public class PostItem {

    // 자유게시판, 비밀게시판 등등 번호 저장용 변수 인데, 미사용 중임
    private int content_index;

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String createDate;
    private String nickname;
    private int like_num;
    private int comment_num;

    public PostItem() {
    }

    public PostItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String
    toString() {
        return "PostItem{" +
                " id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getContent_index() {
        return content_index;
    }

    public void setContent_index(int content_index) {
        this.content_index = content_index;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }
}
