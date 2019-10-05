package com.mypet.MyPet.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Enjoy extends Domain {

    private Pet pet;

    private Post post;
}
