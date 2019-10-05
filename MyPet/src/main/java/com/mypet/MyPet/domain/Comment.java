package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Comment extends Domain {

    private Pet pet;

    private Pet post;

    private String content;

    private LocalDateTime dateTime;
}
