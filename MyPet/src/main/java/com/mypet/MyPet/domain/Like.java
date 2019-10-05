package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Like extends Domain {

    private Pet pet;

    private Post post;
}
