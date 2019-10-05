package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Comment extends Domain {

    private Pet pet;

    private Pet post;

    private String content;

    private LocalDateTime dateTime;
}
