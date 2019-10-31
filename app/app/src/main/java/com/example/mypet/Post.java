package com.example.mypet;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Post extends Domain{

    private Pet pet;

    private ArrayList<Comment> comment;

    private int amountEnjoy;

    private String photos;

    private String description;

        private LocalDateTime dateTime;
}
