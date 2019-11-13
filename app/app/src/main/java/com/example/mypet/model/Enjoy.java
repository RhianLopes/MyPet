package com.example.mypet.model;

public class Enjoy extends Domain {

    private Pet pet;

    private Post post;

    public Enjoy() {
    }

    public Enjoy(Long id, boolean isActive, Pet pet, Post post) {
        super(id, isActive);
        this.pet = pet;
        this.post = post;
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
}
