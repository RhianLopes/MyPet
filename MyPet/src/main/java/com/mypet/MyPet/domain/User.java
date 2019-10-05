package com.mypet.MyPet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class User extends Domain{

    private String name;

    private String nickname;

    @Email
    private String email;

    @JsonIgnore
    private String password;

    private String photo;
}
