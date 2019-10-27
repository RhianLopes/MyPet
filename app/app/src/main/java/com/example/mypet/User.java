package com.example.mypet;

public class User extends Domain {

    private String name;

    private String nickname;

    private String email;

    private String password;

    private String photo;

    public User() {
    }

    public User(Long id, boolean isActive, String name, String nickname, String email, String password, String photo) {
        super(id, isActive);
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
