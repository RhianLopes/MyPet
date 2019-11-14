package com.example.mypet.model;


public class Comment extends Domain {

    private Pet pet;

    private Post post;

    private String content;

    private String dateTime;

    public Comment() {
    }

    public Comment(Long id, boolean isActive, Pet pet, Post post, String content, String dateTime) {
        super(id, isActive);
        this.pet = pet;
        this.post = post;
        this.content = content;
        this.dateTime = dateTime;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
