package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Post extends Domain{

    private Pet pet;

    private List<Comment> comment;

    private List<Enjoy> enjoy;

    private String photos;

    private String description;

    private LocalDateTime dateTime;
}
