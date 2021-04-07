package com.example.myeverytime.signUp.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.Locale;

public class SignUpDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String userRole;
    private String university;
    private String entranceYear;
    private Timestamp createDate;

    public SignUpDto(String username, String password, String email, String nickname, String university, String entranceYear) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.university = university;
        this.entranceYear = entranceYear;
    }

    public SignUpDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getEntranceYear() {
        return entranceYear;
    }

    public void setEntranceYear(String entranceYear) {
        this.entranceYear = entranceYear;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
