package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pet extends Domain {

    private User user;

    private String name;

    private String specie;

    private String description;

    private String genre;

    private String photo;

    private Long follower;

    private Long followed;
}
