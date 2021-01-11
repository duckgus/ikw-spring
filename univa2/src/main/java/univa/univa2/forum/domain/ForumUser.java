package univa.univa2.forum.domain;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
public class ForumUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String image_url;
    @ManyToOne(targetEntity=ForumUserGrade.class, fetch=FetchType.EAGER)
    @JoinColumn(name="grade_idx")
    private ForumUserGrade grade;
    private String nation;
    private int State;
    private LocalDateTime dateTime;

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

    public ForumUserGrade getGrade() {
        return grade;
    }
    public void setGrade(ForumUserGrade grade) {
        this.grade = grade;
    }

    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }

    public int getState() {
        return State;
    }
    public void setState(int state) {
        State = state;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
