package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Post extends Domain{

    private Pet pet;

    private List<Comment> comment;

    private List<Like> like;

    private String photos;

    private String description;

    private LocalDateTime dateTime;
}
