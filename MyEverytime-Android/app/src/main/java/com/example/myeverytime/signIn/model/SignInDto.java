package com.example.myeverytime.signIn.model;

import com.google.gson.annotations.SerializedName;

public class SignInDto {

    private String loginUsername;

    private String loginPw;

    public SignInDto(String loginUsername, String loginPw) {
        this.loginUsername = loginUsername;
        this.loginPw = loginPw;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPw() {
        return loginPw;
    }

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }
}
