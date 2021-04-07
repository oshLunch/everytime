package com.example.myeverytime.main.boards.in_post.reply.model;

public class Reply {
    private Long id;
    private Long userId;
    private String nickname;
    private String content;
    private String createDate;
    private Boolean anonymous;

    public Reply(String nickname, String content, Boolean anonymous) {
        this.nickname = nickname;
        this.content = content;
        this.anonymous = anonymous;
    }

    public Reply() {
    }


    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", content='" + content + '\'' +
                ", anonymous=" + anonymous +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
