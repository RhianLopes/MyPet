package com.example.mypet.model;


public class Follower extends Domain {

    private Pet petFollower;

    private Pet petFollowed;

    public Follower() {
    }

    public Follower(Long id, boolean isActive, Pet petFollower, Pet petFollowed) {
        super(id, isActive);
        this.petFollower = petFollower;
        this.petFollowed = petFollowed;
    }

    public Pet getPetFollower() {
        return petFollower;
    }

    public void setPetFollower(Pet petFollower) {
        this.petFollower = petFollower;
    }

    public Pet getPetFollowed() {
        return petFollowed;
    }

    public void setPetFollowed(Pet petFollowed) {
        this.petFollowed = petFollowed;
    }
}
