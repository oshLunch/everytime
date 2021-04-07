package com.example.myeverytime.main.boards.updating.model;

public class UpdatingReqDto {
    private Long userId;
    private String title;
    private String content;

    public UpdatingReqDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
