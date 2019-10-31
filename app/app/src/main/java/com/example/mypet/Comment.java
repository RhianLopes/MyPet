package com.example.mypet;


import java.time.LocalDateTime;


public class Comment extends Domain {

    private Pet pet;

    private Post post;

    private String content;

    private LocalDateTime dateTime;
}
