package com.example.myeverytime.main.boards.in_post.reply.model;

public class ReplySaveReqDto {
    private String nickname;
    private String content;
    private Boolean anonymous;

    public ReplySaveReqDto(String content) {
        this.content = content;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
