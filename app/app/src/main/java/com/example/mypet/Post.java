package com.example.mypet;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Post extends Domain {

    private Pet pet;

    private ArrayList<Comment> comment;

    private int amountEnjoy;

    private String photos;

    private String description;

    private String dateTime;

    @NonNull
    @Override
    public String toString() {
        return this.description;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public ArrayList<Comment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Comment> comment) {
        this.comment = comment;
    }

    public int getAmountEnjoy() {
        return amountEnjoy;
    }

    public void setAmountEnjoy(int amountEnjoy) {
        this.amountEnjoy = amountEnjoy;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
