package com.example.mypet.model;


import androidx.annotation.NonNull;

public class Pet extends Domain {

    private User user;

    private String name;

    private String specie;

    private String description;

    private String genre;

    private String photo;

    private Long follower;

    private Long followed;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getFollower() {
        return follower;
    }

    public void setFollower(Long follower) {
        this.follower = follower;
    }

    public Long getFollowed() {
        return followed;
    }

    public void setFollowed(Long followed) {
        this.followed = followed;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
