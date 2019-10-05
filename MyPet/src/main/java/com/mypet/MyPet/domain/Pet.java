package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pet extends Domain {

    private User user;

    private String nome;

    private Specie specie;

    private String description;

    private Genre genre;

    private String photo;
}
