package com.univa.domain;

import com.univa.repository.UnivaUserGrade;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UnivaUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String image_url;
    @ManyToOne(targetEntity= UnivaUserGrade.class, fetch=FetchType.EAGER)
    @JoinColumn(name="grade_idx")
    private UnivaUserGrade grade;
    private String nation;
    private int state;
    private LocalDateTime date;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
