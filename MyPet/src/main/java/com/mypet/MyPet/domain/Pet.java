package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pet extends Domain {

    private User user;

    private String nome;

    private Specie specie;

    private String description;

    private Genre genre;

    private String photo;
}
